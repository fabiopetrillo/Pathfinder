package application;

import javafx.beans.binding.NumberBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class MethodCalling extends Line {
	
	private Method start;
	private Method end;
	private Polygon arrow = new Polygon();
	
	public MethodCalling(Method start, Method end) {
		this.start = start;
		this.end = end;
		this.arrow.getPoints().addAll(new Double[]{ 0.0, 5.0, 5.0, -5.0, -5.0, -5.0 });
	}
	
	public void delete() {
		this.start.removeMethodCall(this);
		this.end.removeMethodCall(this);
		((Pane) this.getParent()).getChildren().remove(this.arrow);
		((Pane) this.getParent()).getChildren().remove(this);
	}

	public void bindStart(NumberBinding startX, NumberBinding startY) {
		this.startXProperty().bind(startX);
		this.startYProperty().bind(startY);
	}

	public void bindEnd(NumberBinding endX, NumberBinding endY) {
		this.endXProperty().bind(endX);
		this.endYProperty().bind(endY);
	}
	
	public void createArrow() {
		((Pane) this.getParent()).getChildren().add(this.arrow);
		this.arrow.layoutXProperty().bind(this.endXProperty());
		this.arrow.layoutYProperty().bind(this.endYProperty());
		this.arrow.setRotate(Math.toDegrees(Math.atan2(this.getStartX() - this.getEndX(), this.getEndY() - this.getStartY())));
		this.startXProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				MethodCalling.this.arrow.setRotate(Math.toDegrees(Math.atan2(MethodCalling.this.getStartX() - MethodCalling.this.getEndX(), MethodCalling.this.getEndY() - MethodCalling.this.getStartY())));
			}

		});
		this.startYProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				MethodCalling.this.arrow.setRotate(Math.toDegrees(Math.atan2(MethodCalling.this.getStartX() - MethodCalling.this.getEndX(), MethodCalling.this.getEndY() - MethodCalling.this.getStartY())));
			}

		});
		this.endXProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				MethodCalling.this.arrow.setRotate(Math.toDegrees(Math.atan2(MethodCalling.this.getStartX() - MethodCalling.this.getEndX(), MethodCalling.this.getEndY() - MethodCalling.this.getStartY())));
			}

		});
		this.endYProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				MethodCalling.this.arrow.setRotate(Math.toDegrees(Math.atan2(MethodCalling.this.getStartX() - MethodCalling.this.getEndX(), MethodCalling.this.getEndY() - MethodCalling.this.getStartY())));
			}

		});
	}
}
