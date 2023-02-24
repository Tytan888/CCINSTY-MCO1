import java.util.ArrayList;
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

public class MazeView {

    private JFrame mainFrame;
    private int n;

    private JPanel buttonPanel;
    private JPanel mazePanel;
    private JPanel tilePanels[][];

    private JButton theButton;

    public MazeView(int n, ActionListener startListener) {

        this.n = n;

        this.mainFrame = new JFrame("迷路ボット");
        this.mainFrame.setSize(750, 800);
        this.mainFrame.setUndecorated(false);
        this.mainFrame.setResizable(false);
        this.mainFrame.setLocationRelativeTo(null);
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.buttonPanel = new JPanel();
        this.buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 5));
        this.buttonPanel.setBackground(Color.black);
        this.buttonPanel.setPreferredSize(new Dimension(1000, 80));

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

        this.mazePanel = new JPanel();
        this.mazePanel.setLayout(new GridLayout(n, n));
        this.mazePanel.setBackground(Color.BLACK);

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

        this.mainFrame.add(this.buttonPanel, BorderLayout.SOUTH);
        this.mainFrame.add(this.mazePanel, BorderLayout.CENTER);

        this.mainFrame.setVisible(true);
    }

    public void updateTiles(/* TODO ADD TILES */) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // TODO: Add changing colors
            }
        }
    }

    public void updateButton(String text, ActionListener endListener, boolean enable) {
        this.theButton.removeActionListener(this.theButton.getActionListeners()[0]);
        this.theButton.setText(text);
        this.theButton.addActionListener(endListener);
        this.theButton.setEnabled(enable);
    }

    public void closeFrame() {
        this.mainFrame.setVisible(false);
        this.mainFrame.dispose();
        System.exit(1);
    }
}
