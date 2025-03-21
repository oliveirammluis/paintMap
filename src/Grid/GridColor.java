package Grid;
import org.academiadecodigo.simplegraphics.graphics.Color;

public enum GridColor {
    WHITE(Color.WHITE),
    BLACK(Color.BLACK),
    GREEN(Color.GREEN);

    private Color color;

    GridColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
