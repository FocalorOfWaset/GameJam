
import java.util.List;
import java.util.ResourceBundle;
import java.net.URL;
import javafx.beans.binding.NumberBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
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

	public void setLevel(Level level) {
		this.level = level;
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
		Image piece = new Image(getClass().getResource("images/"+url).toExternalForm());
		return piece;
	}

	private void removeImage(int x, int y) {
		//TODO implement maybe by keeping track of order of images in piece/level/gamecontroller
	}

	/**Adds image with name imgName  to the StackPane at row,col*/
	//TODO allow specifying of position in image stack
	private void addImage(int row, int col, String imgName, PieceType type, boolean bottom, boolean preserve) {
		StackPane panel = panels[row][col];
		//place scenery as the bootom image
		ImageView view = new ImageView(getResource("images/" + imgName + ".png"));
		//scenery always goes at the bottom
		if (type == PieceType.SCENERY) bottom = true;
		//clear existing images
		if (!preserve) panel.getChildren().clear();
		if (bottom) {
			if (type == PieceType.ENTITY) {
				//place entity one above the scenery
				panel.getChildren().add(1, view); 
			} else if (type == PieceType.SCENERY) {
				//preserve the entities that lie above the scenery
				ObservableList<Node> children = FXCollections.observableArrayList();
				FXCollections.copy(children, panel.getChildren());
				panel.getChildren().clear(); 
				panel.getChildren().add(view);
				for (int i = 1; i < children.size();i++) {
					panel.getChildren().add(children.get(i));
				}
			}
		} else {
			//put image on top
			panel.getChildren().add(view);
		}
  }

	/**For first time loading scenery from level */
	public void loadScenery() {
		//load on scenery images
		for (int row = 0; row<this.level.getHeight();row++) {
			for (int col = 0; col<this.level.getWidth();col++) {
				Scenery scene = this.level.scenery[row][col];
				addImage(row, col, scene.getImage(), scene.type(), true, false);
			}
		} 
		//overlay entity images
		for (Entity ent : this.level.entities) {
			addImage(ent.getY(), ent.getX(), ent.getImage(), ent.type(), false, false);
		}
	}

	/**Updates positions and images of altered game pieces */
	public void updateBoard(List<UpdateSquare> coords) {
		for(UpdateSquare update:coords) {
			if (update.piece instanceof Scenery) {
				addImage(update.y, update.x, update.piece.getImage(), update.piece.type(), true, true);
			} else if (update.piece instanceof Entity) {
				if (update.x == -1) {
					//remove piece
					removeImage(update.y, update.x);
				}
				addImage(update.y, update.x, update.piece.getImage(), update.piece.type(), update.bottom, true);
			}
		}
	}
}
