// Written by Tunahan İbiş

// Important Notes:

// ******* PLEASE DON'T FORGET to add algs4.jar file to referenced libraries to use the importations in the code. ********

// Please put my WeightedQuickUnionFind.java file in the same directory with Percolation.java in order to use them together.
// I have submitted WeightedQuickUnion.java because you wanted us to use WeightedQuickUnion in the PDF, not WeightedQuickUnionUF.

// Explanation of Code:
// This code prints 10 problems with decreasing probability values and tells if the grid percolates or not.
// The closed sites are black. The open sites are blue if they have a connection with the virtualTop. If not, they are coloured white.
// In terminal, you can see the grid state and root representation of 10 grids.
// The "Percolation Grid State" part shows the sites according to their openness. It puts 1 if they are open and 0 if they are not.
// The "Root Representation" part shows the roots of the sites with numbers starting from 1. 
// If roots of the sites are same, they also have the same number representation. It means they are part of the same connected component.

// Importations:
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdDraw;
// You can import WeightedQuickUnionUF below from Princeton library instead of WeightedQuickUnionFind, it also works well with the code.
// import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.HashMap;
import java.util.Map;

public class Percolation {
    // Instance Variables:
    private boolean[][] grid; // Represent the grid. (row, col) is true if the site is open and it is false if the site is closed
    private static int gridSize; // The size of the grid
    private int gridSquared; // The number of sites in a grid
    private WeightedQuickUnionFind wquFind; // Weighted quick union-find object keeps track of all connected/opened sites
    private static int virtualTop; // Index of a virtual top in the size and parent arrays in WeightedQuickUnionFind
    private static int virtualBottom; // Index of a virtual bottom in the size and parent arrays in WeightedQuickUnionFind

    // Constructor:
    public Percolation(int n) {
        gridSize = n; // Set to n
        gridSquared = n * n; // Set to n square
        grid = new boolean[n][n]; // Create an n by n grid
        wquFind = new WeightedQuickUnionFind(gridSquared + 2); // Use wquFind for union-find data structure
        virtualTop = gridSquared; // Virtual top is set to n square
        virtualBottom = gridSquared + 1; // Virtual bottom is set to n square plus one.

        // Connect virtual top and virtual bottom sites to the respective rows in the grid
        for (int col = 0; col < n; col++) { // Search the columns to make connection
            wquFind.union(virtualTop, col); // Connect virtual top
            wquFind.union(virtualBottom, gridSquared - n + col); // Connect virtual bottom
        }
    }

    // PUBLIC METHODS:

    // Method to open a site at (row, col)
    public void openSite(int row, int col) {
        // Open if the site is closed. Otherwise do nothing.
        if (!grid[row][col]) { // Check if the site is closed
            grid[row][col] = true; // If the site is closed, open it

            int index = getIndex(row, col); // Get index of site

            // Connect to virtual top if opened site is in the first row
            if (row == 0) { // Check if the opened site is in the first row
                wquFind.union(virtualTop, index); // Connect to virtual top
            }

            // Connect to virtual bottom if opened site is in the last row
            if (row == gridSize - 1) { // Check if the opened site is in the last row
                wquFind.union(virtualBottom, index); // Connect to virtual bottom
            }

            // Connect opened site to any adjacent site that is open.
            connectAdjacents(row, col); // Connect to any adjacent site that is open
        }
    }

    // Method to open all sites with probability value p
    public void openAllSites(double p) {
        // Starting at the first site (0,0), one row at a time, open each site with probability value p
        for (int row = 0; row < gridSize; row++) { // Check all the rows
            for (int col = 0; col < gridSize; col++) { // Check all the columns
                // Using "bernoulli" to get random instead of "uniform" because it is deprecated by java.
                if (StdRandom.bernoulli(p)) { // Get a random p value
                    openSite(row, col); // Open the site
                }
            }
        }
    }

