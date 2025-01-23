import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuController {
	@FXML private Button exitBtn;
	
	@FXML protected void handleExitBtn(ActionEvent event) {
		Stage stage = (Stage) exitBtn.getScene().getWindow();
		stage.close();
	}
	
	@FXML protected void handleSettingsBtn(ActionEvent event) {
		Main.OpenWindow(350,"settings.fxml",(Stage)exitBtn.getScene().getWindow());
	}

	@FXML protected void handlePlayBtn(ActionEvent event) {
		Main.OpenWindow(700,"game.fxml",(Stage)exitBtn.getScene().getWindow());
	}
}
