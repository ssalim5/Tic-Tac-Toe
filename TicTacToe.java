import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.application.Platform;

/* Tic-Tac Toe Game; Project 2
 * By: Sakib Salim; CUNY ID: 23552937
 * CS 211; Professor Mahavadi
 * I first develop a hybrid class to fill the game board that will contain:
 * 	-Button that user will click on
 *  -Keep track of whose turn it is
 *  -Computer's random moves are included
 * In the second class I develop the GUI, and methods:
 *  -GridPane for the gameboard; Hbox as a sort of "Menu"; all laid out on a BorderPane
 *  -Methods are created to check for a winner, a tie, as well as to start a new game;
 */


//Custom class that will fill the tic-tac toe board
class GameButton {
	Button button = new Button();
	
    // 0 for Player X's turn; 1 for Player O's turn
    static int Turn = 0;

    GameButton() {
        button.setMinSize(100, 100);
        button.setOnAction(e -> {
        	//if the button is empty fill it, check if there is a winner or a tie and switch player turn;
        	//Turn 0 is X, Turn 1 is O
            if (button.getText().isEmpty()) {
            	if (Turn == 0) {
            		button.setText("X");
            		button.setDisable(true);
            		if (Pr22937.checkWin("X")) {
                		Pr22937.GameOver("X");
                		return;
            		}
            		else if(Pr22937.BoardFull()) {
                		Pr22937.GameOver("");
                		return;
            		}
                	if (Pr22937.gameMode == 11) {
                		if (Pr22937.BoardFull())
                    		return;
                    	Button randButton = Pr22937.boardbuttons[(int) (Math.random()*3)][(int) (Math.random()*3)].button();
                    	while (randButton.isDisabled())
                    		randButton = Pr22937.boardbuttons[(int) (Math.random()*3)][(int) (Math.random()*3)].button();
                    	randButton.setText("O");
                    	randButton.setDisable(true);
                    	if (Pr22937.checkWin("O"))
                    		Pr22937.GameOver("O");
                    	else if (Pr22937.BoardFull())
                    		Pr22937.GameOver("");
            			return;
            		}
            		ChangePlayer();
            		Turn = 1;
            		return;
            	}
            	if (Turn == 1) {
            		button.setText("O");
            		button.setDisable(true);
            		if (Pr22937.checkWin("O"))
                		Pr22937.GameOver("O");
            		else if(Pr22937.BoardFull())
                		Pr22937.GameOver("");
            		ChangePlayer();
            		Turn = 0;
            		return;
            	}
            }
        });
    }
    
    //Random active button is filled
    public void ComputerMoves() {
    	if (Pr22937.BoardFull())
    		return;
    	Button randButton = Pr22937.boardbuttons[(int) (Math.random()*3)][(int) (Math.random()*3)].button();
    	while (randButton.isDisabled())
    		randButton = Pr22937.boardbuttons[(int) (Math.random()*3)][(int) (Math.random()*3)].button();
    	randButton.setText("O");
    	randButton.setDisable(true);
    	if (Pr22937.checkWin("O"))
    		Pr22937.GameOver("O");
    	else if (Pr22937.BoardFull())
    		Pr22937.GameOver("");
    }
    
    //Change message on screen
    public void ChangePlayer() {
    	if (getTurn() == 0) {
    		Pr22937.activePlayer.setText("It is Player O's turn");
    	}
    	if (getTurn() == 1) {
    		Pr22937.activePlayer.setText("It is Player X's turn");
    	}
    }
    
    public static int getTurn() {
    	return Turn;
    }
    
    public Button button() {
        return button;
    }

    public void clear() {
        button.setText("");
        button.setDisable(false);
        Turn = 0;
    }
}


public class TicTacToe extends Application {
  
  //3x3 Array of GameButtons
  static GameButton[][] boardbuttons = new GameButton[3][3];
  
  //Message to tell you whose turn it is
  public static Label activePlayer = new Label();
  
  //10 is for Player vs. Player; 11 is for Computer vs. Player
  public static int gameMode = 10;

  public static void main(String[] args) {
	    launch(args);
}
   
