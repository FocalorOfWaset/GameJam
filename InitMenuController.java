import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class InitMenuController {
	@FXML private Button exitBtn;
	
	@FXML protected void handleExitBtn(ActionEvent event) {
		Stage stage = (Stage) exitBtn.getScene().getWindow();
		stage.close();
	}
	@FXML protected void handleLoginBtn(ActionEvent event) {

	}
	@FXML protected void handleSignupBtn(ActionEvent event) {

	}
	@FXML protected void handleGuestBtn(ActionEvent event) {

	}
}
