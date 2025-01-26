
import java.util.ResourceBundle;
import java.net.URL;
import javafx.beans.binding.NumberBinding;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.animation.AnimationTimer;

/**Handles the screen while a level is underway.
 * Currently has example methods that showcase a grid, adding images to the grid, handling clicks and keystrokes,
 * and the animation timer.
 */
public class GameController implements Initializable {
	@FXML private Button exit;
	@FXML private Button start;
	@FXML private VBox rootbox;
	@FXML private HBox gameboxH;
	@FXML private VBox gameboxV;
	@FXML private GridPane grid;
	//keeps track of grid of panes 
	private StackPane panels[][];
	private AnimationTimer timer;
	private int time;
	private Level level;
	
	@FXML protected void handleExitBtn(ActionEvent event) {
		timer.stop();
		Main.ChangeScene(350,"menu.fxml");
	}

	@FXML protected void handleStartBtn(ActionEvent event) {
		//TODO vary grid size based on level data
		int width = 8;
		int height = 8;
		grid.getChildren().clear();
    for (int i=0;i<width;i++) {
      for(int j=0;j<height;j++) {
				//creates stackpanes
        StackPane pane = new StackPane();
        panels[i][j] = pane;
				//color stackpanes in alternating green and white
        if (i % 2==0) {
        	if (j % 2==0) {
        		pane.setStyle("-fx-background-color: white");
        	} else {
        		pane.setStyle("-fx-background-color: green");
        	}
        } else {
        	if (j % 2!=0) {
        		pane.setStyle("-fx-background-color: white");
        	} else {
        		pane.setStyle("-fx-background-color: green");
        	}
        }  		
				//add stackpane to grid square
        grid.add(pane, j, i);
      }
    }
		//add pawn image to stackpane
		panels[0][1].getChildren().add(new ImageView(getResource("blackPawn.png")));	
		//add keyboard event handler to scene 
		rootbox.getScene().setOnKeyPressed(e -> { 
				switch(e.getCode()) {
					case KeyCode.W: this.level.logKey(Direction.N);
					case KeyCode.A: this.level.logKey(Direction.E);
					case KeyCode.S: this.level.logKey(Direction.S);
					case KeyCode.D: this.level.logKey(Direction.W);
					default: ;
				}
		});
		//create animation timer
		timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				level.queryEntities(time);
				time = (time + 1) % 60;
			}
		};
		timer.start();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//runs when scene is set
		panels = new StackPane[9][9];
		//binds grid width to grid height so grid remains a square
		NumberBinding binding = Bindings.min(gameboxH.widthProperty(), gameboxH.heightProperty());
		gameboxV.prefWidthProperty().bind(binding);
		gameboxV.prefHeightProperty().bind(binding);
		gameboxV.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		this.time = 0;
		this.level = new Level(this);	
	}

	private Image getResource(String url) {
		//retrieves an image from the images folder by name
		Image piece = new Image(getClass().getResource("images/"+url).toExternalForm());
		return piece;
	}

	public void updateBoard() {
		//will need to be passed location data somehow
	}
}
