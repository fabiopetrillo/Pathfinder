package application;

import javafx.scene.shape.Line;

public class AttributeAccess extends Line {

	public AttributeAccess() {
		this.getStrokeDashArray().addAll(5.0, 5.0);
	}
	
}
