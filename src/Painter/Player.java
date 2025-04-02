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
    private int playerRow = 0;
    private int playerCol = 0;
    Grid grid = new Grid();

    public Player() {
        this.keyboard = new Keyboard(this);
        drawPlayer(); // Draw player at start
        createKeyboardEvents();
    }

    void drawPlayer() {
        int playerX = Grid.getX(0) + Grid.getCellSize() / 2 - 10;
        int playerY = Grid.getY(0) + Grid.getCellSize() / 2 - 10;

        this.player = new Rectangle(playerX, playerY, 20, 20);
        player.setColor(Color.BLUE);
        player.fill();
    }

    private void updatePlayerPosition(int row, int col) {
        this.playerRow = row;
        this.playerCol = col;
        centerPlayerInCell(row, col);
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
        int playerRow = (player.getY() - Grid.getY(0)) / Grid.getCellSize();
        int playerCol = (player.getX() - Grid.getX(0)) / Grid.getCellSize();

        switch (keyboardEvent.getKey()) {
            case KeyboardEvent.KEY_LEFT:
                if (playerCol > 0) {
                    playerCol--;
                }
                break;

            case KeyboardEvent.KEY_RIGHT:
                if (playerCol < Grid.COLS - 1) {
                    playerCol++;
                }
                break;

            case KeyboardEvent.KEY_UP:
                if (playerRow > 0) {
                    playerRow--;
                }
                break;

            case KeyboardEvent.KEY_DOWN:
                if (playerRow < Grid.ROWS - 1) {
                    playerRow++;
                }
                break;

            case KeyboardEvent.KEY_SPACE:
                if (gridState[playerRow][playerCol]) {
                    unfillCell(playerRow, playerCol); // Limpa célula
                } else {
                    fillCell(playerRow, playerCol, GridColor.GREEN); // Pinta a célula, retorna rectangulo
                    // filledCells guardar posição do player
                }
                break;

            case KeyboardEvent.KEY_S:
                saveGridState();  // Guarda estado da grid
                break;

            case KeyboardEvent.KEY_L:
                loadGridState();  // Carrega estado guardado
                break;

            case KeyboardEvent.KEY_C:
                clearGridState();  // Limpa a grid
                break;

        }
        centerPlayerInCell(playerRow, playerCol);
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {
        // No action needed here for now
    }

    private Rectangle fillCell(int row, int col, GridColor gridColor) {
        Rectangle cell = new Rectangle(Grid.getX(col), Grid.getY(row), Grid.getCellSize(), Grid.getCellSize());
        cell.setColor(gridColor.getColor());
        cell.fill();// Preenche célula
        gridState[row][col] = true; // Marca a célula como preenchida
        grid.drawGrid(); // Desenha a grid
        return cell;
    }

    private void unfillCell(int row, int col) {

        Rectangle cellUnFill = new Rectangle(Grid.getX(col), Grid.getY(row), Grid.getCellSize(), Grid.getCellSize());
        cellUnFill.setColor(Color.WHITE);  // Define a cor de fundo como branca
        cellUnFill.fill(); // cell.delete
        gridState[row][col] = false;  // Marca a célula como não preenchida
        grid.drawGrid();
        // drawPlayer();
    }


    // Método para guardar estado da grid
    private void saveGridState() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("gridState.txt"))) {
            for (int row = 0; row < Grid.ROWS; row++) {
                for (int col = 0; col < Grid.COLS; col++) {
                    if (gridState[row][col]) {
                        writer.write(row + "," + col);
                        writer.newLine();  // Nova linha após cada posição
                    }
                }
            }
            System.out.println("Grid state saved!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para carregar estado da grip guardado
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
        drawPlayer();
    }

    private void centerPlayerInCell(int row, int col) {
        // Calcula nova posição do player (centrado)
        int xPos = Grid.getX(col) + Grid.getCellSize() / 2 - 10;
        int yPos = Grid.getY(row) + Grid.getCellSize() / 2 - 10;
        // Move o player para nova célula
        player.translate(xPos - player.getX(), yPos - player.getY());

        // drawPlayer();
    }
}