    // Method to check if there is a connection between the virtual top and virtual bottom
    public static boolean percolationCheck(boolean[][] grid) {
        int gridSize = grid.length; // Set grid size to grid length
        int virtualTop = gridSize * gridSize; // Set virtual top to grid length's square
        int virtualBottom = gridSize * gridSize + 1; // Set virtual bottom to one more of grid length's square

        // Call the wquFind and set it to two more of grid length's square
        WeightedQuickUnionFind wquFind = new WeightedQuickUnionFind(gridSize * gridSize + 2);

        // Connect virtual top and bottom sites to the respective rows in the grid
        for (int col = 0; col < gridSize; col++) {
            if (grid[0][col]) { // Connect the site in the first row to the virtual top
                int indexTopRow = col; // Index of the site in the first row
                wquFind.union(virtualTop, indexTopRow); // Connect virtual top
            }

            if (grid[gridSize - 1][col]) { // Connect the site in the last row to the virtual bottom
                int indexBottomRow = (gridSize - 1) * gridSize + col; // Index of the site in the last row
                wquFind.union(virtualBottom, indexBottomRow); // Connect virtual bottom
            }
        }

        // Connect opened site to any adjacent site that is open.
        int[] rowOffsets = { -1, 1, 0, 0 }; // Up and down directions
        int[] colOffsets = { 0, 0, -1, 1 }; // Left and right directions

        for (int row = 0; row < gridSize; row++) { // Check the rows
            for (int col = 0; col < gridSize; col++) { // Check the columns
                if (grid[row][col]) {
                    int index = row * gridSize + col; // Calculate the 1D index

                    // Connect to virtual top if opened site is in the first row
                    if (row == 0) { // Check if opened site is in the first row
                        wquFind.union(virtualTop, index); // Connect virtual top
                    }

                    // Connect to virtual bottom if opened site is in the last row
                    if (row == gridSize - 1) { // Check if opened site is in the last row
                        wquFind.union(virtualBottom, index); // Connect virtual bottom
                    }

                    // Connect opened site to any adjacent site that is open.
                    for (int i = 0; i < 4; i++) { // Check for sides of site
                        int newRow = row + rowOffsets[i]; // Update the row coordinates of an adjacent site
                        int newCol = col + colOffsets[i]; // Update the column coordinates of an adjacent site

                        if (isValid(newRow, newCol) && grid[newRow][newCol]) { // Check if the coordinates are matching
                            wquFind.union(getIndex(row, col), getIndex(newRow, newCol)); // Connect the adjacent site
                        }
                    }
                }
            }
        }
        // Return if the virtual top and virtual bottom is already connected
        return wquFind.find(virtualTop) == wquFind.find(virtualBottom);
    }

    // PRIVATE METHODS:

    // Additional private method to display grids using StdDraw
    private static void displayGrid(double x, double y, boolean[][] grid, WeightedQuickUnionFind wquFind,
            double probability, int cellSize, int padding, int problemNumber) {
        // Draw squares with appropriate colours
        for (int row = 0; row < grid.length; row++) { // Include all the rows
            for (int col = 0; col < grid[row].length; col++) { // Include all the columns
                double cellX = x + col * cellSize; // Calculate the x-coordinate for the center of the square
                double cellY = y + (grid.length - 1 - row) * cellSize; // Calculate the y-coordinate for the center of the square

                if (grid[row][col]) { // Check if the site is open
                    int index = getIndex(row, col, grid.length); // Get the index if the site is open

                    // Check if the site has a connection to the top
                    boolean hasConnectionToTop = wquFind.find(index) == wquFind.find(virtualTop);

                    // Set the color of the square based on whether it has a connection to the top
                    if (hasConnectionToTop) {
                        StdDraw.setPenColor(135, 206, 250); // Set light blue colour coordinates
                    } else {
                        StdDraw.setPenColor(255, 255, 255); // Set white colour coordinates
                    }

                    // Fill the square
                    StdDraw.filledSquare(cellX, cellY, cellSize / 2); // Fill the half of the side length with white
                } else {
                    StdDraw.setPenColor(0, 0, 0); // Set black colour coordinates
                    StdDraw.filledSquare(cellX, cellY, cellSize / 2); // Fill the half of the side length with black
                }
            }
        }

        // Draw the edges of sites in the grid with thin gray lines
        StdDraw.setPenColor(100, 100, 100); // Set dark gray colour coordinates
        StdDraw.setPenRadius(0.002); // Make the line thin
        for (int row = 0; row < grid.length; row++) { // Include all the rows
            for (int col = 0; col < grid[row].length; col++) { // Include all the columns
                // Draw a square structure to make the sites in grids more visible
                StdDraw.square(x + col * cellSize, y + (grid.length - 1 - row) * cellSize, cellSize / 2);
            }
        }

        // Draw the text above the grid
        StdDraw.setPenColor(0, 0, 255); // Set blue colour coordinates
        StdDraw.text(x + grid.length * cellSize / 2, y + grid.length * cellSize + 3 * padding,
                "Problem " + (problemNumber + 1)); // Display problem number
        StdDraw.setPenColor(255, 0, 0); // Set red colour coordinates
        StdDraw.text(x + grid.length * cellSize / 2, y + grid.length * cellSize + 2 * padding,
                String.format("p = %.1f", probability)); // Display probability value "p"
        StdDraw.text(x + grid.length * cellSize / 2, y + grid.length * cellSize + padding,
                // Display percolation status
                wquFind.find(virtualTop) == wquFind.find(virtualBottom) ? "Percolates" : "Doesn't Percolate");
    }

