import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.BitSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class GameModel extends JPanel implements Runnable {


    KeyHandler keyHandler = new KeyHandler();
    MouseHandler mouseHandler = new MouseHandler();
    Thread gameThread;

    public int gameState = 1;
    public final int titleState = 0;
    public final int sandState = 1;

    //FPS
    int FPS = 60;
    int screenWidth = 800, screenHeight = 600;  //choose screenWidth and screenHeight here

    public GameModel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);
        mouseHandler.setup(rows, cols, pixelsPerGrid);
    }


    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    //How big(pixels) should every square on the grid be?
    int pixelsPerGrid = 10;

    //Do you want to draw the grid on the screen?
    boolean drawGrid = false;

    //How quick do you want the next generation of sand to be generated?
    int frameRefresh = 1;

    int rows = screenHeight / pixelsPerGrid;
    int cols = screenWidth / pixelsPerGrid;
    int mx;
    int my;
    int[][] nxtlst = new int[rows][cols]; //initializing an array will set all values to 0 automatically
    int sandSize = rows * cols;
    BitSet sand = new BitSet(sandSize); //Effectively using 1-D Array of Bits in Row Major Order
    Random sandMovement = new Random();



    public void update() {
        if (mouseHandler.mouseOnScreen) {
            mx = mouseHandler.mouseX;
            my = mouseHandler.mouseY;
        }

        for (int i : mouseHandler.newSand) {
            sand.set(i);
        }
        mouseHandler.newSand.clear();
        smoothSandPlacement();
        /*
        //variables for how much sand I want to drop
        int extent = 5;     //how big of a square do I want to drop the sand in? ex: 3 = 3x3
        int loopextent = extent / 2;    //how much will I need to go left & right from the mouse position
        int vertindex;       //TODO will use later
        Random r = new Random();

        if(mouseHandler.drawSand){
            int mouseCol = mx / pixelsPerGrid;
            int mouseRow = my / pixelsPerGrid;

            //Let's find out what index of the BitSet we're on
            int index = (mouseRow * cols) + mouseCol;


            for(int vert = 0 - loopextent; vert <= loopextent; vert++){
                vertindex = index - (vert * cols);
                for(int horiz = 0 - loopextent; horiz <= loopextent; horiz++){
                    if((r.nextInt(100) > 90)){
                        sand.set(vertindex + horiz);
                    }
                }
            }
            //TODO: loop through all indexes around the mouse based on extent size
            /*for(int i = -loopextent;i <= loopextent;i++){
                for(int j = -loopextent;j <= loopextent;j++){
                    int rand = r.nextInt(0, 100);   //random number from 1-99
                    int col = mouseCol + i; //find the right column
                    int row = mouseRow + j; //find the right row
                    if(withinRows(col) && withinCols(row) && rand < 50){    //if it's on the screen, 50% chance to spawn
                        mouseHandler.lst[col][row] = 1;
                    }
                }
            }
            */
    }

    public void update2(){
        //loop through all bits, going forwards for now
        for(int index = sandSize - 1; index >= 0; index--){
            //only do logic if the current bit has some sand in it
            if(sand.get(index)){
                int below = index + cols; //index of the bit of sand visually below
                //only do logic if below index isn't outside the array
                if(withinBounds(below)){
                    //if there is no sand right below, move sand down one
                    if(!sand.get(below)){
                        sand.flip(index);
                        sand.flip(below);
                        //there is sand below, we must check for sideways movement
                    } else{
                        //Get a random number 1 or -1
                        int rand = sandMovement.nextInt(2);
                        if(rand == 0){rand = -1;}

                        //below - rand will check right or left first depending on the random value
                        if(!sand.get(below - rand)){
                            sand.flip(index);
                            sand.flip(below - rand);
                        //below + rand will check whichever direction below - rand did not
                        } else if(!sand.get(below + rand)){
                            sand.flip(index);
                            sand.flip(below + rand);
                        }
                        // if there is nowhere to move sideways, we will simply go to the next bit
                    }
                }
            }
        }
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / FPS; //~0.01666seconds, or 1/60
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int frames = 0;

        while(gameThread!= null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval; //amount of time passed divided by drawInterval
            //once the amount of time passed has reached the drawInterval, then it will be >=1
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if(delta >= 1) {
                //Update stuff like character positions
                update();
                //Draw the screen with updated stuff
                repaint();
                //reset delta and increment frame count
                delta = 0;
                frames++;
                //Every other frame, update the new generation of sand
                if(frames % frameRefresh == 0){
                    update2();
                }
            }

            //if 1 second has passed, print information and reset frame counter and timer
            if(timer >= 1000000000) {
                System.out.println("FPS: " + frames);
                frames = 0;
                timer = 0;
            }

        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        if(gameState == sandState){
            for(int index = 0; index < sandSize; index++) {
                int column = index % cols;
                int row = index / cols;
                int xcord = column * pixelsPerGrid;
                int ycord = row * pixelsPerGrid;
                if (sand.get(index)) {
                    g2d.setColor(Color.getHSBColor(46, 70, 87));
                    g2d.fillRect(xcord, ycord, pixelsPerGrid, pixelsPerGrid);
                } else {
                    g2d.setColor(Color.black);
                    g2d.fillRect(xcord, ycord, pixelsPerGrid, pixelsPerGrid);
                }
            }
        }
        //dispose(clear up memory)
        g2d.dispose();
    }

    /**method mainly to clean-up code
     * @param index Integer
     * @return true if the integer is within row bounds, false if it goes outside of bounds
     */
    public boolean withinBounds(int index){
        return index >= 0 && index < sandSize;
    }

    public void smoothSandPlacement() {
        if (mouseHandler.drawSand) {
            int mouseCol = mx / pixelsPerGrid;
            int mouseRow = my / pixelsPerGrid;

            // Radius of the circular area around the mouse
            int radius = 3;  // You can adjust the radius as needed

            // Iterate over the circular area
            for (int row = -radius; row <= radius; row++) {
                for (int col = -radius; col <= radius; col++) {
                    int targetRow = mouseRow + row;
                    int targetCol = mouseCol + col;

                    // Check if the point (targetRow, targetCol) is within the bounds of the circle
                    if (targetRow >= 0 && targetRow < rows && targetCol >= 0 && targetCol < cols) {
                        double distance = Math.sqrt(row * row + col * col);
                        if (distance <= radius) {
                            // Randomize sand placement a bit to avoid a perfect circle
                            if (sandMovement.nextInt(100) > 90) {
                                int index = (targetRow * cols) + targetCol;
                                sand.set(index);
                            }
                        }
                    }
                }
            }
        }
    }
    public void updateThreads() {
        int numThreads = Runtime.getRuntime().availableProcessors(); // Get the number of available processors
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        // Split the work among threads
        for (int threadIndex = 0; threadIndex < numThreads; threadIndex++) {
            final int threadID = threadIndex; // Capture thread index for use in lambda
            executor.execute(() -> {
                int startRow = (rows / numThreads) * threadID;
                int endRow = (rows / numThreads) * (threadID + 1);

                // Update sand for the assigned rows
                for (int row = startRow; row < endRow; row++) {
                    for (int col = 0; col < cols; col++) {
                        int index = row * cols + col;
                        if (sand.get(index)) {
                            int below = index + cols; // index of the bit of sand visually below
                            if (withinBounds(below)) {
                                // Move sand down if there's no sand below
                                if (!sand.get(below)) {
                                    sand.flip(index);
                                    sand.flip(below);
                                } else {
                                    // Handle sideways movement
                                    int rand = sandMovement.nextInt(2);
                                    if (rand == 0) { rand = -1; }
                                    // Check left and right for sideways movement
                                    if (!sand.get(below - rand)) {
                                        sand.flip(index);
                                        sand.flip(below - rand);
                                    } else if (!sand.get(below + rand)) {
                                        sand.flip(index);
                                        sand.flip(below + rand);
                                    }
                                }
                            }
                        }
                    }
                }
            });
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
            // Wait for all threads to finish
        }
    }
}
