import java.util.ArrayList;
import java.util.Random;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Parent;
import javafx.scene.paint.Color;

public abstract class TicTacToe extends Application {
	private boolean canContinue = true;
	private boolean cancelBotTurn = false;
	private Board[][] board = new Board[3][3];
	private ArrayList<Row> rows = new ArrayList<Row>();
	
	//creates the board, establishes the rows
	public Parent createBoard() {
		Pane window = new Pane();
		window.setPrefSize(600, 600);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Board newBoard = new Board();
				newBoard.setTranslateX(j * 200);
				newBoard.setTranslateY(i * 200);
				window.getChildren().add(newBoard);
				board[j][i] = newBoard;
			}
		}
		for (int x = 0; x < 3; x++) {
			rows.add(new Row(board[x][0], board[x][1], board[x][2]));
		}
		for (int y = 0; y < 3; y++) {
			rows.add(new Row(board[0][y], board[1][y], board[2][y]));
		}
		rows.add(new Row(board[0][0], board[1][1], board[2][2]));
		rows.add(new Row(board[2][0], board[1][1], board[0][2]));
		return window;
	}
	
	//setup for the board to be created
	//draws X's for user's turns and calls the bot's turn after if possible to continue the game
	//will also call a method which checks to see if the game will continue or not
	private class Board extends StackPane {
		private Text text = new Text();
		public Board() {
			Rectangle border = new Rectangle(200, 200);
			border.setFill(null);
			border.setStroke(Color.BLACK);
			text.setFont(Font.font(150));
			setAlignment(Pos.CENTER);
			getChildren().addAll(border, text);
			setOnMouseClicked(event -> {
				text.setText("X");
				gameState();
				if (cancelBotTurn == false) {
					botTurn();
					gameState();
				}
				cancelBotTurn = false;
			});
		}
		public String getValue() {
			return text.getText();
		}
	}
	
	//checks the game state to see if it should continue or if it should stop
	//once it has decided to stop the game, it calls the display to inform user the game is over
	//after it has stopped the game, it calls a method to start a new game
	private void gameState() {
		if (board[0][0].getValue().equals("X") || board[0][0].getValue().equals("O")) {
			if (board[0][1].getValue().equals("X") || board[0][1].getValue().equals("O")) {
				if (board[0][2].getValue().equals("X") || board[0][2].getValue().equals("O")) {
					if (board[1][0].getValue().equals("X") || board[1][0].getValue().equals("O")) {
						if (board[1][1].getValue().equals("X") || board[1][1].getValue().equals("O")) {
							if (board[1][2].getValue().equals("X") || board[1][2].getValue().equals("O")) {
								if (board[2][0].getValue().equals("X") || board[2][0].getValue().equals("O")) {
									if (board[2][1].getValue().equals("X") || board[2][1].getValue().equals("O")) {
										if (board[2][2].getValue().equals("X") || board[2][2].getValue().equals("O")) {
											canContinue = false;
											display();
											startNewGame();
										}
									}
								}
							}
						}
					}
				}
			}
		}
		for(Row row : rows) {
			if (row.isComplete()) {
				canContinue = false;
				display();
				startNewGame();
				break;
			}
		}
	}
	
	//handles if there are three in a row
		private class Row {
			private Board[] boards;
			public Row(Board...boards) {
				this.boards = boards;
			}
			public boolean isComplete() {
				 if (boards[0].getValue().isEmpty())
					 return false;
				 return boards[0].getValue().equals(boards[1].getValue()) && boards[0].getValue().equals(boards[2].getValue());
			 }
		}
	
	//displays a message informing the user that the game has ended and a new one will start
	private void display() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Game done");
		alert.setHeaderText(null);
		alert.setContentText("Clearing board and restarting");
		alert.showAndWait();
	}
		 
	//used to start a new game once the current game has ended
	private void startNewGame() {
		canContinue = true;
		cancelBotTurn = true;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j].text.setText("");
			}
		}
	}
	
	//method for the program's turns
	private void botTurn() {
		Random random = new Random();
		boolean occupied = true;
		while (occupied == true) {
			int r = random.nextInt(9);
			if (r == 0 && !(board[0][0].getValue().equals("X")) && !(board[0][0].getValue().equals("O"))) {
				board[0][0].text.setText("O");
				occupied = false;
			}
			else if (r == 1 && !(board[0][1].getValue().equals("X")) && !(board[0][1].getValue().equals("O"))) {
				board[0][1].text.setText("O");
				occupied = false;
			}
			else if (r == 2 && !(board[0][2].getValue().equals("X")) && !(board[0][2].getValue().equals("O"))) {
				board[0][2].text.setText("O");
				occupied = false;
			}
			else if (r == 3 && !(board[1][0].getValue().equals("X")) && !(board[1][0].getValue().equals("O"))) {
				board[1][0].text.setText("O");
				occupied = false;
			}
			else if (r == 4 && !(board[1][1].getValue().equals("X")) && !(board[1][1].getValue().equals("O"))) {
				board[1][1].text.setText("O");
				occupied = false;
			}
			else if (r == 5 && !(board[1][2].getValue().equals("X")) && !(board[1][2].getValue().equals("O"))) {
				board[1][2].text.setText("O");
				occupied = false;
			}
			else if (r == 6 && !(board[2][0].getValue().equals("X")) && !(board[2][0].getValue().equals("O"))) {
				board[2][0].text.setText("O");
				occupied = false;
			}
			else if (r == 7 && !(board[2][1].getValue().equals("X")) && !(board[2][1].getValue().equals("O"))) {
				board[2][1].text.setText("O");
				occupied = false;
			}
			else if (r == 8 && !(board[2][2].getValue().equals("X")) && !(board[2][2].getValue().equals("O"))) {
				board[2][2].text.setText("O");
				occupied = false;
			}
		}
	}
}