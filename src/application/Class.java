package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Class {

	@FXML VBox theClass = new VBox();
	@FXML Text className = new Text();
	@FXML TextField classNameEditor = new TextField();
	@FXML FlowPane methodContainer = new FlowPane();
	@FXML FlowPane attributeContainer = new FlowPane();

	double x = 0;
	double y = 0;
	double dragX = 0;
	double dragY = 0;
	
	@FXML
	protected void mousePressed(MouseEvent event) {
		// TODO
		this.dragX = event.getSceneX();
		this.dragY = event.getSceneY();
		this.x = this.theClass.getLayoutX();
		this.y = this.theClass.getLayoutY();
		event.consume();
	}
	
	@FXML
	protected void mouseReleased(MouseEvent event) {
		// TODO
		event.consume();
	}
	
	@FXML
	protected void mouseClicked(MouseEvent event) {
		if (event.getButton() == MouseButton.PRIMARY) {
			if (event.getClickCount() == 2) {
				this.classNameEditor.setVisible(true);
				this.classNameEditor.requestFocus();
			}
		}
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
	public void classNameEditorAction(ActionEvent event) {
		this.className.setText(this.classNameEditor.getText());
		this.classNameEditor.setVisible(false);
	}
	
	@FXML
	public void addMethod(MouseEvent event) {
		if (event.getButton() == MouseButton.PRIMARY) {
			Circle newMethod = new Circle(20.0, Color.YELLOW);
			newMethod.setStroke(Color.BLACK);
			newMethod.setStrokeWidth(1.0);
			this.methodContainer.getChildren().add(newMethod);
		}
		if (event.getButton() == MouseButton.SECONDARY && !this.methodContainer.getChildren().isEmpty()) {
			this.methodContainer.getChildren().remove(0);
		}
		event.consume();
	}
	
	@FXML
	public void addAttribute(MouseEvent event) {
		if (event.getButton() == MouseButton.PRIMARY) {
			Rectangle newAttribute = new Rectangle(40.0, 40.0, Color.GOLD);
			newAttribute.setStroke(Color.BLACK);
			newAttribute.setStrokeWidth(1.0);
			this.attributeContainer.getChildren().add(newAttribute);
		}
		if (event.getButton() == MouseButton.SECONDARY && !this.attributeContainer.getChildren().isEmpty()) {
			this.attributeContainer.getChildren().remove(0);
		}
		event.consume();
	}
	
	public Class() {
		this.classNameEditor.setText(this.className.getText());
	}
}
