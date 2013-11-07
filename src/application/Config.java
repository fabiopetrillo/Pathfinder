package application;

public class Config {
	
	public static enum Tools {
		SELECTION,
		PACKAGE,
		CLASS,
		METHOD,
		ATTRIBUTE,
		EDGE_START,
		EDGE_END,
		ERASE
	}
	
	public static enum UserAction {
		NOTHING,
		RESIZING
	}
	
	private static Tools selectedTool = Tools.SELECTION;
	private static UserAction currentUserAction = UserAction.NOTHING;
	
	private Config() {
	}
	
	public static void setCurrentTool(Tools action) {
		Config.selectedTool = action;
	}
	
	public static Tools getCurrentTool() {
		return Config.selectedTool;
	}
	
	public static void setUserAction(UserAction action) {
		Config.currentUserAction = action;
	}
	
	public static UserAction getUserAction() {
		return Config.currentUserAction;
	}
}
