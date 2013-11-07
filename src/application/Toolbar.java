package application;

import javafx.fxml.FXML;
import application.Config.Tools;

public class Toolbar {

	@FXML
	protected void setSelectionTool() {
		Config.setCurrentTool(Tools.SELECTION);
	}

	@FXML
	protected void setPackageTool() {
		Config.setCurrentTool(Tools.PACKAGE);
	}

	@FXML
	protected void setClassTool() {
		Config.setCurrentTool(Tools.CLASS);
	}

	@FXML
	protected void setMethodTool() {
		Config.setCurrentTool(Tools.METHOD);
	}

	@FXML
	protected void setAttributeTool() {
		Config.setCurrentTool(Tools.ATTRIBUTE);
	}

	@FXML
	protected void setEdgeTool() {
		Config.setCurrentTool(Tools.EDGE_START);
	}

	@FXML
	protected void setEraseTool() {
		Config.setCurrentTool(Tools.ERASE);
	}
}
