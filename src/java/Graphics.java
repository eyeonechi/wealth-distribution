import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Graphics extends JFrame {

  private WealthDistribution model;
  private JFrame f;
  private JPanel displayPanel;
  private JPanel controlPanel;
  private JPanel graphPanel;
  private JPanel variablePanel;
  private JPanel infoPanel;
  private JLabel[][] labels;
  private ImageIcon turtleRed;
  private ImageIcon turtleBlue;
  private ImageIcon turtleGreen;
  private Color[] colorBands;

  JButton setupButton;
  JButton goButton;
  JTextArea infoTextArea;
  JLabel ticksLabel;
  JLabel numPeopleLabel;
  JLabel maxVisionLabel;
  JLabel metabolismMaxLabel;
  JLabel lifeExpectancyMinLabel;
  JLabel lifeExpectancyMaxLabel;
  JLabel percentBestLandLabel;
  JLabel grainGrowthIntervalLabel;
  JLabel numGrainGrownLabel;

  private Graph lorenzCurveGraph;
  private Graph giniIndexReserveGraph;

  public Graphics(WealthDistribution model) {
    this.model = model;

    colorBands = getColorBands(WealthDistribution.MAX_GRAIN);
    turtleRed = new ImageIcon("turtleRed.jpg");
    turtleBlue = new ImageIcon("turtleBlue.jpg");
    turtleGreen = new ImageIcon("turtleGreen.jpg");

    setupButton = new JButton("setup");
    // setupButton.setBounds(0, 0, 100, 40); // x, y, width, height
    setupButton.setPreferredSize(new Dimension(100, 40));
    setupButton.setBackground(Color.white);
    setupButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        model.setup();
      }
    });

    goButton = new JButton("go");
    // goButton.setBounds(100, 0, 100, 40); // x, y, width, height
    goButton.setPreferredSize(new Dimension(100, 40));
    goButton.setBackground(Color.white);
    goButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        model.go();
      }
    });

    labels = new JLabel[WealthDistribution.NUM_PATCH_ROWS][WealthDistribution.NUM_PATCH_COLS];
    displayPanel = new JPanel();
    displayPanel.setPreferredSize(new Dimension(480, 480));
    // displayPanel.setBounds(480, 360, 0, 40); // x, y, width, height
    displayPanel.setLayout(new GridLayout(WealthDistribution.NUM_PATCH_ROWS, WealthDistribution.NUM_PATCH_COLS));
    for (int y = 0; y < WealthDistribution.NUM_PATCH_ROWS; y ++) {
      for (int x = 0; x < WealthDistribution.NUM_PATCH_COLS; x ++) {
        JLabel label = new JLabel();
        label.setOpaque(true);
        labels[y][x] = label;
        displayPanel.add(label);
      }
    }

    ticksLabel = new JLabel();
    ticksLabel.setText("Tick: 0");
    numPeopleLabel = new JLabel();
    numPeopleLabel.setText("NumPeople: " + WealthDistribution.NUM_PEOPLE);
    maxVisionLabel = new JLabel();
    maxVisionLabel.setText("MaxVision: " + Turtle.MAX_VISION);
    metabolismMaxLabel = new JLabel();
    metabolismMaxLabel.setText("MetabolismMax: " + Turtle.METABOLISM_MAX);
    lifeExpectancyMinLabel = new JLabel();
    lifeExpectancyMinLabel.setText("LifeExpectancyMin: " + Turtle.LIFE_EXPECTANCY_MIN);
    lifeExpectancyMaxLabel = new JLabel();
    lifeExpectancyMaxLabel.setText("LifeExpectancyMax: " + Turtle.LIFE_EXPECTANCY_MAX);
    percentBestLandLabel = new JLabel();
    percentBestLandLabel.setText("PercentBestLand: " + WealthDistribution.PERCENT_BEST_LAND + "%");
    grainGrowthIntervalLabel = new JLabel();
    grainGrowthIntervalLabel.setText("GrainGrowthInterval: " + WealthDistribution.GRAIN_GROWTH_INTERVAL);
    numGrainGrownLabel = new JLabel();
    numGrainGrownLabel.setText("NumGrainGrown: " + Patch.NUM_GRAIN_GROWN);
    variablePanel = new JPanel();
    variablePanel.setLayout(new GridLayout(9, 1));
    variablePanel.setPreferredSize(new Dimension(240, 360));
    variablePanel.add(ticksLabel);
    variablePanel.add(numPeopleLabel);
    variablePanel.add(maxVisionLabel);
    variablePanel.add(metabolismMaxLabel);
    variablePanel.add(lifeExpectancyMinLabel);
    variablePanel.add(lifeExpectancyMaxLabel);
    variablePanel.add(percentBestLandLabel);
    variablePanel.add(grainGrowthIntervalLabel);
    variablePanel.add(numGrainGrownLabel);

    controlPanel = new JPanel();
    controlPanel.setLayout(new GridLayout(1, 2));
    controlPanel.setPreferredSize(new Dimension(480, 40));
    controlPanel.add(setupButton);
    controlPanel.add(goButton);

    lorenzCurveGraph = new Graph(true);
    lorenzCurveGraph.setPreferredSize(new Dimension(240, 160));
    giniIndexReserveGraph = new Graph(false);
    giniIndexReserveGraph.setPreferredSize(new Dimension(240, 160));
    graphPanel = new JPanel();
    graphPanel.setLayout(new GridLayout(1, 2));
    graphPanel.setPreferredSize(new Dimension(480, 160));
    graphPanel.add(lorenzCurveGraph);
    graphPanel.add(giniIndexReserveGraph);

    infoTextArea = new JTextArea(9, 20);
    infoTextArea.setEditable(false);
    infoTextArea.setLineWrap(true);
    infoTextArea.append("This model simulates the distribution of wealth. “The rich get richer and the poor get poorer” is a familiar saying that expresses inequity in the distribution of wealth. In this simulation, we see Pareto’s law, in which there are a large number of “poor” or red people, fewer “middle class” or green people, and many fewer “rich” or blue people.");
    infoPanel = new JPanel();
    infoPanel.setLayout(new GridLayout(1, 1));
    infoPanel.setPreferredSize(new Dimension(240, 360));
    infoPanel.add(infoTextArea);

    setLayout(new BorderLayout());
    add(controlPanel, BorderLayout.PAGE_START);
    add(variablePanel, BorderLayout.LINE_START);
    add(infoPanel, BorderLayout.LINE_END);
    add(displayPanel, BorderLayout.CENTER);
    add(graphPanel, BorderLayout.PAGE_END);
    pack();
    // setSize(1024, 768); // width, height
    setVisible(true);
    setExtendedState(getExtendedState() | Frame.MAXIMIZED_BOTH);

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
    displayPanel.revalidate();
    displayPanel.repaint();
    lorenzCurveGraph.setScores(model.getCalculator().getLorenzPoints());
    lorenzCurveGraph.revalidate();
    lorenzCurveGraph.repaint();
    giniIndexReserveGraph.addScore(model.getCalculator().getGiniIndexReserve());
    giniIndexReserveGraph.revalidate();
    giniIndexReserveGraph.repaint();
    ticksLabel.setText("Tick: " + model.getTicks());
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
