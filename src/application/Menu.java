package application;

import javafx.fxml.FXML;
import application.Config.MouseAction;

public class Menu {

	@FXML
	protected void close() {
		Main.close();
	}
	
	@FXML
	protected void newDiagram() {
	}
	
	@FXML
	protected void addPackage() {
		Config.setMouseAction(MouseAction.PACKAGE_ADD);
	}
	
	@FXML
	protected void removePackage() {
		Config.setMouseAction(MouseAction.PACKAGE_REMOVE);
	}

	@FXML
	protected void addClass() {
		Config.setMouseAction(MouseAction.CLASS_ADD);
	}
	
	@FXML
	protected void removeClass() {
		Config.setMouseAction(MouseAction.CLASS_REMOVE);
	}

	@FXML
	protected void addMethod() {
		Config.setMouseAction(MouseAction.METHOD_ADD);
	}
	
	@FXML
	protected void removeMethod() {
		Config.setMouseAction(MouseAction.METHOD_REMOVE);
	}

	@FXML
	protected void addAttribute() {
		Config.setMouseAction(MouseAction.ATTRIBUTE_ADD);
	}
	
	@FXML
	protected void removeAttribute() {
		Config.setMouseAction(MouseAction.ATTRIBUTE_REMOVE);
	}

	@FXML
	protected void addEdge() {
		Config.setMouseAction(MouseAction.EDGE_ADD_START);
	}
	
	@FXML
	protected void removeEdge() {
		Config.setMouseAction(MouseAction.EDGE_REMOVE);
	}
}
