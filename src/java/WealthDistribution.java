import java.awt.AWTError;
import java.awt.HeadlessException;
import java.io.FileWriter;
import java.util.Random;

/**
 * Java implementation of NetLogo Wealth Distribution model
 * @author Ivan Ken Weng Chee 736901
 * @author Shorye Chopra 689913
 * @author Saksham Agrawal 866102
 */
public class WealthDistribution {

  public static final Integer NUM_PATCH_ROWS = 20;
  public static final Integer NUM_PATCH_COLS = 20;
  public static final Integer MAX_TICKS = 500;
  public static final Integer MAX_GRAIN = 50;
  public static final Integer NUM_PEOPLE = 20;
  public static final Integer PERCENT_BEST_LAND = 10;
  public static final Integer GRAIN_GROWTH_INTERVAL = 5;

  private Patch[][] patches;
  private Turtle[] turtles;
  private Calculator calculator;
  private Integer ticks;
  private Integer maxWealth;
  private Graphics graphics;
  private Console console;

  /**
   * Wealth Distribution Constructor
   */
  public WealthDistribution() {
	// Attempts to execute GUI
    try {
      graphics = new Graphics(this);
    } catch (HeadlessException e1) {
      System.err.println("No display detected, please connect one");
    } catch (AWTError e2) {
      System.err.println("No display detected, please connect one");
    }
    console = new Console(this);
  }

  /**
   * Setup the initial amounts of grain each patch has
   */
  private void setupPatches() {
    patches = new Patch[NUM_PATCH_ROWS][NUM_PATCH_COLS];
    // give some patches the highest amount of grain possible
    // these patches are the "best land"
    for (int y = 0; y < patches.length; y ++) {
      for (int x = 0; x < patches[y].length; x ++) {
        Float randomFloat = new Random().nextFloat() * 100;
        if (randomFloat <= PERCENT_BEST_LAND) {
          patches[y][x] = new Patch(x, y, MAX_GRAIN, MAX_GRAIN);
        } else {
          patches[y][x] = new Patch(x, y, 0, 0);
        }
      }
    }
    // spread that grain around the window a little and put a little back
    // into the patches that are the "best land" found above
    for (int i = 0; i < 5; i ++) {
      for (int y = 0; y < patches.length; y ++) {
        for (int x = 0; x < patches[y].length; x ++) {
          if (patches[y][x].getGrainHere() != 0) {
            patches[y][x].setGrainHere(
              Double.valueOf(patches[y][x].getMaxGrainHere())
            );
          }
          // Diffuse
          Double grainShare = patches[y][x].getGrainHere() * 0.25 / 8;
          if (y > 0 && x > 0) {
            patches[y - 1][x - 1].diffuseGrainHere(grainShare);
            patches[y][x].setGrainHere(
              patches[y][x].getGrainHere() - grainShare
            );
          }
          if (y > 0) {
            patches[y - 1][x].diffuseGrainHere(grainShare);
            patches[y][x].setGrainHere(
              patches[y][x].getGrainHere() - grainShare
            );
          }
          if (y > 0 && x < NUM_PATCH_COLS - 1) {
            patches[y - 1][x + 1].diffuseGrainHere(grainShare);
            patches[y][x].setGrainHere(
              patches[y][x].getGrainHere() - grainShare
            );
          }
          if (x > 0) {
            patches[y][x - 1].diffuseGrainHere(grainShare);
            patches[y][x].setGrainHere(
              patches[y][x].getGrainHere() - grainShare
            );
          }
          if (x < NUM_PATCH_COLS - 1) {
            patches[y][x + 1].diffuseGrainHere(grainShare);
            patches[y][x].setGrainHere(
              patches[y][x].getGrainHere() - grainShare
            );
          }
          if (y < NUM_PATCH_ROWS - 1 && x > 0) {
            patches[y + 1][x - 1].diffuseGrainHere(grainShare);
            patches[y][x].setGrainHere(
              patches[y][x].getGrainHere() - grainShare
            );
          }
          if (y < NUM_PATCH_ROWS - 1) {
            patches[y + 1][x].diffuseGrainHere(grainShare);
            patches[y][x].setGrainHere(
              patches[y][x].getGrainHere() - grainShare
            );
          }
          if (y < NUM_PATCH_ROWS - 1 && x < NUM_PATCH_COLS - 1) {
            patches[y + 1][x + 1].diffuseGrainHere(grainShare);
            patches[y][x].setGrainHere(
              patches[y][x].getGrainHere() - grainShare
            );
          }
        }
      }
    }
    // spread the grain around some more
    for (int i = 0; i < 10; i ++) {
      for (int y = 0; y < patches.length; y ++) {
        for (int x = 0; x < patches[y].length; x ++) {
          // Diffuse
          Double grainShare = patches[y][x].getGrainHere() * 0.25 / 8;
          if (y > 0 && x > 0) {
            patches[y - 1][x - 1].diffuseGrainHere(grainShare);
            patches[y][x].setGrainHere(
              patches[y][x].getGrainHere() - grainShare
            );
          }
          if (y > 0) {
            patches[y - 1][x].diffuseGrainHere(grainShare);
            patches[y][x].setGrainHere(
              patches[y][x].getGrainHere() - grainShare
            );
          }
          if (y > 0 && x < NUM_PATCH_COLS - 1) {
            patches[y - 1][x + 1].diffuseGrainHere(grainShare);
            patches[y][x].setGrainHere(
              patches[y][x].getGrainHere() - grainShare
            );
          }
          if (x > 0) {
            patches[y][x - 1].diffuseGrainHere(grainShare);
            patches[y][x].setGrainHere(
              patches[y][x].getGrainHere() - grainShare
            );
          }
          if (x < NUM_PATCH_COLS - 1) {
            patches[y][x + 1].diffuseGrainHere(grainShare);
            patches[y][x].setGrainHere(
              patches[y][x].getGrainHere() - grainShare
            );
          }
          if (y < NUM_PATCH_ROWS - 1 && x > 0) {
            patches[y + 1][x - 1].diffuseGrainHere(grainShare);
            patches[y][x].setGrainHere(
              patches[y][x].getGrainHere() - grainShare
            );
          }
          if (y < NUM_PATCH_ROWS - 1) {
            patches[y + 1][x].diffuseGrainHere(grainShare);
            patches[y][x].setGrainHere(
              patches[y][x].getGrainHere() - grainShare
            );
          }
          if (y < NUM_PATCH_ROWS - 1 && x < NUM_PATCH_COLS - 1) {
            patches[y + 1][x + 1].diffuseGrainHere(grainShare);
            patches[y][x].setGrainHere(
              patches[y][x].getGrainHere() - grainShare
            );
          }
        }
      }
    }
    // round grain levels to whole numbers
    // initial grain level is also maximum
    for (int y = 0; y < patches.length; y ++) {
      for (int x = 0; x < patches[y].length; x ++) {
        patches[y][x].setGrainHere(
          Math.floor(patches[y][x].getGrainHere())
        );
        patches[y][x].setMaxGrainHere(
          Double.valueOf(patches[y][x].getGrainHere())
        );
        patches[y][x].recolorPatch();
      }
    }
  }

