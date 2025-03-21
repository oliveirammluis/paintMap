package Painter;

import Grid.*;
import org.academiadecodigo.simplegraphics.graphics.*;
import org.academiadecodigo.simplegraphics.keyboard.*;

public class Player implements KeyboardHandler {
    private Rectangle player;
    private Keyboard keyboard;
    private boolean[][] gridState = new boolean[Grid.ROWS][Grid.COLS];
    Grid grid = new Grid();

    public Player() {
        this.keyboard = new Keyboard(this);
        drawPlayer(); // Desenha o player ao iniciar
        createKeyboardEvents();

    }

    void drawPlayer() {
        this.player = new Rectangle(Grid.getX(0), Grid.getY(0), 40, 40);
        player.setColor(Color.BLUE);
        player.fill();
    }

    public void createKeyboardEvents() {

        KeyboardEvent keyboardEventSpace = new KeyboardEvent();
        keyboardEventSpace.setKey(KeyboardEvent.KEY_SPACE);
        keyboardEventSpace.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(keyboardEventSpace);


        KeyboardEvent keyboardEventRight = new KeyboardEvent();
        keyboardEventRight.setKey(KeyboardEvent.KEY_RIGHT);
        keyboardEventRight.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(keyboardEventRight);

        KeyboardEvent keyboardEventLeft = new KeyboardEvent();
        keyboardEventLeft.setKey(KeyboardEvent.KEY_LEFT);
        keyboardEventLeft.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(keyboardEventLeft);

        KeyboardEvent keyboardEventUp = new KeyboardEvent();
        keyboardEventUp.setKey(KeyboardEvent.KEY_UP);
        keyboardEventUp.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(keyboardEventUp);

        KeyboardEvent keyboardEventDown = new KeyboardEvent();
        keyboardEventDown.setKey(KeyboardEvent.KEY_DOWN);
        keyboardEventDown.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(keyboardEventDown);
    }

    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        // Check the player's position to ensure it doesn't go out of bounds (0 to 14 for both X and Y)
        switch (keyboardEvent.getKey()) {
            case KeyboardEvent.KEY_LEFT:
                // Move left
                // Ensure the player is not at the leftmost edge (X > 0)
                if (player.getX() > Grid.getX(0)) {
                    player.translate(-Grid.getCellSize(), 0);
                }
                break;

            case KeyboardEvent.KEY_RIGHT:
                // Move right
                // Ensure the player is not beyond the rightmost edge (X < maximum X coordinate)
                if (player.getX() + Grid.getCellSize() < Grid.getX(Grid.COLS - 1) + Grid.getCellSize()) {
                    player.translate(Grid.getCellSize(), 0);
                }
                break;

            case KeyboardEvent.KEY_UP:
                // Move up
                // Ensure the player is not beyond the topmost edge (Y > 0)
                if (player.getY() > Grid.getY(0)) {
                    player.translate(0, -Grid.getCellSize());
                }
                break;

            case KeyboardEvent.KEY_DOWN:
                // Move down
                // Ensure the player is not beyond the bottommost edge (Y < maximum Y coordinate)
                if (player.getY() + Grid.getCellSize() < Grid.getY(Grid.ROWS - 1) + Grid.getCellSize()) {
                    player.translate(0, Grid.getCellSize());
                }
                break;

            case KeyboardEvent.KEY_SPACE:
                // Calculate which grid cell the player is in
                int playerRow = (player.getY() - Grid.getY(0)) / Grid.getCellSize();
                int playerCol = (player.getX() - Grid.getX(0)) / Grid.getCellSize();

                if (gridState[playerRow][playerCol]) {
                    // If filled, unfill (reset to white)
                    unfillCell(playerRow, playerCol);
                } else {
                    // If not filled, fill it
                    fillCell(playerRow, playerCol, GridColor.GREEN);
                }
                break;

        }

    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {

    }

    private void fillCell(int row, int col, GridColor gridColor) {
        // Create a rectangle that represents the grid cell
        Rectangle cell = new Rectangle(Grid.getX(col), Grid.getY(row), Grid.getCellSize(), Grid.getCellSize());
        cell.setColor(gridColor.getColor());  // Set the color to green (can be changed to other colors)
        cell.fill();  // Fill the cell with the color
        gridState[row][col] = true; // Mark the cell as filled
        grid.drawGrid();
    }

    private void unfillCell(int row, int col) {

        Rectangle cellUnFill = new Rectangle(Grid.getX(col), Grid.getY(row), Grid.getCellSize(), Grid.getCellSize());
        cellUnFill.setColor(Color.WHITE); // Set color to white (or default background)
        cellUnFill.fill();
        gridState[row][col] = false; // Mark the cell as unfilled
        grid.drawGrid();
    }

}