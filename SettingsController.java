
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class SettingsController {
	@FXML protected void handleSaveBtn(ActionEvent event) {
		Main.ChangeScene(350,"menu.fxml");
	}
}
