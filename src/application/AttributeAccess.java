package application;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class AttributeAccess extends Line {

	private Method method;
	private Attribute attribute;
	
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
}
