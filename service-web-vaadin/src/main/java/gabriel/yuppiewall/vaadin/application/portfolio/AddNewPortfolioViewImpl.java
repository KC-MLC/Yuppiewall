package gabriel.yuppiewall.vaadin.application.portfolio;

import gabriel.yuppiewall.trade.domain.Portfolio;
import gabriel.yuppiewall.trade.service.PortfolioService;
import gabriel.yuppiewall.um.domain.PrimaryPrincipal;
import gabriel.yuppiewall.vaadin.YuppiewallUI;

import java.io.Serializable;
import java.util.Arrays;

import org.springframework.context.annotation.Scope;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope("prototype")
public class AddNewPortfolioViewImpl implements Serializable{

	private VerticalLayout rootLayout;

	public AddNewPortfolioViewImpl() {
	}

	public void init() {
		rootLayout = new VerticalLayout();
		rootLayout.setMargin(true);
		rootLayout.setSpacing(true);
		Portfolio portfolio = new Portfolio();
		BeanItem<Portfolio> personItem = new BeanItem<Portfolio>(portfolio);

		// Create the Form
		final Form portfolioForm = new Form();
		portfolioForm.setCaption("New Portfolio");
		portfolioForm.setWriteThrough(false); // we want explicit 'apply'
		portfolioForm.setInvalidCommitted(false); // no invalid values in
													// datamodel

		// FieldFactory for customizing the fields and adding validators
		portfolioForm.setFormFieldFactory(new PortfolioFieldFactory());
		portfolioForm.setItemDataSource(personItem); // bind to POJO via
														// BeanItem

		// Determines which properties are shown, and in which order:
		portfolioForm.setVisibleItemProperties(Arrays
				.asList(new String[] { "portfolioName" }));

		// Add form to layout
		rootLayout.addComponent(portfolioForm);

		// The cancel / apply buttons
		HorizontalLayout buttons = new HorizontalLayout();
		buttons.setSpacing(true);
		Button discardChanges = new Button("Discard changes",
				new Button.ClickListener() {
					public void buttonClick(ClickEvent event) {
						portfolioForm.discard();
					}
				});
		discardChanges.setStyleName(BaseTheme.BUTTON_LINK);
		buttons.addComponent(discardChanges);
		buttons.setComponentAlignment(discardChanges, Alignment.MIDDLE_LEFT);

		Button apply = new Button("Apply", new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				try {
					// portfolioForm.commit();
					PortfolioService ps = YuppiewallUI.getInstance()
							.getService("portfolioService");

					ps.createPortfolio(new Portfolio(
							(PrimaryPrincipal) YuppiewallUI.getInstance()
									.getApplicationData("user"),
							"portfolioName"));
				} catch (Exception e) {
					// Ignored, we'll let the Form handle the errors
				}
			}
		});
		buttons.addComponent(apply);
		portfolioForm.getFooter().addComponent(buttons);
		portfolioForm.getFooter().setMargin(false, false, true, true);

	}

	private class PortfolioFieldFactory extends DefaultFieldFactory {

		private static final String COMMON_FIELD_WIDTH = "12em";

		public PortfolioFieldFactory() {

		}

		@Override
		public Field createField(Item item, Object propertyId,
				Component uiContext) {
			Field f = super.createField(item, propertyId, uiContext);

			if ("portfolioName".equals(propertyId)) {
				TextField tf = (TextField) f;
				tf.setRequired(true);
				tf.setRequiredError("Please enter New Portfolio Name");
				tf.setWidth(COMMON_FIELD_WIDTH);
				tf.addValidator(new StringLengthValidator(
						"First Name must be 3-15 characters", 3, 15, false));
			}

			return f;
		}

	}

	public ComponentContainer getView() {
		return rootLayout;
	}

}
