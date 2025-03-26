package Grid;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

public class Grid {

    private static final int CELL_SIZE = 40;  // Size of each cell
    public static final int COLS = 4;        // Number of columns
    public static final int ROWS = 4;        // Number of rows
    private static final int PADDING = 1;    // Grid padding
    private int row;
    private int col;
    private Rectangle cell;

    public Grid() {
        drawGrid();    // Draw the grid when initialized
    }

    public void drawGrid() {
        for (int row = 0; row < Grid.ROWS; row++) {
            for (int col = 0; col < Grid.COLS; col++) {
                // Draw borders for each cell
                Rectangle border = new Rectangle(Grid.getX(col), Grid.getY(row), Grid.getCellSize(), Grid.getCellSize());
                border.setColor(Color.BLACK); // Border color
                border.draw();  // Draw the border (not filled)
            }
        }
    }

    public void initialize() {
        drawGrid();
    }

    // Calculate the X coordinate based on the column
    public static int getX(int col) {
        return PADDING + col * CELL_SIZE;
    }

    // Calculate the Y coordinate based on the row
    public static int getY(int row) {
        return PADDING + row * CELL_SIZE;
    }

    public static int getCellSize() {
        return CELL_SIZE;
    }
}
