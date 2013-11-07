package application;

import javafx.beans.binding.NumberBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class AttributeAccess extends Line {

	private Method method;
	private Attribute attribute;

	private NumberBinding startX, startY;
	private NumberBinding endX, endY;
	
	private ChangeListener<Number> startXListener = new ChangeListener<Number>() {

		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			double edgeAngle = Math.atan2(AttributeAccess.this.getEndY()-AttributeAccess.this.getStartY(), AttributeAccess.this.getEndX()-newValue.doubleValue());
			AttributeAccess.this.setStartX(AttributeAccess.this.startX.doubleValue() + 20*Math.cos(edgeAngle));
			AttributeAccess.this.setStartY(AttributeAccess.this.startY.doubleValue() + 20*Math.sin(edgeAngle));
			if (Math.abs(Math.tan(edgeAngle)) < 1) {
				AttributeAccess.this.setEndY(AttributeAccess.this.endY.doubleValue() - 20*Math.signum(Math.sin(edgeAngle))*Math.abs(Math.tan(edgeAngle)));
			} else {
				AttributeAccess.this.setEndY(AttributeAccess.this.endY.doubleValue() - 20*Math.signum(Math.sin(edgeAngle)));
			}
			if (Math.abs(1/Math.tan(edgeAngle)) < 1) {
				AttributeAccess.this.setEndX(AttributeAccess.this.endX.doubleValue() - 20*Math.signum(Math.cos(edgeAngle))/Math.abs(Math.tan(edgeAngle)));
			} else {
				AttributeAccess.this.setEndX(AttributeAccess.this.endX.doubleValue() - 20*Math.signum(Math.cos(edgeAngle)));
			}
		}

	};
	
	private ChangeListener<Number> startYListener = new ChangeListener<Number>() {

		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			double edgeAngle = Math.atan2(AttributeAccess.this.getEndY()-newValue.doubleValue(), AttributeAccess.this.getEndX()-AttributeAccess.this.getStartX());
			AttributeAccess.this.setStartX(AttributeAccess.this.startX.doubleValue() + 20*Math.cos(edgeAngle));
			AttributeAccess.this.setStartY(AttributeAccess.this.startY.doubleValue() + 20*Math.sin(edgeAngle));
			if (Math.abs(Math.tan(edgeAngle)) < 1) {
				AttributeAccess.this.setEndY(AttributeAccess.this.endY.doubleValue() - 20*Math.signum(Math.sin(edgeAngle))*Math.abs(Math.tan(edgeAngle)));
			} else {
				AttributeAccess.this.setEndY(AttributeAccess.this.endY.doubleValue() - 20*Math.signum(Math.sin(edgeAngle)));
			}
			if (Math.abs(1/Math.tan(edgeAngle)) < 1) {
				AttributeAccess.this.setEndX(AttributeAccess.this.endX.doubleValue() - 20*Math.signum(Math.cos(edgeAngle))/Math.abs(Math.tan(edgeAngle)));
			} else {
				AttributeAccess.this.setEndX(AttributeAccess.this.endX.doubleValue() - 20*Math.signum(Math.cos(edgeAngle)));
			}
		}

	};

	private ChangeListener<Number> endXListener = new ChangeListener<Number>() {

		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			double edgeAngle = Math.atan2(AttributeAccess.this.getEndY()-AttributeAccess.this.getStartY(), newValue.doubleValue()-AttributeAccess.this.getStartX());
			AttributeAccess.this.setStartX(AttributeAccess.this.startX.doubleValue() + 20*Math.cos(edgeAngle));
			AttributeAccess.this.setStartY(AttributeAccess.this.startY.doubleValue() + 20*Math.sin(edgeAngle));
			if (Math.abs(Math.tan(edgeAngle)) < 1) {
				AttributeAccess.this.setEndY(AttributeAccess.this.endY.doubleValue() - 20*Math.signum(Math.sin(edgeAngle))*Math.abs(Math.tan(edgeAngle)));
			} else {
				AttributeAccess.this.setEndY(AttributeAccess.this.endY.doubleValue() - 20*Math.signum(Math.sin(edgeAngle)));
			}
			if (Math.abs(1/Math.tan(edgeAngle)) < 1) {
				AttributeAccess.this.setEndX(AttributeAccess.this.endX.doubleValue() - 20*Math.signum(Math.cos(edgeAngle))/Math.abs(Math.tan(edgeAngle)));
			} else {
				AttributeAccess.this.setEndX(AttributeAccess.this.endX.doubleValue() - 20*Math.signum(Math.cos(edgeAngle)));
			}
		}

	};
	
	private ChangeListener<Number> endYListener = new ChangeListener<Number>() {

		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			double edgeAngle = Math.atan2(newValue.doubleValue()-AttributeAccess.this.getStartY(), AttributeAccess.this.getEndX()-AttributeAccess.this.getStartX());
			AttributeAccess.this.setStartX(AttributeAccess.this.startX.doubleValue() + 20*Math.cos(edgeAngle));
			AttributeAccess.this.setStartY(AttributeAccess.this.startY.doubleValue() + 20*Math.sin(edgeAngle));
			if (Math.abs(Math.tan(edgeAngle)) < 1) {
				AttributeAccess.this.setEndY(AttributeAccess.this.endY.doubleValue() - 20*Math.signum(Math.sin(edgeAngle))*Math.abs(Math.tan(edgeAngle)));
			} else {
				AttributeAccess.this.setEndY(AttributeAccess.this.endY.doubleValue() - 20*Math.signum(Math.sin(edgeAngle)));
			}
			if (Math.abs(1/Math.tan(edgeAngle)) < 1) {
				AttributeAccess.this.setEndX(AttributeAccess.this.endX.doubleValue() - 20*Math.signum(Math.cos(edgeAngle))/Math.abs(Math.tan(edgeAngle)));
			} else {
				AttributeAccess.this.setEndX(AttributeAccess.this.endX.doubleValue() - 20*Math.signum(Math.cos(edgeAngle)));
			}
		}

	};
	
	public AttributeAccess(Method method, Attribute attribute) {
		this.method = method;
		this.attribute = attribute;
		this.getStrokeDashArray().addAll(5.0, 5.0);
	}

	public void delete() {
		this.method.removeAttributeAccess(this);
		this.attribute.removeAttributeAccess(this);
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
			double edgeAngle = Math.atan2(this.getEndY()-this.getStartY(), this.getEndX()-this.getStartX());
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
			double edgeAngle = Math.atan2(this.getEndY()-this.getStartY(), this.getEndX()-this.getStartX());
			this.setStartX(this.startX.doubleValue() + 20*Math.cos(edgeAngle));
			this.setStartY(this.startY.doubleValue() + 20*Math.sin(edgeAngle));
			this.setEndX(this.endX.doubleValue() - 20*Math.cos(edgeAngle));
			this.setEndY(this.endY.doubleValue() - 20*Math.sin(edgeAngle));
		}
	}
}
