package application;

import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
	
	private static Stage primaryStage;
	private static PlatformGround platformGround;
	
	private static int edgeNumbers = 0;
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		Main.primaryStage = primaryStage;
		Main.primaryStage.setTitle("Software Pathfinder Editor");
		
		VBox root = new VBox();
		
		Parent menu = FXMLLoader.load(getClass().getResource("Menu.fxml"));
		VBox.setVgrow(menu, Priority.NEVER);
		
		HBox content = new HBox();
		VBox.setVgrow(content, Priority.ALWAYS);

		Main.platformGround = new PlatformGround();
		HBox.setHgrow(Main.platformGround, Priority.ALWAYS);

		Parent toolbar = FXMLLoader.load(getClass().getResource("Toolbar.fxml"));
		HBox.setHgrow(toolbar, Priority.NEVER);

		content.getChildren().addAll(Main.platformGround, toolbar);

		root.getChildren().addAll(menu, content);
		
		Scene scene = new Scene(root,400,400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Main.primaryStage.setScene(scene);
		Main.primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static void newDiagram() {
		Main.edgeNumbers = 0;
		Main.platformGround.newDiagram();
	}
	
	public static void loadDiagram() {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("ProjectDiagram.txt"));
			String line = null;
			ArrayList<LoadedLine> hierarchyTree = new ArrayList<LoadedLine>();
			line = reader.readLine();
			while ((line = reader.readLine()) != null) {
				ArrayList<Integer> calls = new ArrayList<Integer>();
				ArrayList<Integer> callings = new ArrayList<Integer>();
				ArrayList<Integer> accesses = new ArrayList<Integer>();
				String[] parts = line.split("\\s");
				int level = Integer.valueOf(parts[0]);
				String type = parts[1];
				String name = parts[2];
				for (int index = 3; index < parts.length ; ++index) {
					name += " "+parts[index];
				}
				if (parts[1].equals("method")) {
					line = reader.readLine();
					parts = line.split("\\s");
					for (int index = 0; index < parts.length ; ++index) {
						if (!parts[index].equals("")) {
							int methodCallingNumber = Integer.valueOf(parts[index]);
							calls.add(methodCallingNumber);
						}
					}
					line = reader.readLine();
					parts = line.split("\\s");
					for (int index = 0; index < parts.length ; ++index) {
						if (!parts[index].equals("")) {
							int methodCallingNumber = Integer.valueOf(parts[index]);
							callings.add(methodCallingNumber);
						}
					}
					line = reader.readLine();
					if (line != null) {
						parts = line.split("\\s");
						for (int index = 0; index < parts.length ; ++index) {
							if (!parts[index].equals("")) {
								int attributeAccessNumber = Integer.valueOf(parts[index]);
								accesses.add(attributeAccessNumber);
							}
						}
					}
				} else if (parts[1].equals("attribute")) {
					line = reader.readLine();
					if (line != null) {
						parts = line.split("\\s");
						for (int index = 0; index < parts.length ; ++index) {
							if (!parts[index].equals("")) {
								int attributeAccessNumber = Integer.valueOf(parts[index]);
								accesses.add(attributeAccessNumber);
							}
						}
					}
				}
				LoadedLine loadedLine = new LoadedLine(level, type, name, calls, callings, accesses);
				hierarchyTree.add(loadedLine);
			}
			Main.newDiagram();
			Main.createDiagram(hierarchyTree, hierarchyTree);
		} catch (IOException e) {
			// TODO
		}
	}
	
	public static void createDiagram(ArrayList<LoadedLine> hierarchyTree, ArrayList<LoadedLine> hierarchyTree2) {
		HashMap<Integer, Parent> currentParent = new HashMap<>();
		HashMap<Integer, MethodCalling> methodCallList = new HashMap<>();
		HashMap<Integer, AttributeAccess> attributeAccessList = new HashMap<>();
		int currentLevel = 0;
		currentParent.put(-1, Main.platformGround);
		for (int index = 0; index < hierarchyTree.size(); ++index) {
			LoadedLine line = hierarchyTree.get(index);
			currentLevel = line.getLevel();
			if (line.getType().equals("package")) {
				if (currentParent.get(currentLevel-1).getClass().equals(PlatformGround.class)) {
					Package newPackage = ((PlatformGround) currentParent.get(currentLevel-1)).addPackage();
					newPackage.setName(line.getName());
					currentParent.put(line.getLevel(), newPackage);
				} else if (currentParent.get(currentLevel-1).getClass().equals(Package.class)) {
					Package newPackage = ((PlatformGround) currentParent.get(currentLevel-1)).addPackage();
					newPackage.setName(line.getName());
					currentParent.put(line.getLevel(), newPackage);
				}
			} else if (line.getType().equals("class")) {
				if (currentParent.get(currentLevel-1).getClass().equals(PlatformGround.class)) {
					Class newClass = ((PlatformGround) currentParent.get(currentLevel-1)).addClass();
					newClass.setName(line.getName());
					currentParent.put(line.getLevel(), newClass);
				} else if (currentParent.get(currentLevel-1).getClass().equals(Package.class)) {
					Class newClass = ((Package) currentParent.get(currentLevel-1)).addClass();
					newClass.setName(line.getName());
					currentParent.put(line.getLevel(), newClass);
				} else if (currentParent.get(currentLevel-1).getClass().equals(Class.class)) {
					Class newClass = ((Class) currentParent.get(currentLevel-1)).addClass();
					newClass.setName(line.getName());
					currentParent.put(line.getLevel(), newClass);
				}
			} else if (line.getType().equals("method")) {
				if (currentParent.get(currentLevel-1).getClass().equals(Class.class)) {
					Method newMethod = ((Class) currentParent.get(currentLevel-1)).addMethod();
					newMethod.setName(line.getName());
					for (int callNumber : line.getCalls()) {
						if (methodCallList.containsKey(callNumber)) {
							newMethod.addMethodCall(methodCallList.get(callNumber));
							methodCallList.get(callNumber).setStart(newMethod);
						} else {
							MethodCalling newMethodCalling = new MethodCalling(newMethod, null);
							newMethod.addMethodCall(newMethodCalling);
							methodCallList.put(callNumber, newMethodCalling);
						}
					}
					for (int callNumber : line.getCallings()) {
						if (methodCallList.containsKey(callNumber)) {
							newMethod.addMethodCalling(methodCallList.get(callNumber));
							methodCallList.get(callNumber).setEnd(newMethod);
						} else {
							MethodCalling newMethodCalling = new MethodCalling(null, newMethod);
							newMethod.addMethodCall(newMethodCalling);
							methodCallList.put(callNumber, newMethodCalling);
						}
					}
					for (int accessNumber : line.getAccesses()) {
						if (attributeAccessList.containsKey(accessNumber)) {
							newMethod.addAttributeAccess(attributeAccessList.get(accessNumber));
							attributeAccessList.get(accessNumber).setStart(newMethod);
						} else {
							AttributeAccess newAttributeAccess = new AttributeAccess(newMethod, null);
							newMethod.addAttributeAccess(newAttributeAccess);
							attributeAccessList.put(accessNumber, newAttributeAccess);
						}
					}
				}
			} else if (line.getType().equals("attribute")) {
				if (currentParent.get(currentLevel-1).getClass().equals(Class.class)) {
					Attribute newAttribute = ((Class) currentParent.get(currentLevel-1)).addAttribute();
					newAttribute.setName(line.getName());
					for (int accessNumber : line.getAccesses()) {
						if (attributeAccessList.containsKey(accessNumber)) {
							newAttribute.addAttributeAccess(attributeAccessList.get(accessNumber));
							attributeAccessList.get(accessNumber).setEnd(newAttribute);
						} else {
							AttributeAccess newAttributeAccess = new AttributeAccess(null, newAttribute);
							newAttribute.addAttributeAccess(newAttributeAccess);
							attributeAccessList.put(accessNumber, newAttributeAccess);
						}
					}
				}
			}
		}
		for (MethodCalling methodCall : methodCallList.values()) {
			Main.platformGround.addMethodCalling(methodCall);
			methodCall.updateBindings();
			methodCall.createArrow();
		}
		for (AttributeAccess attributeAccess : attributeAccessList.values()) {
			Main.platformGround.addAttributeAccess(attributeAccess);
			attributeAccess.updateBindings();
		}
	}
	
	public static void saveDiagram() {
		String saveString = "";
		ObservableList<Node> saveContent = Main.platformGround.getContent();
		for (Node content : saveContent) {
			if (!(content instanceof Polygon) && !(content instanceof MethodCalling)) {
				saveString += Main.recursiveSave(content, 0);
			}
		}
		PrintWriter writer;
		try {
			writer = new PrintWriter("ProjectDiagram.txt", "UTF-8");
			writer.print(saveString);
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO 
		}
	}
	
	public static String recursiveSave(Node content, int level) {
		String returnString = "";
		if (content.getId() != null) {
			returnString += "\r\n"+level+" ";
			if (content.getId().equals("theClass")) {
				returnString += "class " + ((Text) ((AnchorPane) ((VBox) content).getChildren().get(0)).getChildren().get(0)).getText();
				ObservableList<Node> nodeChildren = ((Pane) ((VBox) content).getChildren().get(1)).getChildren();
				for (Node child : nodeChildren) {
					returnString += Main.recursiveSave(child, level+1);
				}
			} else if (content.getId().equals("thePackage")) {
				ObservableList<Node> nodeChildren = ((Pane) ((VBox) content).getChildren().get(0)).getChildren();
				for (int index = 0 ; index < nodeChildren.size() ; ++index) {
					if (nodeChildren.get(index).getClass() == TextField.class) {
						returnString += "package " + ((TextField) nodeChildren.get(index)).getText();
					}
				}
				for (int index = 0 ; index < nodeChildren.size() ; ++index) {
					if (nodeChildren.get(index).getClass() != TextField.class) {
						returnString += Main.recursiveSave(nodeChildren.get(index), level+1);
					}
				}
			} else if (content.getId().equals("theMethod")) {
				returnString += "method " + ((Method) content).getName() + "\r\n";
				for (MethodCalling call : ((Method) content).getMethodCalls()) {
					if (call.getNumber() == -1) {
						call.setNumber(Main.edgeNumbers);
						Main.edgeNumbers++;
					}
					returnString += call.getNumber() + " ";
				}
				returnString += "\r\n";
				for (MethodCalling call : ((Method) content).getMethodCallings()) {
					if (call.getNumber() == -1) {
						call.setNumber(Main.edgeNumbers);
						Main.edgeNumbers++;
					}
					returnString += call.getNumber() + " ";
				}
				returnString += "\r\n";
				for (AttributeAccess access : ((Method) content).getAttributeAccesses()) {
					if (access.getNumber() == -1) {
						access.setNumber(Main.edgeNumbers);
						Main.edgeNumbers++;
					}
					returnString += access.getNumber() + " ";
				}
			} else if (content.getId().equals("theAttribute")) {
				returnString += "attribute " + ((Attribute) content).getName() + "\r\n";
				for (AttributeAccess access : ((Attribute) content).getAttributeAccesses()) {
					if (access.getNumber() == -1) {
						access.setNumber(Main.edgeNumbers);
						Main.edgeNumbers++;
					}
					returnString += access.getNumber() + " ";
				}
			}
		}
		return returnString;
	}
	
	public static void close() {
		Main.primaryStage.close();
	}

	public static void exportAsImage() {
		WritableImage snapshot = new WritableImage((int) Main.platformGround.getWidth(), (int) Main.platformGround.getHeight());
		snapshot = Main.platformGround.snapshot(null, snapshot);
		RenderedImage renderedImage = SwingFXUtils.fromFXImage(snapshot, null);
		File file = new File("snapshot.png");
		try {
			ImageIO.write(renderedImage, "png", file);
		} catch (IOException e) {
			// TODO
		}
	}
}
