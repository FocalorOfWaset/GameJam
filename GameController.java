
import java.util.List;
import java.util.ResourceBundle;
import java.net.URL;
import javafx.beans.binding.NumberBinding;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
public class GameController implements Initializable {
	@FXML private Button exit;
	@FXML private Button start;
	@FXML private VBox root;
	@FXML private VBox rootbox;
	@FXML private HBox gameboxH;
	@FXML private VBox gameboxV;
	@FXML private GridPane grid;
	private HBox inventoryBox = null;
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
		//add inventory display in place of start button
		int index = this.root.getChildren().indexOf(this.start);
		this.root.getChildren().remove(this.start);
		this.inventoryBox = new HBox();
		this.inventoryBox.setAlignment(Pos.CENTER);
		this.root.getChildren().add(index, this.inventoryBox);
		//get width and height of gridpane
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
		//TODO change to key listeners for keystrokes or something (low priority)
		rootbox.getScene().setOnKeyPressed(e -> { 
				switch(e.getCode()) {
					case KeyCode.W: this.level.logKey(Direction.N); break;
					case KeyCode.A: this.level.logKey(Direction.E); break;
					case KeyCode.S: this.level.logKey(Direction.S); break;
					case KeyCode.D: this.level.logKey(Direction.W); break;
					case KeyCode.E: this.level.logKey(Direction.PICKUP); break;
					case KeyCode.Q: this.level.logKey(Direction.PUTDOWN); break;
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
		//start timer (gameloop)
		timer.start();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//runs when scene is set
		//binds grid width to grid height so grid remains a squareish
		NumberBinding binding = Bindings.min(gameboxH.widthProperty(), gameboxH.heightProperty());
		gameboxV.prefWidthProperty().bind(binding);
		gameboxV.prefHeightProperty().bind(binding);
		gameboxV.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		this.time = 0;	
	}

	/**Sets the level object and builds grid of width and height specified by level */
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

	/**retrieves an image from the images folder by name */
	private Image getResource(String url) {
		//TODO make 100 a constant here and in constraints
		Image img = new Image(getClass().getClassLoader().getResource("images/"+url + ".png").toExternalForm(), 100, 100, false, false);
		return img;
	}

	/**Removes image from the StackPane at row,col,position in stack*/
	private void removeImage(int row, int col, int position) {
		StackPane panel = panels[row][col];
		panel.getChildren().remove(position);
	}

	/**Adds image with name imgName  to the StackPane at row,col,position in stack*/
	//TODO do adding stuff with a posiiton properly
	private void addImage(int row, int col, String imgName, int position, boolean preserve) {
		StackPane panel = panels[row][col];
		ImageView view = new ImageView(getResource(imgName));
		if (!preserve) panel.getChildren().clear();
		if (position >= panel.getChildren().size()) {
			panel.getChildren().add(view);
		} else {
			panel.getChildren().add(position, view);
		}
  }

	/**Loads scenery when the level is first opened */
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
	public void updateBoard(List<Update> updates) {
		for(Update piece:updates) {
			if (piece.uptype == UpdateType.ADD_INV) {
				addToInventory(piece.zIndex, piece.image);
			} else if (piece.uptype == UpdateType.REMOVE_INV) {
				removeFromInventory(piece.zIndex);
			} else {
				if (piece.type == PieceType.SCENERY) {
					addImage(piece.y, piece.x, piece.image, piece.zIndex, true);
				} else if (piece.type == PieceType.ENTITY) {
					if (piece.uptype == UpdateType.REMOVE) {
						//remove piece if coords are negative
						removeImage(piece.y, piece.x, piece.zIndex);
					} else {
						addImage(piece.y, piece.x, piece.image, piece.zIndex, true);
					}
				}
			}
		}
	}

	public void addToInventory(int index, String image) {
		if (this.inventoryBox == null) return;
		this.inventoryBox.getChildren().add(index, new ImageView(getResource(image)));
	}

	public void removeFromInventory(int index) {
		if (this.inventoryBox == null) return;
		this.inventoryBox.getChildren().remove(index);
	}

	public void endGame() {
		timer.stop();
		int index = this.root.getChildren().indexOf(this.inventoryBox);
		this.root.getChildren().remove(this.inventoryBox);
		this.root.getChildren().add(index, new Text("You have won!"));
	}
}
