import processing.core.PApplet;


public class Main extends PApplet {

    private final int gridSize = 50;
    private final int fps = 60;
    private final int width = 1000;
    private final int height = 1000;
    private Grid bubbleGrid;
    private Grid insertionGrid;
    private Grid selectionGrid;
    private Grid quickGrid;
    private boolean isPaused = false;
    private boolean sorted = false;
    private int indexInsertion = 0;
    private int indexSelection = 0;
    private int begin = 0;
    private int end = gridSize*gridSize-1;

    public static void main(String[] args) {
        PApplet.main("Main");
    }

    public void settings() {
        size(width, height);
    }

    public void setup() {

        background(0);
//        frameRate(fps);
//        stroke(255);
        noStroke();
        strokeWeight(0);
        strokeCap(SQUARE);
//        noLoop();

        bubbleGrid = new Grid(gridSize, 0, 0, width / 2, height / 2, this);
        insertionGrid = new Grid(gridSize, 500, 0, width / 2, height / 2, this);
        selectionGrid = new Grid(gridSize, 0, 500, width / 2, height / 2, this);
        quickGrid = new Grid(gridSize, 500, 500, width / 2, height / 2, this);

        for (int i = 0; i < gridSize * gridSize; i++) {
            int randomInt = (int) (Math.random() * gridSize * gridSize);
            bubbleGrid.grid[i] = randomInt;
            insertionGrid.grid[i] = randomInt;
            selectionGrid.grid[i] = randomInt;
            quickGrid.grid[i] = randomInt;
        }

        bubbleGrid.refreshGrid();
        insertionGrid.refreshGrid();
        selectionGrid.refreshGrid();
        quickGrid.refreshGrid();
    }

    public void draw() {
        for (int i = 0; i < 20; i++) {
            bubbleSort(bubbleGrid);
            insertionSort(++indexInsertion, insertionGrid);
            selectionSort(indexSelection++, selectionGrid);
//        quickSort(quickGrid,0,quickGrid.grid.length-1);
        }
    }

    void quickSort(Grid grid, int begin, int end) {
        if (end <= begin) return;
        int pivot = partition(grid.grid, begin, end);
        quickSort(grid, begin, pivot-1);
        quickSort(grid, pivot+1, end);
        grid.refreshGrid();
    }

    int partition(int[] array, int begin, int end) {
        int pivot = end;

        int counter = begin;
        for (int i = begin; i < end; i++) {
            if (array[i] < array[pivot]) {
                int temp = array[counter];
                array[counter] = array[i];
                array[i] = temp;
                counter++;
            }
        }
        int temp = array[pivot];
        array[pivot] = array[counter];
        array[counter] = temp;

        return counter;
    }

    private void selectionSort(int i, Grid grid) {
        if (i < grid.grid.length) {
            int min = grid.grid[i];
            int minId = i;
            for (int j = i + 1; j < grid.grid.length; j++) {
                if (grid.grid[j] < min) {
                    min = grid.grid[j];
                    minId = j;
                    if(grid.grid[i]==min){
                        break;
                    }
                }
            }
            // swapping
            int temp = grid.grid[i];
            grid.grid[i] = min;
            grid.grid[minId] = temp;

            grid.refreshCell(i / grid.gridSize, i % grid.gridSize);
            grid.refreshCell(minId / grid.gridSize, minId % grid.gridSize);
        }

    }

    private void insertionSort(int i, Grid grid) {
        if (i < grid.grid.length) {
            int current = grid.grid[i];
            int j = i - 1;
            while (j >= 0 && current < grid.grid[j]) {
                grid.grid[j + 1] = grid.grid[j];
                j--;
            }
            grid.grid[j + 1] = current;
            grid.refreshGrid();
        }
    }

    private void bubbleSort(Grid grid) {
        if (!sorted) {
            sorted = true;
            for (int i = 0; i < gridSize * gridSize - 1; i++) {
                if (grid.grid[i] > grid.grid[i + 1]) {
                    grid.swapCells(i, i + 1);
                    sorted = false;
                    grid.refreshCell(i / gridSize, i % gridSize);
                    grid.refreshCell((i + 1) / gridSize, (i + 1) % gridSize);
                }
            }
        }
    }

    public void keyPressed() {
        if (key == 'p' || key == 'P') {
            if (isPaused) {
                loop();
                isPaused = false;
            } else {
                noLoop();
                isPaused = true;
            }
        }

    }

}
