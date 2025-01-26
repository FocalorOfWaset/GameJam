
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LevelsController {
	@FXML private Button exitBtn;
	
	@FXML protected void handleExitBtn(ActionEvent event) {
		Main.ChangeScene(350, "menu.fxml");
	}
	
	@FXML protected void handleLevel1(ActionEvent event) {
		
	}

	@FXML protected void handleLevel2(ActionEvent event) {
		
	}
}
