package Painter;

import Grid.*;
import org.academiadecodigo.simplegraphics.graphics.*;
import org.academiadecodigo.simplegraphics.keyboard.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Player implements KeyboardHandler {
    private Rectangle player;
    private Keyboard keyboard;
    private boolean[][] gridState = new boolean[Grid.ROWS][Grid.COLS];
    Grid grid = new Grid();

    public Player() {
        this.keyboard = new Keyboard(this);
        drawPlayer(); // Draw player at start
        createKeyboardEvents();
    }

    void drawPlayer() {
        this.player = new Rectangle(Grid.getX(0), Grid.getY(0), 20, 20);
        player.setColor(Color.BLUE);
        player.fill();
    }

    public void createKeyboardEvents() {
        // Set up keyboard event listeners for different keys
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

        KeyboardEvent keyboardEventSave = new KeyboardEvent();
        keyboardEventSave.setKey(KeyboardEvent.KEY_S);
        keyboardEventSave.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(keyboardEventSave);

        KeyboardEvent keyboardEventLoad = new KeyboardEvent();
        keyboardEventLoad.setKey(KeyboardEvent.KEY_L);
        keyboardEventLoad.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(keyboardEventLoad);

        KeyboardEvent keyboardEventClear = new KeyboardEvent();
        keyboardEventClear.setKey(KeyboardEvent.KEY_C);
        keyboardEventClear.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(keyboardEventClear);

    }

    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        switch (keyboardEvent.getKey()) {
            case KeyboardEvent.KEY_LEFT:
                if (player.getX() > Grid.getX(0)) {
                    player.translate(-Grid.getCellSize(), 0);
                }
                break;

            case KeyboardEvent.KEY_RIGHT:
                if (player.getX() + Grid.getCellSize() < Grid.getX(Grid.COLS - 1) + Grid.getCellSize()) {
                    player.translate(Grid.getCellSize(), 0);
                }
                break;

            case KeyboardEvent.KEY_UP:
                if (player.getY() > Grid.getY(0)) {
                    player.translate(0, -Grid.getCellSize());
                }
                break;

            case KeyboardEvent.KEY_DOWN:
                if (player.getY() + Grid.getCellSize() < Grid.getY(Grid.ROWS - 1) + Grid.getCellSize()) {
                    player.translate(0, Grid.getCellSize());
                }
                break;

            case KeyboardEvent.KEY_SPACE:
                int playerRow = (player.getY() - Grid.getY(0)) / Grid.getCellSize();
                int playerCol = (player.getX() - Grid.getX(0)) / Grid.getCellSize();

                if (gridState[playerRow][playerCol]) {
                    unfillCell(playerRow, playerCol);
                } else {
                    fillCell(playerRow, playerCol, GridColor.GREEN);
                }
                break;

            case KeyboardEvent.KEY_S:
                saveGridState();  // Save the grid state when S is pressed
                break;

            case KeyboardEvent.KEY_L:
                loadGridState();  // Load the grid state when L is pressed
                break;

            case KeyboardEvent.KEY_C:
                clearGridState();  // Limpa a grade quando C é pressionado
                break;

        }
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {
        // No action needed here for now
    }

    private void fillCell(int row, int col, GridColor gridColor) {
        Rectangle cell = new Rectangle(Grid.getX(col), Grid.getY(row), Grid.getCellSize(), Grid.getCellSize());
        cell.setColor(gridColor.getColor());
        cell.fill();
        gridState[row][col] = true;
        grid.drawGrid();
    }

    private void unfillCell(int row, int col) {
        Rectangle cellUnFill = new Rectangle(Grid.getX(col), Grid.getY(row), Grid.getCellSize(), Grid.getCellSize());
        cellUnFill.setColor(Color.WHITE);  // Define a cor de fundo como branca
        cellUnFill.fill();
        gridState[row][col] = false;  // Marca a célula como não preenchida
        grid.drawGrid();
    }


    // Method to save the grid state to a file
    private void saveGridState() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("gridState.txt"))) {
            for (int row = 0; row < Grid.ROWS; row++) {
                for (int col = 0; col < Grid.COLS; col++) {
                    if (gridState[row][col]) {
                        writer.write(row + "," + col);
                        writer.newLine();  // New line after each position
                    }
                }
            }
            System.out.println("Grid state saved!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load the grid state from a file
    private void loadGridState() {
        try (BufferedReader reader = new BufferedReader(new FileReader("gridState.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] coordinates = line.split(",");
                int row = Integer.parseInt(coordinates[0]);
                int col = Integer.parseInt(coordinates[1]);
                fillCell(row, col, GridColor.GREEN);  // Fill cells based on saved state
            }
            System.out.println("Grid state loaded!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearGridState() {
        for (int row = 0; row < Grid.ROWS; row++) {
            for (int col = 0; col < Grid.COLS; col++) {
                if (gridState[row][col]) {
                    unfillCell(row, col);  // Desfaz o preenchimento de todas as células
                }
            }
        }
        grid.drawGrid();
        System.out.println("Grid state cleared!");
    }

}
