import java.util.ResourceBundle;
import java.net.URL;
import javafx.beans.binding.NumberBinding;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;


public class GameController implements Initializable {
	@FXML private Button exit;
	@FXML private Button start;
	@FXML private VBox rootbox;
	@FXML private HBox gameboxH;
	@FXML private VBox gameboxV;
	@FXML private GridPane grid;
	//keeps track of grid of panes 
	private StackPane panels[][];
	private int along = 0;
	private int up = 1;
	
	@FXML protected void handleExitBtn(ActionEvent event) {
		Main.OpenWindow(350,"menu.fxml",(Stage)exit.getScene().getWindow());
	}

	@FXML protected void handleStartBtn(ActionEvent event) {
		grid.getChildren().clear();
    for (int i=0;i<8;i++) {
      for(int j=0;j<8;j++) {
        StackPane pane = new StackPane();
        panels[i][j] = pane;
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
        pane.setOnMouseClicked(this::handleSquareClick);
        grid.add(pane, j, i);
      }
    }
		panels[0][1].getChildren().add(new ImageView(getResource("blackPawn.png")));	
		rootbox.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				KeyCode code = e.getCode();
		if (code == KeyCode.DOWN) {
			movePawn(0, 1);
		} else if (code == KeyCode.UP) {
			movePawn(0,-1);
		} else if (code == KeyCode.LEFT) {
			movePawn(-1,0);
		} else if (code == KeyCode.RIGHT) {
			movePawn(1,0);
		}
			}
		});
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		panels = new StackPane[9][9];
		NumberBinding binding = Bindings.min(gameboxH.widthProperty(), gameboxH.heightProperty());
		gameboxV.prefWidthProperty().bind(binding);
		gameboxV.prefHeightProperty().bind(binding);
		gameboxV.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);	
	}

	protected void handleSquareClick(MouseEvent event) {
		//get position of button
		StackPane panel;
		try {
			ImageView imgView = (ImageView) event.getTarget();
			panel = (StackPane) imgView.getParent();
		}
		catch (Exception ex) {
			panel = (StackPane) event.getTarget();
		}
		int[] position = {GridPane.getRowIndex(panel),GridPane.getColumnIndex(panel)};
		panels[position[0]][position[1]].getChildren().clear();
		panels[position[0]][position[1]].getChildren().add(new ImageView(getResource("blackPawn.png")));
	}

	private Image getResource(String url) {
		Image piece = new Image(getClass().getResource("images/"+url).toExternalForm());
		return piece;
	}

	private void movePawn(int i, int j) {
		panels[along][up].getChildren().clear();
		along += i;
		up += j;
		panels[along][up].getChildren().add(new ImageView(getResource("blackPawn.png")));
	}
}
