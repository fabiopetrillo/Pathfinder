package application;

import javafx.fxml.FXML;

public class Menu {

	@FXML
	protected void newDiagram() {
		Main.newDiagram();
	}

	@FXML
	protected void load() {
		Main.loadDiagram();
	}

	@FXML
	protected void save() {
		Main.saveDiagram();
	}

	@FXML
	protected void exportAsImage() {
		Main.exportAsImage();
	}

	@FXML
	protected void close() {
		Main.close();
	}

}
