package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	
	private static Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		Main.primaryStage = primaryStage;
		
		VBox root = new VBox();
		
		Parent menu = FXMLLoader.load(getClass().getResource("Menu.fxml"));
		VBox.setVgrow(menu, Priority.NEVER);
		
		Parent platformGround = FXMLLoader.load(getClass().getResource("PlatformGround.fxml"));
		VBox.setVgrow(platformGround, Priority.ALWAYS);

		root.getChildren().addAll(menu, platformGround);
		
		Scene scene = new Scene(root,400,400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Main.primaryStage.setScene(scene);
		Main.primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static void close() {
		Main.primaryStage.close();
	}
}
