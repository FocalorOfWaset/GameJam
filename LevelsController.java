
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LevelsController {
	@FXML private Button exitBtn;
	private FileHandler files;
	
	@FXML protected void handleExitBtn(ActionEvent event) {
		Main.ChangeScene(750, "menu.fxml");
	}
	
	//TODO load levels from list in folder, not just two hardcoded ones
	@FXML protected void handleLevel1(ActionEvent event) {
		this.files = new FileHandler();
		Level one = this.files.readLevel("one");
		Main.ShowLevel(700, one);
	}

	@FXML protected void handleLevel2(ActionEvent event) {
		this.files = new FileHandler();
		Level two = this.files.readLevel("two");
		Main.ShowLevel(700, two);
	}
}
