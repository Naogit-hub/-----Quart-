package game;

import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*; 

public class Pieces {
    private Group root;
    private Group piece; // ピースを構成する要素の集合
    private Color c;
    private int posX, posY; // マスの左上の点
    private int objectSize = 0;
    private int[] features = new int[4];
    private Board board;
    private boolean moveable = true;

    Pieces(int shape, int size, int color, int surface, Group root, Board board) {
        features = new int[] { shape, size, color, surface };
        this.root = root;
        this.board = board;
        this.piece = new Group();

        /* group pieceに対してマウスイベントをくっつける */
        this.piece.setOnMousePressed(this::selectPiece);

        int i = 4 * size + 2 * color + 1 * surface;
        int j = shape;

        this.posX = 30 + 55 * i; // 駒の初期配置X
        this.posY = 280 + 55 * j; // 駒の初期配置Y

        this.root.getChildren().add(piece);
        // 駒の初期setはrootに入っている。移動した後はboardSet中に移動させる。

        drawPiece();
    }

    Pieces(Pieces p) {
        features = new int[] { p.getShape(), p.getSize(), p.getColor(), p.getSurface() };
        this.piece = new Group();
        drawPiece();
    }

    public void selectPiece(MouseEvent e) {
        /*　動かせる駒で、gameModeが0 (= 選択状態) */
        if (this.getMoveable() && this.board.getGameMode() == 0) {
            this.board.setSelectedPieces(this);
        }
    }

    public void drawPiece() {
        this.piece.getChildren().clear();
        Rectangle flame = new Rectangle(posX, posY, 40, 40);
        flame.setStroke(Color.LIGHTGREY); // マスの枠
        flame.setFill(Color.WHITE);
        piece.getChildren().add(flame);

        drawF_color(features[2]); // 色
        drawF_size(features[1]); // サイズ
        drawF_shape(features[0]); // 形
        drawF_surface(features[3]); // 点
    }

    public void drawF_shape(int i) {
        if (i == 0) { // 0->丸 1->四角
            Circle o = new Circle(20 + this.posX, 20 + this.posY, 20 - this.objectSize);
            o.setStroke(Color.BLACK);
            o.setFill(this.c);
            this.piece.getChildren().add(o);
        } else {
            Rectangle o = new Rectangle(this.posX + this.objectSize, this.posY + this.objectSize, 40 - 2 * this.objectSize,
                    40 - 2 * this.objectSize);
            o.setStroke(Color.BLACK);
            o.setFill(this.c);
            this.piece.getChildren().add(o);
        }
    }

    public void drawF_size(int i) {
        if (i == 0) {
            this.objectSize = 10; // 0->小
        } else {
            this.objectSize = 0; // 1->大
        }
    }

    public void drawF_color(int i) {
        if (i == 0) {
            this.c = Color.BLACK; // 0->黒
        } else {
            this.c = Color.ANTIQUEWHITE; // 1->白
        }
    }

    public void drawF_surface(int i) {
        if (i == 0) {
            Circle d = new Circle(this.posX + 20, this.posY + 20, 5); // 0->点
            d.setFill(Color.RED);
            this.piece.getChildren().add(d);
        }
    }

    public void setPosX(int update) {
        this.posX = update;
    }

    public void setPosY(int update) {
        this.posY = update;
    }

    public Group getPieceSet() {
        return this.piece;
    }

    public void setMoveable() {
        this.moveable = false;
    }

    public boolean getMoveable() {
        return this.moveable;
    }

    public int getShape() {
        return this.features[0];
    }

    public int getSize() {
        return this.features[1];
    }

    public int getColor() {
        return this.features[2];
    }

    public int getSurface() {
        return this.features[3];
    }

    public int[] getFeatures() {
        return this.features;
    } 
}
