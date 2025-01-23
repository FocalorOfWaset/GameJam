
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application {
	//Entry point !!!!
	@Override
	public void start(Stage primaryStage) {
			OpenWindow(350,"initMenu.fxml",null);
	}
	
	//launches the JavaFX application
	public static void main(String[] args) {
		launch(args);
	}

	/**Opens a square window of width 'size' */
	public static void OpenWindow(int size, String fxml) {
		OpenWindow(size,size,fxml,null);
	}

	/**Opens a square window of width 'size', and closes the window passed in */
	public static void OpenWindow(int size, String fxml, Stage stage) {
		OpenWindow(size,size,fxml,stage);
	}

	/**Opens a square window of width and height as specified */
	public static void OpenWindow(int width, int height, String fxml) {
		OpenWindow(width,height,fxml,null);
	}

	/**Opens a square window of width and height as specified, and closes the window passed in */
  public static void OpenWindow(int width, int height, String fxml, Stage stage) {
		//creates a new stage
		Stage window = new Stage();
		try {
			//retrieves fxml template from views folder
			String path = "views/" + fxml;
			FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(path));     
			Parent root = (Parent)fxmlLoader.load();   
			Scene scene = new Scene(root,width,height);
			window.setTitle("Game Name");			
			window.setScene(scene);
			window.getIcons().add(new Image(Main.class.getResource("images/blackPawn.png").toExternalForm()));
			window.setResizable(true);
			window.show();
			//closes stage if provided
			if(stage!=null) {
				stage.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
