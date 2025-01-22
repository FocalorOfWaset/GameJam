
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
			OpenWindow(350,"initMenu.fxml",null);
		}
	
	public static void main(String[] args) {
		launch(args);
	}

  public static void OpenWindow(int sizex, String fxml,Stage stage) {
		//opens a window with size and fxml source and inserts game
    int sizey = sizex;
		Stage boardStage = new Stage();
		try {
			String path = fxml;
			FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(path));     
			Parent root = (Parent)fxmlLoader.load();   
			Scene scene = new Scene(root,sizex,sizey);
			boardStage.setTitle("Chess");			
			boardStage.setScene(scene);
			boardStage.getIcons().add(new Image(Main.class.getResource("images/blackPawn.png").toExternalForm()));
			boardStage.setResizable(true);
			boardStage.show();
			//closes stage if provided
			if(stage!=null) {
				stage.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
