# Painter

This is a simple game where the player can move a rectangle over a grid, fill cells with a color, and save or load the grid state. The purpose of the project is to practice graphics handling using the SimpleGraphics library and integrate keyboard events for interaction.

## Features

- **Player Movement:** Use the arrow keys (up, down, left, right) to move the player across the grid.
- **Cell Fill:** Press the **Space** key to toggle the fill of the current cell (filled with green).
- **Clear Grid:** Press the **C** key to clear all filled cells.
- **Save Grid State:** Press the **S** key to save the current grid state to a text file.
- **Load Grid State:** Press the **L** key to load a previously saved grid state from a text file.

## Requirements

- **Java:** Ensure you have the JDK (Java Development Kit) installed to run the project.
- **SimpleGraphics Library:** Used for drawing the grid and player graphics. The library can be obtained from the official website or open source repositories.

## How to Run

1. Clone the repository:

   ```bash
   git clone https://github.com/oliveirammluis/paintMap.git
   ```

2. Compile the project:

   If you're using **Maven** or **Gradle**, run the appropriate command to build the project. Otherwise, use your IDE to compile the Java files.

3. Run the project:

   After compiling, run the `Painter` class to start the game.

   ```bash
   java Painter.Painter
   ```

4. Use the following keyboard controls to interact with the game:
   - **Arrow keys**: Move the player
   - **Space**: Fill/unfill the cell
   - **C**: Clear the grid
   - **S**: Save the grid state
   - **L**: Load the grid state

## Project Structure

### `Grid.java`
- Contains logic to draw the grid and calculate cell coordinates.

### `GridColor.java`
- Enum defining available colors for grid cells.

### `Player.java`
- Contains logic to control the player movement and interactions (fill, clear, save, load).

### `Painter.java`
- Contains the `main` method that initializes the grid and the player.

## License

This project is licensed under the [MIT License](https://opensource.org/licenses/MIT).