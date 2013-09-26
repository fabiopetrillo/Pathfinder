package application;

import application.Config.MouseAction;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Attribute extends Rectangle {
	
	String name;
	
	private DoubleProperty connectionX, connectionY;
	
	private double x = 0, y = 0;
	private double dragX = 0, dragY = 0;
	
	public Attribute() {
		this.name = "new attribute";
		this.setHeight(40.0);
		this.setWidth(40.0);
		this.setFill(Color.GOLD);
		this.setStroke(Color.BLACK);
		this.setStrokeWidth(1.0);
		
		this.connectionX = new SimpleDoubleProperty(0.0);
		this.connectionX.bind(this.layoutXProperty().add(20.0));
		this.connectionY = new SimpleDoubleProperty(0.0);
		this.connectionY.bind(this.layoutYProperty().add(20.0));
		
		this.setOnMousePressed(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				Attribute.this.dragX = event.getSceneX();
				Attribute.this.dragY = event.getSceneY();
				Attribute.this.x = Attribute.this.getLayoutX();
				Attribute.this.y = Attribute.this.getLayoutY();
				event.consume();
			}
			
		});
		this.setOnMouseDragged(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				if (Config.getMouseAction() == MouseAction.SELECTION) {
					double newX = Attribute.this.x + event.getSceneX() - Attribute.this.dragX;
					if (newX >= 0) {
						if (newX+Attribute.this.getWidth() <= ((Pane) Attribute.this.getParent()).getWidth()) {
							Attribute.this.setLayoutX(newX);
						} else {
							Attribute.this.setLayoutX(((Pane) Attribute.this.getParent()).getWidth()-Attribute.this.getWidth());
						}
					} else {
						Attribute.this.setLayoutX(0.0);
					}
					
					double newY = Attribute.this.y + event.getSceneY() - Attribute.this.dragY;
					if (newY >= 0) {
						if (newY+Attribute.this.getHeight() <= ((Pane) Attribute.this.getParent()).getHeight()) {
							Attribute.this.setLayoutY(newY);
						} else {
							Attribute.this.setLayoutY(((Pane) Attribute.this.getParent()).getHeight()-Attribute.this.getHeight());
						}
					} else {
						Attribute.this.setLayoutY(0.0);
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
