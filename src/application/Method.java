package application;

import java.util.ArrayList;

import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import application.Config.MouseAction;

public class Method extends Circle {

	private ArrayList<MethodCalling> methodCalls = new ArrayList<MethodCalling>();
	private ArrayList<AttributeAccess> attributeAccess = new ArrayList<AttributeAccess>();
	
	private Tooltip methodNameTooltip = new Tooltip();
	private TextField methodNameEditor = new TextField("new method");

	private DoubleProperty connectionX, connectionY;

	private double x = 0, y = 0;
	private double dragX = 0, dragY = 0;

	public Method() {
		this.methodNameTooltip.textProperty().bind(this.methodNameEditor.textProperty());
		Tooltip.install(this, this.methodNameTooltip);
		this.methodNameEditor.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				Method.this.methodNameEditor.setVisible(false);
			}
			
		});
		this.methodNameEditor.setVisible(false);
		this.methodNameEditor.setMinWidth(30.0);

		this.setRadius(20.0);
		this.setCenterX(20.0);
		this.setCenterY(20.0);
		this.setFill(Color.YELLOW);
		this.setStroke(Color.BLACK);
		this.setStrokeWidth(1.0);

		this.connectionX = new SimpleDoubleProperty(0.0);
		this.connectionX.bind(this.layoutXProperty().add(20.0));
		this.connectionY = new SimpleDoubleProperty(0.0);
		this.connectionY.bind(this.layoutYProperty().add(50.0));

		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				switch (Config.getMouseAction()) {
					case SELECTION:
						if (event.getButton() == MouseButton.PRIMARY) {
							if (event.getClickCount() == 2) {
								Method.this.methodNameEditor.setVisible(true);
								Method.this.methodNameEditor.requestFocus();
							}
						}
						break;
					default:
						break;
				}
			}
			
		});
		this.setOnMouseDragged(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				if (Config.getMouseAction() == MouseAction.SELECTION) {
					double newX = Method.this.x + event.getSceneX() - Method.this.dragX;
					if (newX >= 0) {
						if (newX+Method.this.getRadius()*2 <= ((Pane) Method.this.getParent()).getWidth()) {
							Method.this.setLayoutX(newX);
						} else {
							Method.this.setLayoutX(((Pane) Method.this.getParent()).getWidth()-Method.this.getRadius()*2);
						}
					} else {
						Method.this.setLayoutX(0.0);
					}
					double newY = Method.this.y + event.getSceneY() - Method.this.dragY;
					if (newY >= 0) {
						if (newY+Method.this.getRadius()*2 <= ((Pane) Method.this.getParent()).getHeight()) {
							Method.this.setLayoutY(newY);
						} else {
							Method.this.setLayoutY(((Pane) Method.this.getParent()).getHeight()-Method.this.getRadius()*2);
						}
					} else {
						Method.this.setLayoutY(0.0);
					}
				}
				event.consume();
			}
			
		});
		this.setOnMousePressed(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				Method.this.dragX = event.getSceneX();
				Method.this.dragY = event.getSceneY();
				Method.this.x = Method.this.getLayoutX();
				Method.this.y = Method.this.getLayoutY();
				event.consume();
			}
			
		});
		this.setOnMouseReleased(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				
			}
			
		});
	}

	public void updateName(Pane parent) {
		parent.getChildren().add(this.methodNameEditor);
		this.methodNameEditor.layoutXProperty().bind(this.layoutXProperty());
		this.methodNameEditor.layoutYProperty().bind(this.layoutYProperty().add(this.getRadius()*2 + this.methodNameEditor.getHeight()));
	}

	public NumberBinding getConnectionX() {
		Parent recursiveParent = this.getParent().getParent();
		NumberBinding returnBinding = this.connectionX.add(recursiveParent.layoutXProperty());
		while (recursiveParent.getParent() != null && !recursiveParent.getParent().getId().equals("platformGround")) {
			recursiveParent = recursiveParent.getParent();
			if (recursiveParent.getId().equals("theClass")) {
				returnBinding = returnBinding.add(recursiveParent.layoutXProperty());
			}
		}
		return returnBinding;
	}

	public NumberBinding getConnectionY() {
		Parent recursiveParent = this.getParent().getParent();
		NumberBinding returnBinding = this.connectionY.add(recursiveParent.layoutYProperty());
		while (recursiveParent.getParent() != null && !recursiveParent.getParent().getId().equals("platformGround")) {
			recursiveParent = recursiveParent.getParent();
			if (recursiveParent.getId().equals("theClass")) {
				returnBinding = returnBinding.add(recursiveParent.layoutYProperty().add(28.0));
			}
		}
		return returnBinding;
	}

	public void addMethodCall(MethodCalling call) {
		this.methodCalls.add(call);
	}
	
	public void removeMethodCall(MethodCalling call) {
		this.methodCalls.remove(call);
	}

	public void addAttributeAccess(AttributeAccess access) {
		this.attributeAccess.add(access);
	}
	
	public void removeAttributeAccess(AttributeAccess access) {
		this.attributeAccess.remove(access);
	}
	
	public void clearCallAccess() {
		for (int index = this.methodCalls.size()-1; index == 0; --index) {
			this.methodCalls.get(index).delete();
		}
		for (int index = this.attributeAccess.size()-1; index == 0; --index) {
			this.attributeAccess.get(index).delete();
		}
	}
}
