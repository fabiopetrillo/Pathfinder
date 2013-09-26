package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import application.Config.MouseAction;

public class Class {

	@FXML VBox theClass = new VBox();
	@FXML Text className = new Text();
	@FXML TextField classNameEditor = new TextField();
	@FXML Pane classInwards = new Pane();

	double x = 0;
	double y = 0;
	double dragX = 0;
	double dragY = 0;
	
	public Class() {
		this.classNameEditor.setText(this.className.getText());
	}
	
	private Method newEdgeStart;
	
	@FXML
	protected void mousePressed(MouseEvent event) {
		this.dragX = event.getSceneX();
		this.dragY = event.getSceneY();
		this.x = this.theClass.getLayoutX();
		this.y = this.theClass.getLayoutY();
		event.consume();
	}
	
	@FXML
	protected void mouseDragged(MouseEvent event) {
		double newX = this.x + event.getSceneX() - this.dragX;
		if (newX >= 0) {
			if (newX+this.theClass.getWidth() <= ((Pane) this.theClass.getParent()).getWidth()) {
				this.theClass.setLayoutX(newX);
			} else {
				this.theClass.setLayoutX(((Pane) this.theClass.getParent()).getWidth()-this.theClass.getWidth());
			}
		} else {
			this.theClass.setLayoutX(0.0);
		}
		
		double newY = this.y + event.getSceneY() - this.dragY;
		if (newY >= 0) {
			if (newY+this.theClass.getHeight() <= ((Pane) this.theClass.getParent()).getHeight()) {
				this.theClass.setLayoutY(newY);
			} else {
				this.theClass.setLayoutY(((Pane) this.theClass.getParent()).getHeight()-this.theClass.getHeight());
			}
		} else {
			this.theClass.setLayoutY(0.0);
		}
		
		event.consume();
	}
	
	@FXML
	protected void mouseReleased(MouseEvent event) {
		// TODO
		event.consume();
	}
	
	@FXML
	protected void mouseClicked(MouseEvent event) {
		
		switch (Config.getMouseAction()) {
			case SELECTION:
				if (event.getButton() == MouseButton.PRIMARY) {
					if (event.getClickCount() == 2) {
						this.classNameEditor.setVisible(true);
						this.classNameEditor.requestFocus();
					}
				}
				Config.setMouseAction(MouseAction.SELECTION);
				break;
			case METHOD_ADD:
				if (event.getButton() == MouseButton.PRIMARY) {
					Method newMethod = new Method();
					this.classInwards.getChildren().add(newMethod);
				}
				Config.setMouseAction(MouseAction.SELECTION);
				break;
			case METHOD_REMOVE:
//				if (event.getButton() == MouseButton.SECONDARY && !this.methodContainer.getChildren().isEmpty()) {
//					this.classInwards.getChildren().remove(0);
//				}
				break;
			case ATTRIBUTE_ADD:
				if (event.getButton() == MouseButton.PRIMARY) {
					Attribute newAttribute = new Attribute();
					this.classInwards.getChildren().add(newAttribute);
				}
				Config.setMouseAction(MouseAction.SELECTION);
				break;
			case ATTRIBUTE_REMOVE:
//					if (event.getButton() == MouseButton.SECONDARY && !this.attributeContainer.getChildren().isEmpty()) {
//						this.classInwards.getChildren().remove(0);
//					}
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
							this.classInwards.getChildren().add(newMethodCalling);
						}
						if (event.getTarget().getClass() == Attribute.class) {
							AttributeAccess newAttributeAccess = new AttributeAccess();
							Attribute attributeBeingAccessed = (Attribute)(event.getTarget());
							newAttributeAccess.startXProperty().bind(this.newEdgeStart.getConnectionX());
							newAttributeAccess.startYProperty().bind(this.newEdgeStart.getConnectionY());
							newAttributeAccess.endXProperty().bind(attributeBeingAccessed.getConnectionX());
							newAttributeAccess.endYProperty().bind(attributeBeingAccessed.getConnectionY());
							this.classInwards.getChildren().add(newAttributeAccess);
						}
						Config.setMouseAction(MouseAction.SELECTION);
					}
				}
				break;
			default:
				break;
		}
		event.consume();
	}

	@FXML
	public void classNameEditorAction(ActionEvent event) {
		this.className.setText(this.classNameEditor.getText());
		this.classNameEditor.setVisible(false);
	}
}