    // Additional private method to make a connection with the adjacent open sites
    private void connectAdjacents(int row, int col) {
        int[] rowOffsets = { -1, 1, 0, 0 }; // Up and down directions
        int[] colOffsets = { 0, 0, -1, 1 }; // Left and right directions

        // Look each corner of square to make a connection
        for (int i = 0; i < 4; i++) { // Check four sides of site
            int newRow = row + rowOffsets[i]; // Update the row coordinates
            int newCol = col + colOffsets[i]; // Update the column coordinates

            // Make a connection if the adjacent site is open
            if (isValid(newRow, newCol) && grid[newRow][newCol]) { // Check if the coordinates match
                wquFind.union(getIndex(row, col), getIndex(newRow, newCol)); // Connect the adjacent open site
            }
        }
    }

    // Additional private method to get index
    private static int getIndex(int row, int col, int gridSize) {
        return row * gridSize + col;
    }

    // Additional private method to get the 1D index from 2D coordinates
    private static int getIndex(int row, int col) {
        return row * gridSize + col;
    }

    // Additional private method to check if the indices are valid
    private static boolean isValid(int row, int col) {
        return row >= 0 && row < gridSize && col >= 0 && col < gridSize;
    }

    // Additional private method to store the grid's pattern and use it later
    private boolean[][] getGridCopy() {
        boolean[][] copyGrid = new boolean[gridSize][gridSize]; // Create the copyGrid object
        for (int row = 0; row < gridSize; row++) { // Check the rows
            // Copy the properties of grid
            System.arraycopy(grid[row], 0, copyGrid[row], 0, gridSize);
        }
        return copyGrid; // Copy the grid and write it to system memory
    }

    // Additional private method to display all grid problems in one frame
    private static void displayAllGridsInFrame(boolean[][][] allGrids, WeightedQuickUnionFind[] wquFinds,
            double[] probabilities) {
        int gridSize = allGrids[0].length; // Set the grid size
        int cellSize = 20; // Set the cell size
        int padding = 20; // Set the padding (spacing)
        int titleSpacing = 80; // Set the title spacing
        int rows = 2; // Set two rows to print the grids 5 by 5
        int cols = (int) Math.ceil((double) allGrids.length / rows); // Calculate the column number

        // Set up the canvas size and scale for StdDraw
        int canvasWidth = cols * (gridSize * cellSize + padding) + padding; // Set the canvas width for stdDraw
        int canvasHeight = rows * (2 * gridSize * cellSize + padding) + padding + titleSpacing; // Set the canvas height for stdDraw

        // Implement the height and weight values to stdDraw frame
        StdDraw.setCanvasSize(canvasWidth, canvasHeight);
        StdDraw.setXscale(0, canvasWidth);
        StdDraw.setYscale(0, canvasHeight);

        // Display the message at the top of the frame
        StdDraw.setPenColor(0, 0, 0); // Set black colour coordinates
        // Set the location and text properties
        StdDraw.text(canvasWidth / 2, canvasHeight - padding / 2,
                "Note: Please look at the terminal for grid states and root representations for these problems.");

        StdDraw.setPenColor(0, 0, 255); // Set blue colour coordinates
        // Set the location and text properties
        StdDraw.text(canvasWidth / 2, canvasHeight - padding - titleSpacing / 2, "PERCOLATION PROBLEMS");

        // Display each grid using StdDraw
        for (int i = 0; i < allGrids.length; i++) { // Use iterative to display 10 grids in one run
            double x = (i % cols) * (gridSize * cellSize + padding) + padding; // Calculate x-coordinate
            double y = canvasHeight - ((i / cols) + 1) * (2 * gridSize * cellSize + padding); // Calculate y-coordinate

            // Draw grid by displayGrid method using StdDraw
            displayGrid(x, y, allGrids[i], wquFinds[i], probabilities[i], cellSize, padding, i);
        }
    }

