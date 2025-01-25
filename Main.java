
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application {
	//Entry point !!!!

	private static Stage window;

	@Override
	public void start(Stage primaryStage) {
		//creates window
			window = new Stage();
			window.getIcons().add(new Image(Main.class.getResource("images/blackPawn.png").toExternalForm()));
			window.setTitle("Game Name");
			window.setResizable(true);
			window.show();
			//opens scene
			ChangeScene(350,"menu.fxml");
	}
	
	//launches the JavaFX application
	public static void main(String[] args) {
		launch(args);
	}

	/**Changes to scene to square scene from fxml source with width 'size'.  */
	public static void ChangeScene(int size, String fxml) {
		ChangeScene(size,size,fxml);
	}

	/**hanges to scene to square scene from fxml source with width and height as specified.*/
  public static void ChangeScene(int width, int height, String fxml) {
		try {
			window.hide();
			//retrieves fxml template from views folder
			String path = "views/" + fxml;
			FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(path));     
			Parent root = (Parent)fxmlLoader.load();
			//creates scene from fxml with specified dimensions   
			Scene scene = new Scene(root,width,height);
			window.setScene(scene);
			window.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
