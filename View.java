
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

public class View {

    // All the private variables needed for the GUI to function.
    private JFrame mainFrame;
    private int n;

    private JPanel buttonPanel;
    private JPanel mazePanel;
    private JPanel tilePanels[][];

    private JButton theButton;

    /**
     * This creates a View object, which handles all the aspects relating to the GUI
     * and its functionalities.
     */
    public View(int n, ActionListener startListener) {

        // Store the size of the maze.
        this.n = n;

        // Instantiate the main JFrame to be used.
        this.mainFrame = new JFrame("Mazefinder 2000");
        this.mainFrame.setSize(750, 800);
        this.mainFrame.setUndecorated(false);
        this.mainFrame.setResizable(false);
        this.mainFrame.setLocationRelativeTo(null);
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Instantiate the JPanel to house the bottom button.
        this.buttonPanel = new JPanel();
        this.buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 5));
        this.buttonPanel.setBackground(Color.black);
        this.buttonPanel.setPreferredSize(new Dimension(1000, 80));

        // Instantiate the only JButton present in the program.
        this.theButton = new JButton("Start");
        this.theButton.addActionListener(startListener);
        this.theButton.setPreferredSize(new Dimension(500, 70));
        this.theButton.setFocusable(false);
        this.theButton.setHorizontalTextPosition(JButton.CENTER);
        this.theButton.setVerticalTextPosition(JButton.BOTTOM);
        this.theButton.setForeground(Color.WHITE);
        this.theButton.setBackground(Color.BLACK);
        this.theButton.setFont(new Font("Helvetica", Font.PLAIN, 40));
        this.theButton.setBorder(BorderFactory.createLineBorder(Color.white, 5));
        this.buttonPanel.add(theButton);

        // Instantiate the JPanel to house the main maze.
        this.mazePanel = new JPanel();
        this.mazePanel.setLayout(new GridLayout(n, n));
        this.mazePanel.setBackground(Color.BLACK);

        // Instantiate the many sub-JPanels representing each tile in the maze.
        this.tilePanels = new JPanel[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tilePanels[i][j] = new JPanel();
                tilePanels[i][j].setBackground(Color.BLACK);
                if (n < 30)
                    tilePanels[i][j].setBorder(BorderFactory.createLineBorder(Color.white, 2));
                else
                    tilePanels[i][j].setBorder(BorderFactory.createLineBorder(Color.white, 1));
                mazePanel.add(tilePanels[i][j]);
            }
        }

        // Add buttonPanel and mazePanel to the overall JFrame.
        this.mainFrame.add(this.buttonPanel, BorderLayout.SOUTH);
        this.mainFrame.add(this.mazePanel, BorderLayout.CENTER);

        // Finally set the JFrame to be visible.
        this.mainFrame.setVisible(true);
    }

    /**
     * This method is tasked with updating the color of a tile, given its position
     * and state in the maze.
     * 
     * @param row          the row coordinate of the tile to be updated
     * @param col          the column coordinate of the tile to be updated
     * @param TILE_STATE   the type of the tile (wall, goal, start, free)
     * @param SEARCH_STATE the search status of the tile (unexplored, frontier,
     *                     explored)
     */
    public void updateTile(int row, int col, int TILE_STATE, int SEARCH_STATE) {
        switch (TILE_STATE) {
            // If tile is a wall, then set the color to black.
            case Tile.WALL:
                this.tilePanels[row][col].setBackground(Color.black);
                break;
            // If tile is the goal, then set the color to orange.
            case Tile.GOAL:
                this.tilePanels[row][col].setBackground(Color.ORANGE);
                break;
            // If tile is the start, then set the color to blue.
            case Tile.START:
                this.tilePanels[row][col].setBackground(Color.BLUE);
                break;
            // If tile is a normal tile...
            case Tile.FREE:
                switch (SEARCH_STATE) {
                    // If tile is still unexplored, then set the color to white.
                    case Tile.UNEXPLORED:
                        this.tilePanels[row][col].setBackground(Color.WHITE);
                        break;
                    // If tile is in the frontier, then set the color to yellow.
                    case Tile.FRONTIER:
                        this.tilePanels[row][col].setBackground(Color.YELLOW);
                        break;
                    // If tile has been explored, then set the color to purple.
                    case Tile.EXPLORED:
                        this.tilePanels[row][col].setBackground(new Color(156, 81, 182));
                        break;
                }
                break;
        }
    }

    /**
     * This method is tasked with updating the color of a tile to green to indicate
     * its inclusion in the optimal path, given its position in the maze.
     * 
     * @param row the row coordinate of the tile to be updated
     * @param col the column coordinate of the tile to be updated
     */
    public void succeedTile(int row, int col) {
        this.tilePanels[row][col].setBackground(Color.green);
    }

    /**
     * This method is tasked with updating the color of all currently purple tiles
     * to red to indicate failure of finding a single possible path.
     */
    public void failTiles() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tilePanels[i][j].getBackground().equals(new Color(156, 81, 182))) {
                    this.tilePanels[i][j].setBackground(Color.red);
                }
            }
        }
    }

    /**
     * This method is created to update the text, actionListener, and enabledStatus
     * of the bottom JButton.
     * 
     * @param text        the String to which the button's text will be set
     * @param endListener the new actionListenere to which the button's
     *                    actionListener will be set
     * @param enable      a boolean value indicating whether the button should be
     *                    enabled or not
     */
    public void updateButton(String text, ActionListener endListener, boolean enable) {
        this.theButton.removeActionListener(this.theButton.getActionListeners()[0]);
        this.theButton.setText(text);
        this.theButton.addActionListener(endListener);
        this.theButton.setEnabled(enable);
    }

    /**
     * This method is solely responsible for closing the JFrame and the overall
     * program when the user eventually hits the close button.
     */
    public void closeFrame() {
        this.mainFrame.setVisible(false);
        this.mainFrame.dispose();
        System.exit(1);
    }
}