    // Additional private method to draw union processes in the terminal
    private static void drawUnionProcesses(int problemNumber, boolean[][] grid, WeightedQuickUnionFind wquFind) {
        int gridSize = grid.length; // Set the grid size

        // Print the visualization of the grid
        System.out.println("Percolation Grid State for Problem " + (problemNumber + 1) + ":");
        for (int row = 0; row < gridSize; row++) { // Check the rows
            for (int col = 0; col < gridSize; col++) { // Check the columns
                // Print 1 if the site is open and 0 if it is not
                System.out.print(grid[row][col] ? "1 " : "0 ");
            }
            System.out.println(); // Print the visualization of grid state
        }

        // Create a mapping from root to label
        Map<Integer, Integer> rootToLabel = new HashMap<>();

        // Find and print the root representation of the grid
        System.out.println("\nRoot Representation of Sites for Problem " + (problemNumber + 1) + ":");
        for (int row = 0; row < gridSize; row++) { // Check the rows
            for (int col = 0; col < gridSize; col++) { // Check the columns
                int index = getIndex(row, col); // Get the index of grid
                int root = wquFind.find(index); // Find the root of the site

                // If the root is not in the mapping, assign a new label
                rootToLabel.putIfAbsent(root, rootToLabel.size() + 1);

                // Print the label with appropriate spacing
                System.out.print(String.format("%3d ", rootToLabel.get(root)));
            }
            System.out.println(); // Print the visualization of root representation
        }
        System.out.println();
    }

    // Additional private method to get wquFind
    private WeightedQuickUnionFind getWquFind() {
        return wquFind;
    }

    // MAIN METHOD:

    // Method to create an instance of the Percolation class
    // Open sites with a given probability, display the grid, and print whether the system percolates
    public static void main(String[] args) {
        int gridSize = 8; // Set the grids to 8X8 format
        double probabilityIncrement = 0.1; // Set the increasing amount of the probability value
        int numberOfProblems = 10; // Set the number of problems you want to work on

        // Create objects for main
        boolean[][][] allGrids = new boolean[numberOfProblems][gridSize][gridSize];
        WeightedQuickUnionFind[] wquFinds = new WeightedQuickUnionFind[numberOfProblems];

        // Initialize the probability value from 1.0 and make it decrease grid by grid
        double probability = 1.0;
        for (int i = 0; i < numberOfProblems; i++) { // Use iterative to display 10 grids in one run
            // Create new percolation object after every change in probability value
            Percolation percolation = new Percolation(gridSize);
            percolation.openAllSites(probability); // Open all the sites according to the probability value "p"
            allGrids[i] = percolation.getGridCopy(); // Store the grid
            wquFinds[i] = percolation.getWquFind(); // Store the WeightedQuickUnionUF object

            // Use drawUnionProcess to display the grid state for problems
            drawUnionProcesses(i, allGrids[i], wquFinds[i]);

            // Calculate the updated probability value after every decreasement
            probability = Math.max(0.1, probability - probabilityIncrement);
        }

        // Display all grids in a single frame with decreasing probability values
        double[] probabilities = { 1.0, 0.9, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.1 }; // Set the probability values
        // Use displayAllGridsInFrame to print texts and grids in a single frame
        displayAllGridsInFrame(allGrids, wquFinds, probabilities);
    }
}
