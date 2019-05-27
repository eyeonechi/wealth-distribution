import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * JNetLogo GUI
 * @author Ivan Ken Weng Chee 736901
 * @author Shorye Chopra 689913
 * @author Saksham Agrawal 866102
 */
public class Graphics extends JFrame {

  private static final long serialVersionUID = 1L;
  private WealthDistribution model;
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

  private JButton setupButton;
  private JButton goButton;
  private JTextArea infoTextArea;
  private JLabel ticksLabel;
  private JLabel numPeopleLabel;
  private JLabel maxVisionLabel;
  private JLabel metabolismMaxLabel;
  private JLabel lifeExpectancyMinLabel;
  private JLabel lifeExpectancyMaxLabel;
  private JLabel percentBestLandLabel;
  private JLabel grainGrowthIntervalLabel;
  private JLabel numGrainGrownLabel;

  private Graph lorenzCurveGraph;
  private Graph giniIndexGraph;

  /**
   * Graphics Constructor
   * @param model : Model
   */
  public Graphics(WealthDistribution model) {
    this.setModel(model);

    // images and colors
    colorBands = getColorBands(WealthDistribution.MAX_GRAIN);
    turtleRed = new ImageIcon("turtleRed.jpg");
    turtleBlue = new ImageIcon("turtleBlue.jpg");
    turtleGreen = new ImageIcon("turtleGreen.jpg");

    // setup button
    setupButton = new JButton("setup");
    setupButton.setPreferredSize(new Dimension(100, 40));
    setupButton.setBackground(Color.white);
    setupButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        resetAll();
        model.setup();
        goButton.setEnabled(true);
      }
    });

    // go button
    goButton = new JButton("go");
    goButton.setEnabled(false);
    goButton.setPreferredSize(new Dimension(100, 40));
    goButton.setBackground(Color.white);
    goButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Thread thread = null;
        if (e.getActionCommand().equals("go")) {
          thread = new Thread() {
            @Override
            public void run() {
              goButton.setEnabled(false);
              model.go();
              goButton.setEnabled(false);
              setupButton.setEnabled(true);
              model.resetAll();
            }
          };
          setupButton.setEnabled(false);
          thread.start();
        } else {
          while (thread != null) {
            try {
              thread.join();
              setupButton.setEnabled(true);
            } catch (Exception e1) {
              e1.printStackTrace();
            }
          }
        }
      }
    });

    // display panel
    labels = new JLabel[
      WealthDistribution.NUM_PATCH_ROWS
    ][
      WealthDistribution.NUM_PATCH_COLS
    ];
    displayPanel = new JPanel();
    displayPanel.setPreferredSize(new Dimension(480, 480));
    displayPanel.setLayout(new GridLayout(
      WealthDistribution.NUM_PATCH_ROWS,
      WealthDistribution.NUM_PATCH_COLS
    ));
    for (int y = 0; y < WealthDistribution.NUM_PATCH_ROWS; y ++) {
      for (int x = 0; x < WealthDistribution.NUM_PATCH_COLS; x ++) {
        JLabel label = new JLabel();
        label.setOpaque(true);
        labels[y][x] = label;
        displayPanel.add(label);
      }
    }

    // variable panel
    ticksLabel = new JLabel();
    ticksLabel.setText("Tick: 0");
    numPeopleLabel = new JLabel();
    numPeopleLabel.setText("NumPeople: " + WealthDistribution.NUM_PEOPLE);
    maxVisionLabel = new JLabel();
    maxVisionLabel.setText("MaxVision: " + Turtle.MAX_VISION);
    metabolismMaxLabel = new JLabel();
    metabolismMaxLabel.setText("MetabolismMax: " + Turtle.METABOLISM_MAX);
    lifeExpectancyMinLabel = new JLabel();
    lifeExpectancyMinLabel.setText(
      "LifeExpectancyMin: " + Turtle.LIFE_EXPECTANCY_MIN
    );
    lifeExpectancyMaxLabel = new JLabel();
    lifeExpectancyMaxLabel.setText(
      "LifeExpectancyMax: " + Turtle.LIFE_EXPECTANCY_MAX
    );
    percentBestLandLabel = new JLabel();
    percentBestLandLabel.setText(
      "PercentBestLand: " + WealthDistribution.PERCENT_BEST_LAND + "%"
    );
    grainGrowthIntervalLabel = new JLabel();
    grainGrowthIntervalLabel.setText(
      "GrainGrowthInterval: " + WealthDistribution.GRAIN_GROWTH_INTERVAL
    );
    numGrainGrownLabel = new JLabel();
    numGrainGrownLabel.setText(
      "NumGrainGrown: " + Patch.NUM_GRAIN_GROWN
    );
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

    // control panel
    controlPanel = new JPanel();
    controlPanel.setLayout(new GridLayout(1, 2));
    controlPanel.setPreferredSize(new Dimension(480, 40));
    controlPanel.add(setupButton);
    controlPanel.add(goButton);

    // graph panel
    lorenzCurveGraph = new Graph(true, false);
    lorenzCurveGraph.setPreferredSize(new Dimension(240, 160));
    giniIndexGraph = new Graph(false, true);
    giniIndexGraph.setPreferredSize(new Dimension(240, 160));
    graphPanel = new JPanel();
    graphPanel.setLayout(new GridLayout(1, 2));
    graphPanel.setPreferredSize(new Dimension(480, 160));
    graphPanel.add(lorenzCurveGraph);
    graphPanel.add(giniIndexGraph);

    // info panel
    infoTextArea = new JTextArea(9, 20);
    infoTextArea.setEditable(false);
    infoTextArea.setLineWrap(true);
    infoTextArea.append("SWEN90004 Modelling Complex Software Systems\n");
    infoTextArea.append("Wealth Distribution\n");
    infoTextArea.append("Ivan Chee | Saksham Agrawal | Shorye Chopra\n");
    infoTextArea.append(
      "This model simulates the distribution of wealth. "
      + "“The rich get richer and the poor get poorer” is a familiar saying "
      + "that expresses inequity in the distribution of wealth. "
      + "In this simulation, we see Pareto’s law, in which there are a "
      + "large number of “poor” or red people, fewer “middle class” "
      + "or green people, and many fewer “rich” or blue people.");
    infoPanel = new JPanel();
    infoPanel.setLayout(new GridLayout(1, 1));
    infoPanel.setPreferredSize(new Dimension(240, 360));
    infoPanel.add(infoTextArea);

    // frame layout
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
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  }

  /**
   * Updates the GUI
   * @param ticks      : Ticks
   * @param turtles    : Array of turtles
   * @param patches    : Array of patch rows
   * @param calculator : Calculator
   */
  public void update(
    Integer ticks,
    Turtle[] turtles,
    Patch[][] patches,
    Calculator calculator
  ) {
    for (int y = 0; y < patches.length; y ++) {
      for (int x = 0; x < patches[y].length; x ++) {
        labels[y][x].setIcon(null);
        if (patches[y][x].getGrainHere() == 0) {
          labels[y][x].setBackground(colorBands[patches[y][x].getGrainHere()]);
        } else {
          labels[y][x].setBackground(colorBands[patches[y][x].getGrainHere()]);
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
    lorenzCurveGraph.setScores(calculator.getLorenzPoints());
    giniIndexGraph.addScore(calculator.getGiniIndex());
    ticksLabel.setText("Tick: " + ticks);
  }

  /**
   * Constructs an array of colors for grains
   * @param bands : Number of bands
   * @return      : Color bands
   */
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

  /**
   * Resets the GUI
   */
  private void resetAll() {
    lorenzCurveGraph.resetScores();
    giniIndexGraph.resetScores();
  }

  /**
   * Model Getter
   * @return : Model
   */
  public WealthDistribution getModel() {
	return model;
  }

  /**
   * Model Setter
   * @param model : New model
   */
  public void setModel(WealthDistribution model) {
	this.model = model;
  }

}
