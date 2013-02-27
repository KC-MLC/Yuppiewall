package gabriel.yuppiewall.gwt.client.application.portfolio;

import gabriel.yuppiewall.common.Tuple;

import com.google.gwt.event.shared.GwtEvent;

public class GroupSelectionEvent extends GwtEvent<GroupSelectionEventHandler> {

	public static Type<GroupSelectionEventHandler> TYPE = new Type<GroupSelectionEventHandler>();
	private Tuple<Integer, String> value;

	public GroupSelectionEvent(Tuple<Integer, String> value) {
		this.value = value;
	}

	@Override
	public Type<GroupSelectionEventHandler> getAssociatedType() {
		return TYPE;
	}

	public Tuple<Integer, String> getValue() {
		return value;
	}

	@Override
	protected void dispatch(GroupSelectionEventHandler handler) {
		handler.onGroupSelectionChanged(this);
	}
}