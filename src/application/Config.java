package application;

public class Config {
	
	public static enum MouseAction {
		SELECTION,
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
	
	private static MouseAction currentMouseAction = MouseAction.SELECTION;
	
	private Config() {
	}
	
	public static void setMouseAction(MouseAction action) {
		Config.currentMouseAction = action;
	}
	
	public static MouseAction getMouseAction() {
		return Config.currentMouseAction;
	}
	
}
