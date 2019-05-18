import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Graphics extends JFrame {

  private WealthDistribution model;
  private JFrame f;
  private JPanel panel;
  private JLabel[][] labels;
  private ImageIcon turtleRed;
  private ImageIcon turtleBlue;
  private ImageIcon turtleGreen;
  private Color[] colorBands;

  private Graph lorenzCurveGraph;
  private Graph giniIndexReserveGraph;

  public Graphics(WealthDistribution model) {
    this.model = model;

    colorBands = getColorBands(WealthDistribution.MAX_GRAIN);
    turtleRed = new ImageIcon("turtleRed.jpg");
    turtleBlue = new ImageIcon("turtleBlue.jpg");
    turtleGreen = new ImageIcon("turtleGreen.jpg");

    JButton setupButton = new JButton("setup");
    setupButton.setBounds(0, 0, 100, 40); // x, y, width, height
    setupButton.setBackground(Color.white);
    setupButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        model.setup();
      }
    });

    JButton goButton = new JButton("go");
    goButton.setBounds(100, 0, 100, 40); // x, y, width, height
    goButton.setBackground(Color.white);
    goButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        model.go();
      }
    });

    labels = new JLabel[WealthDistribution.NUM_PATCH_ROWS][WealthDistribution.NUM_PATCH_COLS];
    panel = new JPanel();
    panel.setBounds(480, 360, 0, 40); // x, y, width, height
    panel.setLayout(new GridLayout(WealthDistribution.NUM_PATCH_COLS, WealthDistribution.NUM_PATCH_ROWS));
    for (int y = 0; y < WealthDistribution.NUM_PATCH_ROWS; y ++) {
      for (int x = 0; x < WealthDistribution.NUM_PATCH_COLS; x ++) {
        JLabel label = new JLabel();
        label.setOpaque(true);
        labels[y][x] = label;
        panel.add(label);
      }
    }

    lorenzCurveGraph = new Graph(true);
    lorenzCurveGraph.setPreferredSize(new Dimension(240, 160));
    giniIndexReserveGraph = new Graph(false);
    giniIndexReserveGraph.setPreferredSize(new Dimension(240, 160));

    setLayout(new BorderLayout());
    add(setupButton, BorderLayout.LINE_START);
    add(goButton, BorderLayout.LINE_END);
    add(panel, BorderLayout.CENTER);
    add(lorenzCurveGraph, BorderLayout.PAGE_START);
    add(giniIndexReserveGraph, BorderLayout.PAGE_END);
    pack();
    setSize(1024, 768); // width, height
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
    panel.revalidate();
    panel.repaint();
    lorenzCurveGraph.setScores(model.getCalculator().getLorenzPoints());
    lorenzCurveGraph.revalidate();
    lorenzCurveGraph.repaint();
    giniIndexReserveGraph.addScore(model.getCalculator().getGiniIndexReserve());
    giniIndexReserveGraph.revalidate();
    giniIndexReserveGraph.repaint();
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
