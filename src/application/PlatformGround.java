package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import application.Config.Tools;

public class PlatformGround {

	private Method startEdgeMethod;
	
	@FXML
	private Pane platformGround;
	
	@FXML
	protected void doAction(MouseEvent event) {
		switch (Config.getCurrentTool()) {
		case PACKAGE:
			if (event.getButton() == MouseButton.PRIMARY) {
				try {
					Parent newPackage = FXMLLoader.load(getClass().getResource("Package.fxml"));
					this.platformGround.getChildren().add(newPackage);
					Config.setCurrentTool(Tools.SELECTION);
				} catch (IOException e) {
					// TODO 
				}
			}
			break;
		case CLASS:
			if (event.getButton() == MouseButton.PRIMARY) {
				try {
					Parent newClass = FXMLLoader.load(getClass().getResource("Class.fxml"));
					this.platformGround.getChildren().add(newClass);
					Config.setCurrentTool(Tools.SELECTION);
				} catch (IOException e) {
					// TODO 
				}
			}
			break;
		case METHOD:
			if (event.getButton() == MouseButton.PRIMARY) {
				PlatformMethod newPlatformMethod = new PlatformMethod();
				this.platformGround.getChildren().add(newPlatformMethod);
			}
			Config.setCurrentTool(Tools.SELECTION);
			break;
		case EDGE_START:
			if (event.getButton() == MouseButton.PRIMARY) {
				if (event.getTarget().getClass() == Method.class) {
					this.startEdgeMethod = (Method)(event.getTarget());
					Config.setCurrentTool(Tools.EDGE_END);
				} else {
					Config.setCurrentTool(Tools.SELECTION);
				}
			}
			break;
		case EDGE_END:
			if (event.getButton() == MouseButton.PRIMARY) {
				if (this.startEdgeMethod != null) {
					if (event.getTarget().getClass() == Method.class) {
						Method methodBeingCalled = (Method)(event.getTarget());
						MethodCalling newMethodCalling = new MethodCalling(this.startEdgeMethod, methodBeingCalled);
						methodBeingCalled.addMethodCall(newMethodCalling);
						this.startEdgeMethod.addMethodCall(newMethodCalling);
						newMethodCalling.bindStart(this.startEdgeMethod.getConnectionX(), this.startEdgeMethod.getConnectionY());
						newMethodCalling.bindEnd(methodBeingCalled.getConnectionX(), methodBeingCalled.getConnectionY());
						this.platformGround.getChildren().add(newMethodCalling);
						newMethodCalling.createArrow();
					}
					if (event.getTarget().getClass() == Attribute.class) {
						Attribute attributeBeingAccessed = (Attribute)(event.getTarget());
						AttributeAccess newAttributeAccess = new AttributeAccess(this.startEdgeMethod, attributeBeingAccessed);
						attributeBeingAccessed.addAttributeAccess(newAttributeAccess);
						this.startEdgeMethod.addAttributeAccess(newAttributeAccess);
						newAttributeAccess.bindStart(this.startEdgeMethod.getConnectionX(), this.startEdgeMethod.getConnectionY());
						newAttributeAccess.bindEnd(attributeBeingAccessed.getConnectionX(), attributeBeingAccessed.getConnectionY());
						this.platformGround.getChildren().add(newAttributeAccess);
					}
					Config.setCurrentTool(Tools.SELECTION);
				}
			}
			break;
		case ERASE:
			
			break;
		default:
			break;
		}
	}
	
	public void newDiagram() {
		this.platformGround.getChildren().clear();
	}
}
