import java.awt.*;
import javax.swing.*;

public class Graphics extends JFrame {

  private JFrame f;
  private JPanel grid;
  private JLabel[][] labels;
  private ImageIcon turtleRed;
  private ImageIcon turtleBlue;
  private ImageIcon turtleGreen;
  private Color[] colorBands;

  public Graphics(Turtle[] turtles, Patch[][] patches) {
    colorBands = getColorBands(WealthDistribution.MAX_GRAIN);
    // JButton b = new JButton("click");
    // b.setBounds(130, 100, 100, 40); // x, y, width, height

    turtleRed = new ImageIcon("sakshamRed.jpg");
    turtleBlue = new ImageIcon("sakshamBlue.jpg");
    turtleGreen = new ImageIcon("sakshamGreen.jpg");

    labels = new JLabel[WealthDistribution.NUM_PATCH_ROWS][WealthDistribution.NUM_PATCH_COLS];
    grid = new JPanel();
    grid.setLayout(new GridLayout(WealthDistribution.NUM_PATCH_COLS, WealthDistribution.NUM_PATCH_ROWS));
    for (int y = 0; y < patches.length; y ++) {
      for (int x = 0; x < patches[y].length; x ++) {
        JLabel label = new JLabel();
        label.setOpaque(true);
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
        labels[y][x].setIcon(null);
        if (patches[y][x].getGrainHere() == 0) {
          labels[y][x].setBackground(colorBands[patches[y][x].getGrainHere()]);
          // labels[y][x].setText(String.format("%2d", patches[y][x].getGrainHere()));
        } else {
          labels[y][x].setBackground(colorBands[patches[y][x].getGrainHere()]);
          // labels[y][x].setText(String.format("%2d", patches[y][x].getGrainHere()));
        }
      }
    }
    for (int i = 0; i < turtles.length; i ++) {
      if (turtles[i].getColor().equals("green")) {
        labels[turtles[i].getY()][turtles[i].getX()].setIcon(turtleGreen);
      } else if (turtles[i].getColor().equals("blue")) {
        labels[turtles[i].getY()][turtles[i].getX()].setIcon(turtleBlue);
      } else {
        labels[turtles[i].getY()][turtles[i].getX()].setIcon(turtleRed);
      }
    }
  }

  private Color[] getColorBands(Integer bands) {
    Color color = Color.yellow;
    Color[] colorBands = new Color[bands];
    for (int i = 0; i < bands; i ++) {
      colorBands[i] = new Color(
        color.getRed() * i / bands,
        color.getGreen() * i / bands,
        color.getBlue() * i / bands,
        color.getAlpha()
      );
    }
    return colorBands;
  }

}
