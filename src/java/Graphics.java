import java.awt.*;
import javax.swing.*;

public class Graphics extends JFrame {

  private JFrame f;
  private JPanel grid;
  private JLabel[][] labels;
  private ImageIcon turtle;
  private ImageIcon emptyPatch;
  private ImageIcon grainPatch;

  public Graphics(Turtle[] turtles, Patch[][] patches) {
    // JButton b = new JButton("click");
    // b.setBounds(130, 100, 100, 40); // x, y, width, height

    turtle = new ImageIcon("saksham.jpg");
    emptyPatch = new ImageIcon("emptyPatch.jpg");
    grainPatch = new ImageIcon("grainPatch.jpg");

    labels = new JLabel[WealthDistribution.NUM_PATCH_ROWS][WealthDistribution.NUM_PATCH_COLS];
    grid = new JPanel();
    grid.setLayout(new GridLayout(WealthDistribution.NUM_PATCH_COLS, WealthDistribution.NUM_PATCH_ROWS));
    for (int y = 0; y < patches.length; y ++) {
      for (int x = 0; x < patches[y].length; x ++) {
        JLabel label = new JLabel(emptyPatch);
        grid.add(label);
        labels[y][x] = label;
      }
    }

    // f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // add(b);
    add(grid);
    setSize(480, 360); // width, height
    // setLayout(null);
    pack();
    setVisible(true);
  }

  public void update(Turtle[] turtles, Patch[][] patches) {
    for (int y = 0; y < patches.length; y ++) {
      for (int x = 0; x < patches[y].length; x ++) {
        if (patches[y][x].getCountTurtlesHere() > 0) {
          labels[y][x].setIcon(turtle);
          // labels[y][x].setText(String.format("%2d", patches[y][x].getCountTurtlesHere()));
        } else {
          if (patches[y][x].getGrainHere() == 0) {
            labels[y][x].setIcon(emptyPatch);
            // labels[y][x].setText(String.format("%2d", patches[y][x].getGrainHere()));
          } else {
            labels[y][x].setIcon(grainPatch);
            // labels[y][x].setText(String.format("%2d", patches[y][x].getGrainHere()));
          }
        }
      }
    }
  }

}
