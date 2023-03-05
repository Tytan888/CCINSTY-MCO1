import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.awt.event.ActionEvent;

public class Controller {

    // Constant for how many miliseconds between each exploration in the A* search.
    private final int DELAY = 50;

    // All the private variables needed for the program to run.
    private View view;
    private Model model;
    private int n;

    private Timer mazeTimer;
    private Timer successTimer;

    private Tile tempGoal;

    /**
     * This creates a Controller object, which is responsible for creating and
     * integrating the Model and View objects to be used in the program.
     */
    public Controller() throws FileNotFoundException {
        // Initialize the Model and retrieve the size from the inputted maze.txt file.
        this.model = new Model();
        this.n = this.model.getSize();

        // Initialize the View as well as the button's starting actionListener.
        this.view = new View(this.n, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSearch();
            }
        });

        // Update all displayed tiles in the GUI.
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.view.updateTile(i, j, model.getTile()[i][j].getType(), model.getTile()[i][j].getStatus());
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        // Start program by instantiating a new Controller.
        new Controller();
    }

    /**
     * This method faciliates the starting and the execution of the A* search
     * process, which includes sending instructions to the Model periodically to
     * perform the A* process, as well as updating the GUI within View to reflect
     * changes within said Model.
     */
    private void startSearch() {
        // When search is started, change button text...
        this.view.updateButton("Searching...", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        }, false);

        // Create new timer to facilitate the A* looping search process.
        this.tempGoal = model.Astar();
        mazeTimer = new Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // If goal is not yet found and frontier is not yet empty...
                if (tempGoal == null && !model.getFrontier().isEmpty()) {
                    // Continue performing A*.
                    tempGoal = model.Astar();
                    // Update all displayed tiles in the GUI.
                    for (int i = 0; i < n; i++) {
                        for (int j = 0; j < n; j++) {
                            view.updateTile(i, j, model.getTile()[i][j].getType(), model.getTile()[i][j].getStatus());
                        }
                    }
                }
                // If goal is found or frontier is empty...
                else {
                    String endMsg = "Exit";
                    // If goal was not found, meaning that the frontier ended up empty...
                    if (tempGoal == null) {
                        // Change all explored tiles to red.
                        view.failTiles();
                        // Change button text to display explored tiles and the failed state.
                        endMsg = "(" + model.getCount() + ") Failed...";
                    }
                    // If goal was found, meaning A* had found an optimal path...
                    else {
                        // Set a new timer for tracking the final path.
                        successTimer = new Timer(DELAY, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // Backtrack tiles until the start tile is reached.
                                if (tempGoal.getParent() != null) {
                                    // Set tile to green (unless they are the goal tile).
                                    if (!(tempGoal.getCoordinate()[0] == model.getGoals()[0]
                                            && tempGoal.getCoordinate()[1] == model.getGoals()[1])) {
                                        view.succeedTile(tempGoal.getCoordinate()[0], tempGoal.getCoordinate()[1]);
                                    }
                                    tempGoal = tempGoal.getParent();
                                } else {
                                    // Once start tile is reached, stop this "success" timer.
                                    successTimer.stop();
                                }

                            }
                        });

                        // Start said "success" timer.
                        successTimer.setRepeats(true);
                        successTimer.start();
                        endMsg = "(" + model.getCount() + ") Success!";
                    }

                    // Change button text and actionListener to reflect the search finishing.
                    view.updateButton(endMsg, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            endSearch();
                        }
                    }, true);

                    // Finally, stop this "maze" timer once and for all.
                    mazeTimer.stop();
                }
            }
        });

        // Set "maze" timer to repeating and start.
        mazeTimer.setRepeats(true);
        mazeTimer.start();
    }

    /**
     * This function is in charge of terminating the JFrame within View when the
     * program is instructed to Fend.
     */
    private void endSearch() {
        this.view.closeFrame();
    }

}
