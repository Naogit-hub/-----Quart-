package game;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.*;

public class Quarto extends Application {
  private Group root;
  private int initialSceneHeight = 400, initialSceneWidth = 500;
  private Board board;
  private Button btn;
  private int turnPlayer; //1P2P
  private Text player;

  @Override
  public void start(Stage st) throws Exception {
    root = new Group();
    Scene scene = new Scene(root, initialSceneWidth, initialSceneHeight, Color.rgb(255, 255, 255));
    st.setMaxHeight(initialSceneHeight);
    st.setMaxWidth(initialSceneWidth);
    st.setTitle("Quarto");
    st.setScene(scene);
    st.show();
    
    turnPlayer = 1;
    makeButton();
    textBoard();
    makeBoard();
    makePieces(); 
  }

  public static void main(String[] args) {
    launch(args);
  }

  public void makePieces() {
    for (int a = 0; a < 2; a++) {
      for (int b = 0; b < 2; b++) {
        for (int c = 0; c < 2; c++) {
          for (int d = 0; d < 2; d++) {
            new Pieces(a, b, c, d, root, board);
          }
        }
      }
    }
  }

  public void makeBoard() {
    board = new Board(root);
  }

  public void drawGame() {
    board.drawBoard();
  }

  public void makeButton(){
    btn = new Button("渡す駒を確定する。");
    btn.setOnAction(this::changeTurn);
    AnchorPane.setTopAnchor(btn, 210.0);
    AnchorPane.setLeftAnchor(btn, 265.0);

    AnchorPane anchorPane = new AnchorPane();
    anchorPane.getChildren().add(btn);
    this.root.getChildren().add(anchorPane);
  }

  public void changeTurn(ActionEvent a){  //駒を確定する。(= ターンが変わる)
    if(board.getGameMode() == 1){
      //System.out.println("aaaaaa");
      return;
    }
    if(board.getSelectedPieces() != null){
      if(this.turnPlayer == 1) {
        this.turnPlayer = 2;
        player.setText("2P の手番です。");
      //  System.out.println("j");
      }else if(this.turnPlayer == 2){
        this.turnPlayer = 1;
        player.setText("1P の手番です。");
      }
      this.board.changeGameMode();
    }
  }

  public void textBoard(){
    player = new Text(250, 50, "1P の手番です。");
    player.setFont(new Font(20));
    this.root.getChildren().add(player);
  }
}