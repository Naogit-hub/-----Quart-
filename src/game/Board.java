package game;

import javafx.scene.Group;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.*;

public class Board {
  public final int MAX_X = 4, MAX_Y = 4;
  private Pieces[][] board;
  private Group boardSet, sP;
  private Pieces selecetedPiece;
  private int gameMode; // 駒を選択0・設置1
  private Text message;

  Board(Group root) {
    this.sP = new Group();
    this.boardSet = new Group();
    this.message = new Text(250, 75, "相手に渡す駒を選んでください。\n選択中の駒↓");
    this.message.setFont(new Font(20));
    this.selecetedPiece = null;

    this.board = new Pieces[][] {
        { null, null, null, null },
        { null, null, null, null },
        { null, null, null, null },
        { null, null, null, null }
    };
    root.getChildren().add(sP);
    root.getChildren().add(boardSet);
    root.getChildren().add(message);
    boardSet.setOnMousePressed(this::selectSquare);
    drawBoard();
  }

  public void drawBoard() {
    this.selecetedPiece = null; 
    this.boardSet.getChildren().clear(); // 盤内のデータを全て消去。
    for (int y = 0; y < MAX_Y; y++) {
      for (int x = 0; x < MAX_X; x++) {
        Rectangle r = new Rectangle(60 * x + 10, 60 * y + 10, 50, 50);
        r.setStroke(Color.BLACK);
        r.setFill(Color.WHITE);
        boardSet.getChildren().add(r);
        if (this.board[y][x] != null) {
          boardSet.getChildren().add(this.board[y][x].getPieceSet());
          this.board[y][x].setPosX(60 * x + 15);
          this.board[y][x].setPosY(60 * y + 15);
          this.board[y][x].drawPiece();
        }
      }
    }
  }

  public void selectSquare(MouseEvent e) {
    int x, y;
    if (this.gameMode == 0 || this.selecetedPiece == null) {
      return;
    }
    // gameModeの実装で、駒を置けるタイミングで置く駒がない(null)ということがなくなった。
    else {
      for (int i = 0; i < MAX_Y; i++) {
        for (int j = 0; j < MAX_X; j++) {
          if ((60 * i + 10 <= e.getY() && e.getY() <= 60 * i + 60)
              && (60 * j + 10 <= e.getX() && e.getX() <= 60 * j + 60)
                && this.board[i][j] == null) { // 駒の置き場がある。
            y = i; // 選んだ盤のマス座標を
            x = j; // それぞれ格納

            setPiece(y, x, selecetedPiece);

            drawBoard();
            checkVic(y, x);
            sP.getChildren().clear();
            changeGameMode();
          }
        }
      }
    }
  }

  /* drawBoardにくっつける↓ */
  public void checkVic(int y, int x) {
    boolean a, b, c, d;
    a = checkLine(y, x, 1, 1, this.board[y][x].getFeatures());
    b = checkLine(y, x, 1, -1, this.board[y][x].getFeatures());
    c = checkLine(y, x, 0, 1, this.board[y][x].getFeatures());
    d = checkLine(y, x, 1, 0, this.board[y][x].getFeatures());
    if (a || b || c || d) {
      // WIN!!
      // System.out.println("win n n n n n n");
      // this.message.setText("WIN NNNNNNN");
      this.gameMode = 2;
    }
  }

  public boolean checkLine(int y, int x, int dy, int dx, int[] feature) {
    int[] count = { 0, 0, 0, 0 };
    for (int a = 0; a < MAX_X; a++) {
      for (int i = 1; i < MAX_X; i++) {
        count[a] += put(y + dy * i, x + dx * i, feature[a], a);
        count[a] += put(y - dy * i, x - dx * i, feature[a], a);
      }
      if (count[a] == 3) {
        return true;
      }
    }
  //  System.out.println("勝利条件はまだ満たしていない。");
    return false;
  }

  public int put(int y, int x, int f, int num) {
    if (0 <= y && y < MAX_Y && 0 <= x && x < MAX_X) {
      if (this.board[y][x] == null) return 0;
      else if (this.board[y][x].getFeatures()[num] == f)
        return 1;
    }
    return 0;
  }

  public void setPiece(int y, int x, Pieces p) {
    if (this.board[y][x] == null) {
      this.board[y][x] = p;// 盤の2次元配列に格納
      p.setMoveable();// 移動済みを明示
    }
  }

  public void setSelectedPieces(Pieces p) {
    this.selecetedPiece = p;
    Pieces pp = new Pieces(p);
    pp.setPosY(150);
    pp.setPosX(300);
    pp.drawPiece();

    sP.getChildren().add(pp.getPieceSet());
  }

  public void changeGameMode() {
    if (this.gameMode == 0) {
      this.gameMode = 1;
      this.message.setText("駒を置いてください。");
    } else if (this.gameMode == 1) {
      this.gameMode = 0;
      this.message.setText("相手に渡す駒を選んでください。\n選択中の駒↓");
    } else if (this.gameMode == 2) {
      this.message.setText("WIN NNNNNNN");
    }
  }

  public int getGameMode() {
    return this.gameMode;
  }

  public Group getBoardSet() {
    return this.boardSet;
  }

  public Pieces getSelectedPieces() {
    return this.selecetedPiece;
  }
}
