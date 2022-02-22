package mines;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Window;

public class MyController {
	private int width, height, mines;
	@FXML
	private AnchorPane root;

	@FXML
	private GridPane menu;

	@FXML
	private TextField widthTextField;

	@FXML
	private TextField heightTextField;

	@FXML
	private TextField minesTextField;

	@FXML
	private StackPane stack;

	public StackPane getStack() {
		return stack;
	}

	public void setStack(StackPane stack) {
		this.stack = stack;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getMines() {
		return mines;
	}

	@FXML
	void ResetButtonPress(ActionEvent event) {
		//get text field numbers 
		width = Integer.valueOf(widthTextField.getText());
		height = Integer.valueOf(heightTextField.getText());
		mines = Integer.valueOf(minesTextField.getText());
		if (mines > height * width)
			messege(AlertType.ERROR, "Error", "Too many mines entered,please try again.");
		else {
			GridPane gameBoard = MinesFX.makeGameBoard(width, height, mines);
			stack.getChildren().setAll(gameBoard);
			Window myWindow = ((Node) event.getSource()).getScene().getWindow();
			myWindow.sizeToScene();
			myWindow.centerOnScreen();

		}

	}
	//prints a message on an alert window
	public static Optional<ButtonType> messege(AlertType warrning, String pageTitle, String text) {
		Alert alert2 = new Alert(warrning);
		alert2.setTitle(pageTitle);
		alert2.setHeaderText(text);
		return alert2.showAndWait();
	}
}
