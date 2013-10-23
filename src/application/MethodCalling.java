package application;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class MethodCalling extends Line {
	
	private Method start;
	private Method end;
	
	public MethodCalling(Method start, Method end) {
		this.start = start;
		this.end = end;
	}
	
	public void delete() {
		this.start.removeMethodCall(this);
		this.end.removeMethodCall(this);
		((Pane) this.getParent()).getChildren().remove(this);
	}
}
