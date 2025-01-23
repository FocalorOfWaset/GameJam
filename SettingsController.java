import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SettingsController {
	@FXML private Button saveBtn;
	
	@FXML protected void handleSaveBtn(ActionEvent event) {
		Main.OpenWindow(350,"menu.fxml",(Stage)saveBtn.getScene().getWindow());
	}

}
