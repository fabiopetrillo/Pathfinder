package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import application.Config.Tools;
import application.Config.UserAction;

public class Class extends VBox {

	private static final double resizeArea = 15.0;

	@FXML VBox theClass = new VBox();
	@FXML AnchorPane classNamePane = new AnchorPane();
	@FXML Text className = new Text();
	@FXML TextField classNameEditor = new TextField();
	@FXML Pane classInwards = new Pane();

	double x = 0;
	double y = 0;
	double dragX = 0;
	double dragY = 0;

	public Class() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Class.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public void setName(String name) {
		this.classNameEditor.setText(name);
	}
	
	public Class addClass() {
		Class newClass = new Class();
		if (this.classInwards.getWidth() < this.theClass.getMinWidth()) {
			this.theClass.setPrefWidth(theClass.getMinWidth()*2 - this.classInwards.getWidth());
		}
		if (this.classInwards.getHeight() < this.theClass.getMinHeight()) {
			this.theClass.setPrefHeight(theClass.getMinHeight()*2 - this.classInwards.getHeight());
		}
		this.classInwards.getChildren().add(newClass);
		return newClass;
	}
	
	public Method addMethod() {
		Method newMethod = new Method();
		this.classInwards.getChildren().add(newMethod);
		newMethod.updateName(this.classInwards);
		return newMethod;
	}
	
	public Attribute addAttribute() {
		Attribute newAttribute = new Attribute();
		this.classInwards.getChildren().add(newAttribute);
		newAttribute.updateName(this.classInwards);
		return newAttribute;
	}
	
	@FXML
	public void initialize() {
		this.className.textProperty().bind(this.classNameEditor.textProperty());
	}
	
	@FXML
	protected void mouseClicked(MouseEvent event) {
		
		switch (Config.getCurrentTool()) {
			case SELECTION:
				if (event.getButton() == MouseButton.PRIMARY) {
					if (event.getClickCount() == 2 && ((Node)event.getSource()).getId().equals("classNamePane")) {
						this.classNameEditor.setVisible(true);
						this.classNameEditor.requestFocus();
					}
				}
				Config.setCurrentTool(Tools.SELECTION);
				break;
			case CLASS:
				if (event.getButton() == MouseButton.PRIMARY) {
					this.addClass();
					Config.setCurrentTool(Tools.SELECTION);
				}
				break;
			case METHOD:
				if (event.getButton() == MouseButton.PRIMARY) {
					this.addMethod();
					Config.setCurrentTool(Tools.SELECTION);
				}
				break;
			case ATTRIBUTE:
				if (event.getButton() == MouseButton.PRIMARY) {
					this.addAttribute();
					Config.setCurrentTool(Tools.SELECTION);
				}
				break;
			case ERASE:
				if (event.getButton() == MouseButton.PRIMARY) {
					EventTarget target = event.getTarget();
					if (target instanceof Method) {
						((Method) target).clearCallAccess();
					} else if (target instanceof Attribute) {
						((Attribute) target).clearAccess();
					} else {
						for (Object children : this.classInwards.getChildren()) {
							if (children instanceof Method) {
								((Method) children).clearCallAccess();
							}
							if (children instanceof Attribute) {
								((Attribute) children).clearAccess();
							}
						}
						((Pane) this.theClass.getParent()).getChildren().remove(this.theClass);
					}
					this.classInwards.getChildren().remove(target);
				}
				Config.setCurrentTool(Tools.SELECTION);
				break;
			default:
				break;
		}
	}

	@FXML
	protected void mouseDragged(MouseEvent event) {
		switch (Config.getUserAction()) {
			case NOTHING:
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
				break;
			case RESIZING:
				double newWidth = this.x + event.getSceneX() - this.dragX;
				if (newWidth >= this.theClass.getMinWidth()) {
					if (newWidth <= ((Pane) this.theClass.getParent()).getWidth() - this.theClass.getLayoutX()) {
						this.theClass.setPrefWidth(newWidth);
					} else {
						this.theClass.setPrefWidth(((Pane) this.theClass.getParent()).getWidth() - this.theClass.getLayoutX());
					}
				} else {
					this.theClass.setPrefWidth(this.theClass.getMinWidth());
				}
				double newHeight = this.y + event.getSceneY() - this.dragY;
				if (newHeight >= this.theClass.getMinHeight()) {
					if (newHeight <= ((Pane) this.theClass.getParent()).getHeight() - this.theClass.getLayoutY()) {
						this.theClass.setPrefHeight(newHeight);
					} else {
						this.theClass.setPrefHeight(((Pane) this.theClass.getParent()).getHeight() - this.theClass.getLayoutY());
					}
				} else {
					this.theClass.setPrefHeight(this.theClass.getMinHeight());
				}
				break;
			default:
				break;
		}
		event.consume();
	}

	@FXML
	protected void mouseMoved(MouseEvent event) {
		if ((event.getX() > this.theClass.getTranslateX() + this.theClass.getPrefWidth() - resizeArea) && (event.getY() > this.theClass.getTranslateY() + this.theClass.getPrefHeight() - resizeArea)) {
			this.theClass.setCursor(Cursor.SE_RESIZE);
		} else {
			this.theClass.setCursor(Cursor.DEFAULT);
		}
		event.consume();
	}

	@FXML
	protected void mousePressed(MouseEvent event) {
		this.dragX = event.getSceneX();
		this.dragY = event.getSceneY();
		if ((event.getX() > this.theClass.getTranslateX() + this.theClass.getPrefWidth() - resizeArea) && (event.getY() + this.classNamePane.getHeight() > this.theClass.getTranslateY() + this.theClass.getPrefHeight() - resizeArea)) {
			Config.setUserAction(UserAction.RESIZING);
			this.x = this.theClass.getWidth();
			this.y = this.theClass.getHeight();
		} else {
			this.x = this.theClass.getLayoutX();
			this.y = this.theClass.getLayoutY();
		}
		event.consume();
	}

	@FXML
	protected void mouseReleased(MouseEvent event) {
		// TODO
		Config.setUserAction(UserAction.NOTHING);
		event.consume();
	}

	@FXML
	public void classNameEditorAction(ActionEvent event) {
		this.classNameEditor.setVisible(false);
	}
}
