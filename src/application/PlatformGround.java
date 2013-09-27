package application;

import java.io.IOException;

import application.Config.MouseAction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class PlatformGround {

	private Method newEdgeStart;
	
	@FXML
	private Pane platformGround;
	
	@FXML
	protected void doAction(MouseEvent event) {
		switch (Config.getMouseAction()) {
		case CLASS_ADD:
			if (event.getButton() == MouseButton.PRIMARY) {
				try {
					Parent newClass = FXMLLoader.load(getClass().getResource("Class.fxml"));
					this.platformGround.getChildren().add(newClass);
					Config.setMouseAction(MouseAction.SELECTION);
				} catch (IOException e) {
					// TODO 
				}
			}
			break;
		case METHOD_ADD:
			if (event.getButton() == MouseButton.PRIMARY) {
				PlatformMethod newPlatformMethod = new PlatformMethod();
				this.platformGround.getChildren().add(newPlatformMethod);
			}
			Config.setMouseAction(MouseAction.SELECTION);
			break;
		case EDGE_ADD_START:
			if (event.getButton() == MouseButton.PRIMARY) {
				if (event.getTarget().getClass() == Method.class) {
					this.newEdgeStart = (Method)(event.getTarget());
					Config.setMouseAction(MouseAction.EDGE_ADD_END);
				} else {
					Config.setMouseAction(MouseAction.SELECTION);
				}
			}
			break;
		case EDGE_ADD_END:
			if (event.getButton() == MouseButton.PRIMARY) {
				if (this.newEdgeStart != null) {
					if (event.getTarget().getClass() == Method.class) {
						MethodCalling newMethodCalling = new MethodCalling();
						Method methodBeingCalled = (Method)(event.getTarget());
						newMethodCalling.startXProperty().bind(this.newEdgeStart.getConnectionX());
						newMethodCalling.startYProperty().bind(this.newEdgeStart.getConnectionY());
						newMethodCalling.endXProperty().bind(methodBeingCalled.getConnectionX());
						newMethodCalling.endYProperty().bind(methodBeingCalled.getConnectionY());
						this.platformGround.getChildren().add(newMethodCalling);
					}
					if (event.getTarget().getClass() == Attribute.class) {
						AttributeAccess newAttributeAccess = new AttributeAccess();
						Attribute attributeBeingAccessed = (Attribute)(event.getTarget());
						newAttributeAccess.startXProperty().bind(this.newEdgeStart.getConnectionX());
						newAttributeAccess.startYProperty().bind(this.newEdgeStart.getConnectionY());
						newAttributeAccess.endXProperty().bind(attributeBeingAccessed.getConnectionX());
						newAttributeAccess.endYProperty().bind(attributeBeingAccessed.getConnectionY());
						this.platformGround.getChildren().add(newAttributeAccess);
					}
					Config.setMouseAction(MouseAction.SELECTION);
				}
			}
			break;
		default:
			break;
		}
	}
	
}
