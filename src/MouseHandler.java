import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class MouseHandler implements MouseListener, MouseMotionListener {
    boolean drawSand, mouseOnScreen;
    int mouseX, mouseY;
    int numRows, numColumns, pixelsPerGrid;
    int[][] lst;
    HashSet<Integer> newSand = new HashSet<>();

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            drawSand = true;
            mouseX = e.getX();
            mouseY = e.getY();
            if(mouseOnScreen) {
                int row = mouseY / pixelsPerGrid;
                int col = mouseX / pixelsPerGrid;
                int index = (row * numColumns) + col;
                newSand.add(index);
            }

        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            mouseX = e.getX();
            mouseY = e.getY();
            drawSand = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseOnScreen = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseOnScreen = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //if(e.getButton() == MouseEvent.BUTTON1) {
            mouseX = e.getX();
            mouseY = e.getY();
            if(drawSand && mouseOnScreen){
                int row = mouseY / pixelsPerGrid;
                int col = mouseX / pixelsPerGrid;
                int index = (row * numColumns) + col;
                newSand.add(index);
            //}
        }
    }

    /**Initializes the current situation array to the same size as the next gen array
     * All values will be 0
     */
    public void setup(int numRows, int numColumns, int pixels){
        this.numRows = numRows;
        this.numColumns = numColumns;
        this.pixelsPerGrid = pixels;
    }
}
