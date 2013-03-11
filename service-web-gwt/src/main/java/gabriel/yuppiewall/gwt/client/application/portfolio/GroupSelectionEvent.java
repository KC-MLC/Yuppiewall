package gabriel.yuppiewall.gwt.client.application.portfolio;

import gabriel.yuppiewall.trade.domain.Portfolio;

import com.google.gwt.event.shared.GwtEvent;

public class GroupSelectionEvent extends GwtEvent<GroupSelectionEventHandler> {

	public static Type<GroupSelectionEventHandler> TYPE = new Type<GroupSelectionEventHandler>();
	private Portfolio value;

	public GroupSelectionEvent(Portfolio value) {
		this.value = value;
	}

	@Override
	public Type<GroupSelectionEventHandler> getAssociatedType() {
		return TYPE;
	}

	public Portfolio getValue() {
		return value;
	}

	@Override
	protected void dispatch(GroupSelectionEventHandler handler) {
		handler.onGroupSelectionChanged(this);
	}
}