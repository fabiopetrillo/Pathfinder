package application;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import application.Config.Tools;

public class PlatformGround extends AnchorPane {

	private Method startEdgeMethod;
	
	public PlatformGround() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlatformGround.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public void newDiagram() {
		this.platformGround.getChildren().clear();
	}
	
	public ObservableList<Node> getContent() {
		return this.platformGround.getChildren();
	}

	public Package addPackage() {
		Package newPackage = new Package();
		this.platformGround.getChildren().add(newPackage);
		return newPackage;
	}
	
	public Class addClass() {
		Class newClass = new Class();
		this.platformGround.getChildren().add(newClass);
		return newClass;
	}
	
	public PlatformMethod addPlatformMethod() {
		PlatformMethod newPlatformMethod = new PlatformMethod();
		this.platformGround.getChildren().add(newPlatformMethod);
		return newPlatformMethod;
	}
	
	public void addMethodCalling(MethodCalling methodCalling) {
		this.platformGround.getChildren().add(methodCalling);
	}

	public void addAttributeAccess(AttributeAccess attributeAccess) {
		this.platformGround.getChildren().add(attributeAccess);
	}
	
	@FXML
	private Pane platformGround;
	
	@FXML
	protected void doAction(MouseEvent event) {
		switch (Config.getCurrentTool()) {
		case PACKAGE:
			if (event.getButton() == MouseButton.PRIMARY) {
				this.addPackage();
				Config.setCurrentTool(Tools.SELECTION);
			}
			break;
		case CLASS:
			if (event.getButton() == MouseButton.PRIMARY) {
				this.addClass();
				Config.setCurrentTool(Tools.SELECTION);
			}
			break;
		case METHOD:
			if (event.getButton() == MouseButton.PRIMARY) {
				this.addPlatformMethod();
				Config.setCurrentTool(Tools.SELECTION);
			}
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
						methodBeingCalled.addMethodCalling(newMethodCalling);
						this.startEdgeMethod.addMethodCall(newMethodCalling);
						newMethodCalling.updateBindings();
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
}
