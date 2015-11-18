package boardview;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import model.Position;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.geometry.Insets;
import javafx.scene.layout.CornerRadii;
/**
 * View class for a tile on a chess board
 * A tile should be able to display a chess piece
 * as well as highlight itself during the game.
 *
 * @author <Yourname here>
 */
public class TileView implements Tile {
    private Position p;
    private StackPane stack;
    private Label label;
    private Rectangle rectangle;
    /**
     * Creates a TileView with a specified position
     * @param p
     */
    public TileView(Position p) {
        this.p = p;
        rectangle = new Rectangle(50, 50);
        label = new Label("");
        label.setFont(new Font(35.0));
        stack = new StackPane(rectangle, label);
        rectangle.setFill(Color.GREEN);
        //stack.setMinSize(80.0,  80.0);
        //stack.setMaxSize(80.0, 80.0);
        stack.setBackground(new Background(new BackgroundFill(Color.WHITE,
            CornerRadii.EMPTY, Insets.EMPTY)));
        if ((p.getRow() + p.getCol()) % 2 == 1) {
            stack.setBackground(new Background(new BackgroundFill(Color.GRAY,
                CornerRadii.EMPTY, Insets.EMPTY)));
        }
    }


    @Override
    public Position getPosition() {
        return p;
    }


    @Override
    public Node getRootNode() {
        return stack;
    }

    @Override
    public void setSymbol(String symbol) {
        label.setText(symbol);
    }


    @Override
    public String getSymbol() {
        return label.getText();
    }

    @Override
    public void highlight(Color color) {
        rectangle.setFill(color);
    }

    @Override
    public void clear() {
        rectangle.setFill(Color.TRANSPARENT);

    }
}