  /**
   * Setup the initial values for the turtle variables
   */
  private void setupTurtles() {
    turtles = new Turtle[NUM_PEOPLE];
    // setDefaultShape(turtles, "person")
    for (int i = 0; i < turtles.length; i ++) {
      turtles[i] = new Turtle(
        new Random().nextInt(NUM_PATCH_COLS),
        new Random().nextInt(NUM_PATCH_ROWS)
      );
      // put turtles on patch centers
      turtles[i].fd(patches, 0);
      turtles[i].setInitialTurtleVars();
      turtles[i].setAge();
    }
    for (int i = 0; i < turtles.length; i ++) {
      if (turtles[i].getWealth() > maxWealth) {
        maxWealth = turtles[i].getWealth();
      }
    }
    for (int i = 0; i < turtles.length; i ++) {
      turtles[i].recolorTurtles(maxWealth);
    }
  }

  /**
   * Resets ticks to 0
   */
  public void resetTicks() {
    ticks = 0;
  }

  /**
   * Setup and helpers
   */
  public void setup() {
    maxWealth = 0;
    // set global variables to appropriate values
    // call other procedures to setup various parts of the world
    setupPatches();
    setupTurtles();
    calculator = new Calculator();
    // updates numerical values
    calculator.updateLorenzAndGini(turtles, patches);
    resetTicks();
    // graphical output
    graphics.update(ticks, turtles, patches, calculator);
    // console output
    console.update(ticks, turtles, patches, calculator);
  }

  /**
   * Go and helpers
   */
  public void go() {
    try {
      FileWriter csvWriter = new FileWriter("new.csv");
      csvWriter.append("Red");
      csvWriter.append(",");
      csvWriter.append("Green");
      csvWriter.append(",");
      csvWriter.append("Blue");
      csvWriter.append(",");
      csvWriter.append("Gini");
      csvWriter.append("\n");
      csvWriter.flush();

      for (int i = 0; i < turtles.length; i ++) {
        if (turtles[i].getWealth() > maxWealth) {
          maxWealth = turtles[i].getWealth();
        }
      }
      for (ticks = 0; ticks < MAX_TICKS + 1; ticks ++) {
        for (int i = 0; i < turtles.length; i ++) {
          // choose direction holding most grain within the turtle's vision
          turtles[i].turnTowardsGrain(patches);
          turtles[i].harvest(patches);
          turtles[i].moveEatAgeDie(patches);
          turtles[i].recolorTurtles(maxWealth);
        }
        // grow grain every GRAIN_GROWTH_INTERVAL clock ticks
        if (ticks % GRAIN_GROWTH_INTERVAL == 0) {
          for (int y = 0; y < patches.length; y ++) {
            for (int x = 0; x < patches[y].length; x ++) {
              patches[y][x].growGrain();
            }
          }
        }
        // updates numerical values
        calculator.updateLorenzAndGini(turtles, patches);
        // graphical output
        graphics.update(ticks, turtles, patches, calculator);
        // console output
        console.update(ticks, turtles, patches, calculator);
        // csv output
        csvWriter.append(Integer.toString(console.getRed()));
        csvWriter.append(",");
        csvWriter.append(Integer.toString(console.getGreen()));
        csvWriter.append(",");
        csvWriter.append(Integer.toString(console.getBlue()));
        csvWriter.append(",");
        csvWriter.append(Double.toString(calculator.getGiniIndex()));
        csvWriter.append("\n");
        csvWriter.flush();
      }
      csvWriter.close();
    }
    catch(Exception e){
      System.err.println("Failed to write csv output");
    }
  }

  /**
   * Resets the model
   */
  public void resetAll() {
    calculator.resetAll();
    resetTicks();
  }
  
  /**
   * Patch Getter
   * @return : Array of patch rows
   */
  public Patch[][] getPatches() {
    return patches;
  }

  /**
   * Turtle Getter
   * @return : Array of turtles
   */
  public Turtle[] getTurtles() {
    return turtles;
  }

  /**
   * Calculator Getter
   * @return : Calculator
   */
  public Calculator getCalculator() {
    return calculator;
  }

  /**
   * Ticks Getter
   * @return : Ticks
   */
  public Integer getTicks() {
    return ticks;
  }

}