  @Override 
  public void start(Stage primaryStage) {
	
	
	BorderPane root = new BorderPane();
    Button exit = new Button("Exit");
    Button play = new Button("New Game");
    Button Human = new Button("Player vs. Player");
    Button HumanvsComp = new Button ("Player vs. Computer");
     
    exit.setOnAction(e -> Platform.exit());
    
    play.setOnAction(e -> NewGame());
    
    //fill GridPane with GameButton
    GridPane pane = new GridPane(); 
    for (int row = 0; row < 3; row++)
      for (int col = 0; col < 3; col++) {
    	  boardbuttons[row][col] = new GameButton();
    	  boardbuttons[row][col].button().setStyle("-fx-font-size: 35pt;");
    	  pane.add(boardbuttons[row][col].button(), col, row);
    }
    
    //X Player starts by default
    activePlayer.setText("It is Player X's turn");
    
    root.setCenter(pane);
    root.setBottom(activePlayer);
    
    Human.setOnAction(e -> {
    	gameMode = 10;
    	NewGame();
    });
    
    HumanvsComp.setOnAction(e -> {
    	gameMode = 11;
    	NewGame();
    });
    
    HBox MenuButtons = new HBox();
    MenuButtons.setSpacing(5);
    MenuButtons.getChildren().addAll(play, exit, Human, HumanvsComp);
    root.setTop(MenuButtons);
    
    Scene scene = new Scene(root, 350, 350);
    primaryStage.setTitle("TicTacToe: Sakib Salim"); 
    primaryStage.setScene(scene); 
    primaryStage.show();
    
  }

  //Check if all buttons are filled
  public static boolean BoardFull() {
    for (int row = 0; row < 3; row++)
      for (int col = 0; col < 3; col++)
        if (boardbuttons[row][col].button().getText().isEmpty())
          return false;
    return true;
  }

  //Check if the player wins; Cover all possible cases: three matches in a row, a column or along a diagonal
  public static  boolean checkWin(String player) {  
    for (int row = 0; row < 3; row++)
      if (boardbuttons[row][0].button().getText() == player && boardbuttons[row][1].button().getText() == player
          && boardbuttons[row][2].button().getText() == player)
        return true;

    for (int col = 0; col < 3; col++)
      if (boardbuttons[0][col].button().getText() ==  player && boardbuttons[1][col].button().getText() == player
          && boardbuttons[2][col].button().getText() == player)
        return true;

    if (boardbuttons[0][0].button().getText() == player && boardbuttons[1][1].button().getText() == player        
        && boardbuttons[2][2].button().getText() == player)
      return true;

    if (boardbuttons[0][2].button().getText() == player && boardbuttons[1][1].button().getText() == player
        && boardbuttons[2][0].button().getText() == player)
      return true;

    return false;
  }
  
  //Disable all buttons; Display game result
  public static void GameOver(String victor) {
	  for (int row = 0; row < 3; row++) {
		  for (int col = 0; col < 3; col++) {
			  boardbuttons[col][row].button().setDisable(true);
		  }
	  }
	  Stage stage = new Stage();
	  Label label = new Label("It is a tie!");
	  Button newgame = new Button("New Game");
	  Button exit = new Button("Exit");
	  
	  newgame.setOnAction(e -> {
		  NewGame();
		  stage.close();
	  });
	  
	  exit.setOnAction(e -> {
		  Platform.exit();
		  stage.close();
	  });
	  
	  if (victor.equals("X") || victor.equals("O")) {
		  label.setText("Player " + victor + " has won!");
	  }
	  
      HBox layout = new HBox(10);
      layout.getChildren().addAll(label, newgame, exit);
      
      Scene scene = new Scene(layout, 225, 25);
      stage.setScene(scene);
      stage.show();
  }
  
  //Reset all the buttons;
  public static void NewGame() {
	  for (int row = 0; row < 3; row++) {
		  for (int col = 0; col < 3; col++) {
			  boardbuttons[col][row].clear();
		  }
	  }
	  activePlayer.setText("It is Player X's turn");
  }
}