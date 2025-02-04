
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class SettingsController {
	@FXML protected void handleSaveBtn(ActionEvent event) {
		Main.ChangeScene(700,"menu.fxml");
	}
}
