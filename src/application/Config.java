package application;

public class Config {
	
	public static enum MouseAction {
		SELECTION,
		PACKAGE_ADD,
		PACKAGE_REMOVE,
		CLASS_ADD,
		CLASS_REMOVE,
		METHOD_ADD,
		METHOD_REMOVE,
		ATTRIBUTE_ADD,
		ATTRIBUTE_REMOVE,
		EDGE_ADD_START,
		EDGE_ADD_END,
		EDGE_REMOVE
	}
	
	public static enum UserAction {
		NOTHING,
		RESIZING
	}
	
	private static MouseAction currentMouseAction = MouseAction.SELECTION;
	private static UserAction currentUserAction = UserAction.NOTHING;
	
	private Config() {
	}
	
	public static void setMouseAction(MouseAction action) {
		Config.currentMouseAction = action;
	}
	
	public static MouseAction getMouseAction() {
		return Config.currentMouseAction;
	}
	
	public static void setUserAction(UserAction action) {
		Config.currentUserAction = action;
	}
	
	public static UserAction getUserAction() {
		return Config.currentUserAction;
	}
}
