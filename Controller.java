import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.awt.event.ActionEvent;

public class Controller {

    private final int DELAY = 1;

    private View view;
    private Model model;
    private int n;

    private Timer mazeTimer;
    private Timer successTimer;
    private int itest = 1;

    private Tile tempGoal;

    public Controller() throws FileNotFoundException {
        this.model = new Model();
        this.n = this.model.getSize();

        this.view = new View(this.n, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSearch();
            }
        });
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.view.updateTile(i, j, model.getTile()[i][j].getType(), model.getTile()[i][j].getStatus());
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        new Controller();
    }

    private void startSearch() {
        this.view.updateButton("Searching...", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        }, false);

        this.tempGoal = model.Astar();
        mazeTimer = new Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tempGoal == null && !model.getFrontier().isEmpty()) {
                    tempGoal = model.Astar();
                    for (int i = 0; i < n; i++) {
                        for (int j = 0; j < n; j++) {
                            view.updateTile(i, j, model.getTile()[i][j].getType(), model.getTile()[i][j].getStatus());
                        }
                    }
                } else {
                    String endMsg = "Exit";
                    if (tempGoal == null) {
                        view.failTiles();
                        endMsg = "Failed...";
                    } else {
                        successTimer = new Timer(DELAY, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (tempGoal.getParent() != null) {
                                    if (!(tempGoal.getCoordinate()[0] == model.getGoals()[0]
                                            && tempGoal.getCoordinate()[1] == model.getGoals()[1])) {

                                        view.succeedTile(tempGoal.getCoordinate()[0], tempGoal.getCoordinate()[1]);
                                    }
                                    tempGoal = tempGoal.getParent();

                                } else {
                                    successTimer.stop();
                                }

                            }
                        });
                        successTimer.setRepeats(true);
                        successTimer.start();
                        endMsg = "Success!";
                    }
                    view.updateButton(endMsg, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            endSearch();
                            System.out.println(itest);
                        }
                    }, true);
                    mazeTimer.stop();
                }
            }
        });
        mazeTimer.setRepeats(true);
        mazeTimer.start();
    }

    private void endSearch() {
        this.view.closeFrame();
    }

}
