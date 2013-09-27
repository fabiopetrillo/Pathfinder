package application;

import application.Config.MouseAction;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PlatformMethod extends Circle {
	
	String name;
	
	private DoubleProperty connectionX, connectionY;
	
	private double x = 0, y = 0;
	private double dragX = 0, dragY = 0;
	
	public PlatformMethod() {
		this.name = "new platform method";
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
				PlatformMethod.this.dragX = event.getSceneX();
				PlatformMethod.this.dragY = event.getSceneY();
				PlatformMethod.this.x = PlatformMethod.this.getLayoutX();
				PlatformMethod.this.y = PlatformMethod.this.getLayoutY();
				event.consume();
			}
			
		});
		this.setOnMouseDragged(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				if (Config.getMouseAction() == MouseAction.SELECTION) {
					double newX = PlatformMethod.this.x + event.getSceneX() - PlatformMethod.this.dragX;
					if (newX >= 0) {
						if (newX+PlatformMethod.this.getRadius()*2 <= ((Pane) PlatformMethod.this.getParent()).getWidth()) {
							PlatformMethod.this.setLayoutX(newX);
						} else {
							PlatformMethod.this.setLayoutX(((Pane) PlatformMethod.this.getParent()).getWidth()-PlatformMethod.this.getRadius()*2);
						}
					} else {
						PlatformMethod.this.setLayoutX(0.0);
					}
					
					double newY = PlatformMethod.this.y + event.getSceneY() - PlatformMethod.this.dragY;
					if (newY >= 0) {
						if (newY+PlatformMethod.this.getRadius()*2 <= ((Pane) PlatformMethod.this.getParent()).getHeight()) {
							PlatformMethod.this.setLayoutY(newY);
						} else {
							PlatformMethod.this.setLayoutY(((Pane) PlatformMethod.this.getParent()).getHeight()-PlatformMethod.this.getRadius()*2);
						}
					} else {
						PlatformMethod.this.setLayoutY(0.0);
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

	public NumberBinding getConnectionX() {
		return Bindings.add(this.connectionX, this.getParent().layoutXProperty());
	}

	public NumberBinding getConnectionY() {
		return Bindings.add(this.connectionY, this.getParent().layoutYProperty());
	}
}
