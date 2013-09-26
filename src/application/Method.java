package application;

import application.Config.MouseAction;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Method extends Circle {
	
	String name;
	
	private DoubleProperty connectionX, connectionY;
	
	private double x = 0, y = 0;
	private double dragX = 0, dragY = 0;
	
	public Method() {
		this.name = "new method";
		this.setRadius(20.0);
		this.setCenterX(20.0);
		this.setCenterY(20.0);
		this.setFill(Color.YELLOW);
		this.setStroke(Color.BLACK);
		this.setStrokeWidth(1.0);
		
		this.connectionX = new SimpleDoubleProperty(0.0);
		this.connectionX.bind(this.layoutXProperty().add(20.0));
		this.connectionY = new SimpleDoubleProperty(0.0);
		this.connectionY.bind(this.layoutYProperty().add(20.0));
		
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
		this.setOnMouseReleased(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				
			}
			
		});
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				
			}
			
		});
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public DoubleProperty getConnectionX() {
		return this.connectionX;
	}

	public DoubleProperty getConnectionY() {
		return this.connectionY;
	}
}
