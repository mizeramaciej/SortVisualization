import processing.core.PApplet;

class Grid {
    int gridSize;
    int[] grid;
    private int startX;
    private int startY;
    private int width;
    private int height;
    private boolean showText = false;

    private PApplet p;

    Grid(int gridSize, int startX, int startY, int width, int height, PApplet parent) {
        this.gridSize = gridSize;
        this.grid = new int[gridSize * gridSize];
        this.startX = startX;
        this.startY = startY;
        this.width = width;
        this.height = height;
        this.p = parent;
    }

    void swapCells(int first, int second) {
        int temp = grid[first];
        grid[first] = grid[second];
        grid[second] = temp;
    }

    void refreshGrid() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                refreshCell(i, j);
            }
        }
    }

    void refreshCell(int i, int j) {
        int cellColor = (int) PApplet.map(grid[i * gridSize + j], 0, gridSize * gridSize - 1, 0, 255);
        p.fill(cellColor);
        p.square(startX + j * (width / gridSize), startY + i * (height / gridSize), height / gridSize);

        if (showText) {
            p.textSize(12);
            p.fill(p.color(255, 0, 0));
            p.text(grid[i * gridSize + j], startX + j * (width / gridSize) + 15, startY + i * (height / gridSize) + 15);
            p.text(cellColor, j * (width / gridSize) + 15, i * (height / gridSize) + 30);
        }

    }
}
