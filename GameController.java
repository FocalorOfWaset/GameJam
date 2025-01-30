
import java.util.List;
import java.util.ResourceBundle;
import java.net.URL;
import javafx.beans.binding.NumberBinding;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.VBox;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.animation.AnimationTimer;

/**Handles the screen while a level is underway.*/
//TODO add support for multiple layers
//TODO add support for inventory displaying
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
		int width = this.level.getWidth();
		int height = this.level.getHeight();
		grid.getChildren().clear();
    for (int row=0;row<height;row++) {
      for(int col=0;col<width;col++) {
				//creates stackpanes
        StackPane pane = new StackPane();
        panels[row][col] = pane;
				//color stackpanes in alternating green and white
        if (row % 2==0) {
        	if (col % 2==0) {
        		pane.setStyle("-fx-background-color: white");
        	} else {
        		pane.setStyle("-fx-background-color: green");
        	}
        } else {
        	if (col % 2!=0) {
        		pane.setStyle("-fx-background-color: white");
        	} else {
        		pane.setStyle("-fx-background-color: green");
        	}
        }  		
				//add stackpane to grid square
        grid.add(pane, col, row);
      }
    }
		//load in scenery
		this.loadScenery();
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
		//binds grid width to grid height so grid remains a square
		NumberBinding binding = Bindings.min(gameboxH.widthProperty(), gameboxH.heightProperty());
		gameboxV.prefWidthProperty().bind(binding);
		gameboxV.prefHeightProperty().bind(binding);
		gameboxV.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		this.time = 0;	
	}

	//TODO ensure grid is always centered in the window
	public void setLevel(Level level) {
		this.level = level;
		this.level.setUI(this);
		panels = new StackPane[this.level.getHeight()][this.level.getWidth()];
		//add column and row constraints
		GridPane.clearConstraints(this.grid);
		for (int col = 0; col < this.level.getWidth(); col++) {
			this.grid.getColumnConstraints().add(new ColumnConstraints(60, 100, 100, Priority.SOMETIMES, HPos.CENTER, true));
		}
		for (int row = 0; row < this.level.getHeight(); row++) {
			this.grid.getRowConstraints().add(new RowConstraints(60, 100, 100, Priority.SOMETIMES, VPos.CENTER, true));
		}
	}

	private Image getResource(String url) {
		//retrieves an image from the images folder by name
		Image piece = new Image(getClass().getClassLoader().getResource("images/"+url).toExternalForm(), 100, 100, false, false);
		return piece;
	}

	private void removeImage(int row, int col, int position) {
		StackPane panel = panels[row][col];
		panel.getChildren().remove(position);
	}

	/**Adds image with name imgName  to the StackPane at row,col*/
	private void addImage(int row, int col, String imgName, int position, boolean preserve) {
		StackPane panel = panels[row][col];
		ImageView view = new ImageView(getResource(imgName + ".png"));
		if (!preserve) panel.getChildren().clear();
		panel.getChildren().add(position, view);
  }

	/**For first time loading scenery from level */
	public void loadScenery() {
		//load on scenery images
		for (int row = 0; row<this.level.getHeight();row++) {
			for (int col = 0; col<this.level.getWidth();col++) {
				Scenery scene = this.level.scenery[row][col];
				addImage(row, col, scene.getImage(), scene.getZIndex(),  false);
			}
		} 
		//overlay entity images
		for (Entity ent : this.level.getAllEntities()) {
			addImage(ent.getY(), ent.getX(), ent.getImage(), ent.getZIndex(), true);
		}
	}

	/**Updates positions and images of altered game pieces */
	public void updateBoard(List<Gamepiece> updates) {
		for(Gamepiece piece:updates) {
			if (piece instanceof Scenery) {
				addImage(piece.getY(), piece.getX(), piece.getImage(), piece.getZIndex(), true);
			} else if (piece instanceof Entity) {
				if (piece.getX() == -1) {
					//remove piece if coords are negative
					removeImage(piece.getY(), piece.getX(), piece.getZIndex());
				} else {
					addImage(piece.getY(), piece.getX(), piece.getImage(), piece.getZIndex(), true);
				}
			}
		}
	}
}
