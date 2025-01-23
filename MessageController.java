import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MessageController implements Initializable {
	@FXML private Button closeBtn;
	@FXML private Text text;
	public static String displayText = "";
	
	@FXML private void handleClose(ActionEvent event) {
		Stage stage = (Stage) closeBtn.getScene().getWindow();
		stage.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//displays message
		text.setText(displayText);
	}
}
