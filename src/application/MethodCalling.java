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
	
	private NumberBinding startX, startY;
	private NumberBinding endX, endY;
	
	private ChangeListener<Number> startXListener = new ChangeListener<Number>() {

		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			double edgeAngle = Math.atan2(MethodCalling.this.getEndY()-MethodCalling.this.getStartY(), MethodCalling.this.getEndX()-newValue.doubleValue());
			MethodCalling.this.setStartX(MethodCalling.this.startX.doubleValue() + 20*Math.cos(edgeAngle));
			MethodCalling.this.setStartY(MethodCalling.this.startY.doubleValue() + 20*Math.sin(edgeAngle));
			MethodCalling.this.setEndX(MethodCalling.this.endX.doubleValue() - 20*Math.cos(edgeAngle));
			MethodCalling.this.setEndY(MethodCalling.this.endY.doubleValue() - 20*Math.sin(edgeAngle));
		}

	};
	
	private ChangeListener<Number> startYListener = new ChangeListener<Number>() {

		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			double edgeAngle = Math.atan2(MethodCalling.this.getEndY()-newValue.doubleValue(), MethodCalling.this.getEndX()-MethodCalling.this.getStartX());
			MethodCalling.this.setStartX(MethodCalling.this.startX.doubleValue() + 20*Math.cos(edgeAngle));
			MethodCalling.this.setStartY(MethodCalling.this.startY.doubleValue() + 20*Math.sin(edgeAngle));
			MethodCalling.this.setEndX(MethodCalling.this.endX.doubleValue() - 20*Math.cos(edgeAngle));
			MethodCalling.this.setEndY(MethodCalling.this.endY.doubleValue() - 20*Math.sin(edgeAngle));
		}

	};

	private ChangeListener<Number> endXListener = new ChangeListener<Number>() {

		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			double edgeAngle = Math.atan2(MethodCalling.this.getEndY()-MethodCalling.this.getStartY(), newValue.doubleValue()-MethodCalling.this.getStartX());
			MethodCalling.this.setStartX(MethodCalling.this.startX.doubleValue() + 20*Math.cos(edgeAngle));
			MethodCalling.this.setStartY(MethodCalling.this.startY.doubleValue() + 20*Math.sin(edgeAngle));
			MethodCalling.this.setEndX(MethodCalling.this.endX.doubleValue() - 20*Math.cos(edgeAngle));
			MethodCalling.this.setEndY(MethodCalling.this.endY.doubleValue() - 20*Math.sin(edgeAngle));
		}

	};
	
	private ChangeListener<Number> endYListener = new ChangeListener<Number>() {

		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			double edgeAngle = Math.atan2(newValue.doubleValue()-MethodCalling.this.getStartY(), MethodCalling.this.getEndX()-MethodCalling.this.getStartX());
			MethodCalling.this.setStartX(MethodCalling.this.startX.doubleValue() + 20*Math.cos(edgeAngle));
			MethodCalling.this.setStartY(MethodCalling.this.startY.doubleValue() + 20*Math.sin(edgeAngle));
			MethodCalling.this.setEndX(MethodCalling.this.endX.doubleValue() - 20*Math.cos(edgeAngle));
			MethodCalling.this.setEndY(MethodCalling.this.endY.doubleValue() - 20*Math.sin(edgeAngle));
		}

	};
	
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
		if (this.startX != null) {
			this.startX.removeListener(this.startXListener);
		}
		if (this.startY != null) {
			this.startY.removeListener(this.startYListener);
		}
		this.startX = startX;
		this.startY = startY;
		this.startX.addListener(this.startXListener);
		this.startY.addListener(this.startYListener);
		if (this.startX != null && this.endX != null) {
			double edgeAngle = Math.atan2(this.startX.doubleValue()-this.startY.doubleValue(), this.endX.doubleValue()-this.endY.doubleValue());
			this.setStartX(this.startX.doubleValue() + 20*Math.cos(edgeAngle));
			this.setStartY(this.startY.doubleValue() + 20*Math.sin(edgeAngle));
			this.setEndX(this.endX.doubleValue() - 20*Math.cos(edgeAngle));
			this.setEndY(this.endY.doubleValue() - 20*Math.sin(edgeAngle));
		}
	}

	public void bindEnd(NumberBinding endX, NumberBinding endY) {
		if (this.endX != null) {
			this.endX.removeListener(this.endXListener);
		}
		if (this.endY != null) {
			this.endY.removeListener(this.endYListener);
		}
		this.endX = endX;
		this.endY = endY;
		this.endX.addListener(this.endXListener);
		this.endY.addListener(this.endYListener);
		if (this.startX != null && this.endX != null) {
			double edgeAngle = Math.atan2(this.startX.doubleValue()-this.startY.doubleValue(), this.endX.doubleValue()-this.endY.doubleValue());
			this.setStartX(this.startX.doubleValue() + 20*Math.cos(edgeAngle));
			this.setStartY(this.startY.doubleValue() + 20*Math.sin(edgeAngle));
			this.setEndX(this.endX.doubleValue() - 20*Math.cos(edgeAngle));
			this.setEndY(this.endY.doubleValue() - 20*Math.sin(edgeAngle));
		}
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
