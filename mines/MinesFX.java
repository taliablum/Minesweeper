package mines;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class MinesFX extends Application {

	private static Mines game;
	private static BoardButton[][] buttons;
	private static Background background1=new Background(new BackgroundFill(Color.PALEGREEN, null, null));

	@Override
	public void start(Stage Stage) throws Exception {
		AnchorPane root;
		GridPane MygameBoard;
		MyController controller;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Stage.fxml"));
		root = loader.load();
		controller = loader.getController();
		MygameBoard = makeGameBoard(10, 10, 10);
		controller.getStack().getChildren().add(MygameBoard);
	
		Scene scene = new Scene(root);
		Stage.setScene(scene);
		Stage.setResizable(false);
		Stage.setTitle("The Amazing Mines Sweeper");
		Stage.show();
	}
	//building the game board over the Grid pane.
	public static GridPane makeGameBoard(int Height, int Width, int NumOfMines) {

		GridPane g = new GridPane();
		game = new Mines(Height, Width, NumOfMines); //Initializing game 
		buttons = new BoardButton[Height][Width]; 
		for (int i = 0; i < Height; i++) {
			for (int j = 0; j < Width; j++) {
				Button b = new Button(game.get(i, j));
				b.setMaxSize(50,50);
				b.autosize();
				b.setBackground(background1);
			    b.setStyle("-fx-border-color: GREEN; -fx-border-width: 0.5px;-fx-border-radius: 3px;");
				Font font = Font.font("Amasis MT Pro Black", FontWeight.BOLD, 20);
				b.setFont(font);
				buttons[i][j] = new BoardButton(i, j, b);
				b.setOnMouseClicked(buttons[i][j]);  //setting mouse action
				g.add(b, j, i);
			}

		}
		g.setAlignment(Pos.CENTER);
		g.setPadding(new Insets(10));
		g.autosize();
		return g;
	}
	
	//making buttons while implementing handler to handle button 
	private static class BoardButton implements EventHandler<MouseEvent> {

		private int coorX, coorY,count=0;
		private Button b;
		private Background background2=new Background(new BackgroundFill(Color.BISQUE, null, null));

		public BoardButton(int coorX, int coorY, Button b) {
			this.coorX = coorX;
			this.coorY = coorY;
			this.b = b;
		}

		@Override
		public void handle(MouseEvent event) {
			
			if (event.getButton()==MouseButton.SECONDARY) {
				game.toggleFlag(coorX, coorY);
				b.setText(game.get(coorX, coorY));
				b.setTextFill(Color.DARKORANGE);
				
				if(++count==1)
					b.setBackground(background2);
				else
				{
					b.setBackground(background1);
					b.setTextFill(Color.BLACK);
					count=0;
				}
				
				
			}
			else {
				if (game.open(coorX, coorY)) {
					for (BoardButton[] butt : buttons)
						for (BoardButton button : butt) {//open the buttons that needs to be open
							button.b.setText(game.get(button.coorX, button.coorY));
							
							if(!game.get(button.coorX, button.coorY).equals("."))
								button.b.setBackground(background2);
							
							getBkind(button.b.getText(),button);
						}

					if (game.isDone()) {
						MyController.messege(AlertType.CONFIRMATION, "Victory","Congratulations, You Won!");
						
					}
				} else {//in case of mine been clicked.
					game.setShowAll(true);
					for (BoardButton[] butt : buttons)
						for (BoardButton button : butt) {
							button.b.setText(game.get(button.coorX, button.coorY));
							if(!game.get(button.coorX, button.coorY).equals("."))
								button.b.setBackground(background2);
				
							getBkind(button.b.getText(),button);
						}
					MyController.messege(AlertType.CONFIRMATION,"Defeat", "Oh no, you LOST!!!");
				}

			}

		}
		
		private void getBkind(String kind,BoardButton butt)
		{
			
			if(butt.b.getText().equals("1"))
				butt.b.setTextFill(Color.CORNFLOWERBLUE);
			
			
			else if(butt.b.getText().equals("2"))
				butt.b.setTextFill(Color.GREEN);
			
			
			else if(butt.b.getText().equals("3"))
				butt.b.setTextFill(Color.CRIMSON);
			
			else if(butt.b.getText().equals("4"))
				butt.b.setTextFill(Color.PURPLE);
			
			else if(butt.b.getText().equals(" "))
				butt.b.disarm();

		}

	}

	public static void main(String[] args) {
		launch(args);
	}
}
