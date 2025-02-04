

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application {
	private static Stage window;
	//Entry point !!!!
	@Override
	public void start(Stage primaryStage) {
		//creates window
			window = new Stage();
			window.getIcons().add(new Image(Main.class.getResource("images/pot.png").toExternalForm()));
			window.setTitle("Last of the Babylonians");
			window.setResizable(true);
			//window.setFullScreen(true);
			window.show();
			//opens scene
			ChangeScene(700,"menu.fxml");
	}
	
	//launches the JavaFX application
	public static void main(String[] args) {
		launch(args);
	}

	/**Changes scene to square scene from fxml source with width 'size'.  */
	public static void ChangeScene(int size, String fxml) {
		ChangeScene(size,size,fxml);
	}

	/**Changes to scene from fxml source with width and height as specified.*/
  public static void ChangeScene(int width, int height, String fxml) {
		try {
			window.hide();
			//retrieves fxml template from views folder
			String path = "views/" + fxml;
			FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(path));     		
			Parent root = (Parent)fxmlLoader.load();
			root.setId("pane");
			//creates scene from fxml with specified dimensions   
			Scene scene = new Scene(root,width,height);
			scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Pixelify+Sans:wght@400..700&family=Tiny5&display=swap%5C");	
			window.setScene(scene);
			window.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**Changes scene to square scene showing the level provided.*/
	public static void ShowLevel(int size, Level level) {
		ShowLevel(size, size, level);
	}

	/**Changes scene showing the level provided with width and height as specified.*/
	public static void ShowLevel(int width, int height , Level level) {
		try {
			window.hide();
			//retrieves fxml template from views folder
			String path = "views/game.fxml";
			FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(path));     
			Parent root = (Parent)fxmlLoader.load();
			//sets level attribute of controller
			GameController gc = fxmlLoader.getController();
			gc.setLevel(level);
			//creates scene from fxml with specified dimensions   
			Scene scene = new Scene(root,width,height);
			window.setScene(scene);
			window.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
