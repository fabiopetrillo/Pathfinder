package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class PlatformGround {
	@FXML
	private Pane platformGround;
	
	@FXML
	protected void doAction(MouseEvent event) {
		// TODO
		if (event.getButton() == MouseButton.PRIMARY) {
			try {
				Parent newClass = FXMLLoader.load(getClass().getResource("Class.fxml"));
				this.platformGround.getChildren().add(newClass);
			} catch (IOException e) {
				// TODO 
			}
		}
		if (event.getButton() == MouseButton.SECONDARY) {
			
		}
	}
	
}
