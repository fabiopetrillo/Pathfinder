package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import application.Config.MouseAction;

public class PlatformGround {

	private Method startEdgeMethod;
	
	@FXML
	private Pane platformGround;
	
	@FXML
	protected void doAction(MouseEvent event) {
		switch (Config.getMouseAction()) {
		case PACKAGE_ADD:
			if (event.getButton() == MouseButton.PRIMARY) {
				try {
					Parent newPackage = FXMLLoader.load(getClass().getResource("Package.fxml"));
					this.platformGround.getChildren().add(newPackage);
					Config.setMouseAction(MouseAction.SELECTION);
				} catch (IOException e) {
					// TODO 
				}
			}
			break;
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
					this.startEdgeMethod = (Method)(event.getTarget());
					Config.setMouseAction(MouseAction.EDGE_ADD_END);
				} else {
					Config.setMouseAction(MouseAction.SELECTION);
				}
			}
			break;
		case EDGE_ADD_END:
			if (event.getButton() == MouseButton.PRIMARY) {
				if (this.startEdgeMethod != null) {
					if (event.getTarget().getClass() == Method.class) {
						Method methodBeingCalled = (Method)(event.getTarget());
						MethodCalling newMethodCalling = new MethodCalling(this.startEdgeMethod, methodBeingCalled);
						methodBeingCalled.addMethodCall(newMethodCalling);
						this.startEdgeMethod.addMethodCall(newMethodCalling);
						newMethodCalling.startXProperty().bind(this.startEdgeMethod.getConnectionX());
						newMethodCalling.startYProperty().bind(this.startEdgeMethod.getConnectionY());
						newMethodCalling.endXProperty().bind(methodBeingCalled.getConnectionX());
						newMethodCalling.endYProperty().bind(methodBeingCalled.getConnectionY());
						this.platformGround.getChildren().add(newMethodCalling);
					}
					if (event.getTarget().getClass() == Attribute.class) {
						Attribute attributeBeingAccessed = (Attribute)(event.getTarget());
						AttributeAccess newAttributeAccess = new AttributeAccess(this.startEdgeMethod, attributeBeingAccessed);
						attributeBeingAccessed.addAttributeAccess(newAttributeAccess);
						this.startEdgeMethod.addAttributeAccess(newAttributeAccess);
						newAttributeAccess.startXProperty().bind(this.startEdgeMethod.getConnectionX());
						newAttributeAccess.startYProperty().bind(this.startEdgeMethod.getConnectionY());
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
	
	public void newDiagram() {
		this.platformGround.getChildren().clear();
	}
}
