/*
 * Copyright 2010 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package gabriel.yuppiewall.gwt.client;

import gabriel.yuppiewall.gwt.client.application.ApplicationRegistry;
import gabriel.yuppiewall.gwt.client.application.ApplicationRegistry.ApplicationHolder;
import gabriel.yuppiewall.gwt.client.application.ApplicationWidget;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.prefetch.Prefetcher;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Yuppiewall implements EntryPoint {

	public static class AppUtils {

		public static EventBus EVENT_BUS = GWT.create(SimpleEventBus.class);
	}

	/**
	 * Get the token for a given content widget.
	 * 
	 * @return the content widget token.
	 */
	public static String getApplicationWidgetToken(ApplicationWidget content) {
		return getApplicationWidgetToken(content.getClass());
	}

	/**
	 * Get the token for a given content widget.
	 * 
	 * @return the content widget token.
	 */
	public static <C extends ApplicationWidget> String getApplicationWidgetToken(
			Class<C> cwClass) {
		String className = cwClass.getName();
		className = className.substring(className.lastIndexOf('.') + 1);
		return "!" + className;
	}

	/**
	 * The main application shell.
	 */
	private YuppiewallShell shell;

	/**
	 * The static images used throughout the Showcase.
	 */
	public static final YuppiewallResources images = GWT
			.create(YuppiewallResources.class);

	// TODO REMOVE THIS HACK TO EVENT BUS
	public static Yuppiewall HACK;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		HACK = this;
		ApplicationRegistry registry = new ApplicationRegistry();
		List<ApplicationHolder> contentWidgets = registry
				.getAllApplicationWidgets();
		shell = new YuppiewallShell(registry);
		RootLayoutPanel.get().add(shell);
		// Always prefetch.
		Prefetcher.start();
		for (ApplicationHolder applicationHolder : contentWidgets) {

			Prefetcher.prefetch(applicationHolder.getSplitPoint());
		}
		displayAppicationWidget(contentWidgets.get(0).getApplicationWidget());
	}

	/**
	 * Set the content to the {@link ContentWidget}.
	 * 
	 * @param content
	 *            the {@link ContentWidget} to display
	 */
	public void displayAppicationWidget(ApplicationWidget content) {
		if (content == null) {
			return;
		}

		shell.setContent(content);
		// Window.setTitle("Showcase of Features: " + content.getName());
	}
}
