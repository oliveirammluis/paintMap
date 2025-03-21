package Grid;

import Painter.Player;
import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

public class Grid {

    private static final int CELL_SIZE = 40;  // Tamanho de cada célula
    public static final int COLS = 4;       // Número de colunas
    public static final int ROWS = 4;       // Número de linhas
    private static final int PADDING = 1;    // Margem da grid
    private int row;
    private int col;
    private Rectangle cell;


    public Grid() {
        drawGrid();    // Desenha a grid ao inicializar
    }


    public void drawGrid() {
        for (int row = 0; row < Grid.ROWS; row++) {
            for (int col = 0; col < Grid.COLS; col++) {
                // Desenhar bordas de cada célula
                Rectangle border = new Rectangle(Grid.getX(col), Grid.getY(row), Grid.getCellSize(), Grid.getCellSize());
                border.setColor(Color.BLACK); // Cor das bordas
                border.draw();  // Desenha a borda (não preenche, só desenha)
            }
        }
    }

    public void initialize() {
        drawGrid();
    }

    // Calcula a coordenada X com base na coluna
    public static int getX(int col) {
        return PADDING + col * CELL_SIZE;
    }

    // Calcula a coordenada Y com base na linha
    public static int getY(int row) {
        return PADDING + row * CELL_SIZE;
    }

    public static int getCellSize() {
        return CELL_SIZE;
    }
}


