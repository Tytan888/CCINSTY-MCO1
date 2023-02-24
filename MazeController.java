import java.util.ArrayList;
import javax.swing.Timer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.Image;
import java.awt.event.ActionEvent;

public class MazeController {
    private MazeView view;
    private Timer mazeTimer;
    private int itest = 10;
    // TODO add MazeModel

    public MazeController() {
        this.view = new MazeView(45, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSearch();
            }
        });
        // TODO add MazeModel
    }

    public static void main(String[] args) {
        new MazeController();
    }

    private void startSearch() {
        this.view.updateButton("Searching...", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        }, false);

        mazeTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Replace this if with model's end.
                final int j = itest;
                if (j > 0) {
                    itest--;
                    view.updateButton(j + "", new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                        }
                    }, false);
                } else {
                    view.updateButton("Exit", new ActionListener() {
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
