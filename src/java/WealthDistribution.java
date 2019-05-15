import java.util.Random;

public class WealthDistribution {

  public static final Integer NUM_PATCH_ROWS = 20;
  public static final Integer NUM_PATCH_COLS = 20;
  public static final Integer MAX_TICKS = 500;

  public static final Integer MAX_GRAIN = 50;
  // public static final Double GINI_INDEX_RESERVE;
  // public static final Double LORENZ_POINTS;

  public static final Integer NUM_PEOPLE = 20; // 250;
  public static final Integer PERCENT_BEST_LAND = 10;
  public static final Integer GRAIN_GROWTH_INTERVAL = 1;

  private Patch[][] patches;
  private Turtle[] turtles;
  private Calculator calculator;
  private Integer ticks;
  private Graphics graphics;

  public WealthDistribution() {}

  // setup and helpers
  public void setup() {
    clearAll();
    // set global variables to appropriate values
    // call other procedures to setup various parts of the world
    setupPatches();
    setupTurtles();
    graphics = new Graphics(turtles, patches);
    calculator = new Calculator();
    calculator.updateLorenzAndGini(turtles, patches);
    resetTicks();
  }

  private void clearAll() {
    // TODO: is this used?
  }

  // setup the initial amounts of grain each patch has
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
    // TODO: confirm how diffuse function works
    for (int i = 0; i < 5; i ++) {
      for (int y = 0; y < patches.length; y ++) {
        for (int x = 0; x < patches[y].length; x ++) {
          if (patches[y][x].getGrainHere() != 0) {
            patches[y][x].setGrainHere(Double.valueOf(patches[y][x].getMaxGrainHere()));
          }
          // Diffuse
          Double grainShare = patches[y][x].getGrainHere() * 0.25 / 8;
          if (y > 0 && x > 0) {
            patches[y - 1][x - 1].diffuseGrainHere(grainShare);
            patches[y][x].setGrainHere(patches[y][x].getGrainHere() - grainShare);
          }
          if (y > 0) {
            patches[y - 1][x].diffuseGrainHere(grainShare);
            patches[y][x].setGrainHere(patches[y][x].getGrainHere() - grainShare);
          }
          if (y > 0 && x < NUM_PATCH_COLS - 1) {
            patches[y - 1][x + 1].diffuseGrainHere(grainShare);
            patches[y][x].setGrainHere(patches[y][x].getGrainHere() - grainShare);
          }
          if (x > 0) {
            patches[y][x - 1].diffuseGrainHere(grainShare);
            patches[y][x].setGrainHere(patches[y][x].getGrainHere() - grainShare);
          }
          if (x < NUM_PATCH_COLS - 1) {
            patches[y][x + 1].diffuseGrainHere(grainShare);
            patches[y][x].setGrainHere(patches[y][x].getGrainHere() - grainShare);
          }
          if (y < NUM_PATCH_ROWS - 1 && x > 0) {
            patches[y + 1][x - 1].diffuseGrainHere(grainShare);
            patches[y][x].setGrainHere(patches[y][x].getGrainHere() - grainShare);
          }
          if (y < NUM_PATCH_ROWS - 1) {
            patches[y + 1][x].diffuseGrainHere(grainShare);
            patches[y][x].setGrainHere(patches[y][x].getGrainHere() - grainShare);
          }
          if (y < NUM_PATCH_ROWS - 1 && x < NUM_PATCH_COLS - 1) {
            patches[y + 1][x + 1].diffuseGrainHere(grainShare);
            patches[y][x].setGrainHere(patches[y][x].getGrainHere() - grainShare);
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
            patches[y][x].setGrainHere(patches[y][x].getGrainHere() - grainShare);
          }
          if (y > 0) {
            patches[y - 1][x].diffuseGrainHere(grainShare);
            patches[y][x].setGrainHere(patches[y][x].getGrainHere() - grainShare);
          }
          if (y > 0 && x < NUM_PATCH_COLS - 1) {
            patches[y - 1][x + 1].diffuseGrainHere(grainShare);
            patches[y][x].setGrainHere(patches[y][x].getGrainHere() - grainShare);
          }
          if (x > 0) {
            patches[y][x - 1].diffuseGrainHere(grainShare);
            patches[y][x].setGrainHere(patches[y][x].getGrainHere() - grainShare);
          }
          if (x < NUM_PATCH_COLS - 1) {
            patches[y][x + 1].diffuseGrainHere(grainShare);
            patches[y][x].setGrainHere(patches[y][x].getGrainHere() - grainShare);
          }
          if (y < NUM_PATCH_ROWS - 1 && x > 0) {
            patches[y + 1][x - 1].diffuseGrainHere(grainShare);
            patches[y][x].setGrainHere(patches[y][x].getGrainHere() - grainShare);
          }
          if (y < NUM_PATCH_ROWS - 1) {
            patches[y + 1][x].diffuseGrainHere(grainShare);
            patches[y][x].setGrainHere(patches[y][x].getGrainHere() - grainShare);
          }
          if (y < NUM_PATCH_ROWS - 1 && x < NUM_PATCH_COLS - 1) {
            patches[y + 1][x + 1].diffuseGrainHere(grainShare);
            patches[y][x].setGrainHere(patches[y][x].getGrainHere() - grainShare);
          }
        }
      }
    }
    // round grain levels to whole numbers
    // initial grain level is also maximum
    for (int y = 0; y < patches.length; y ++) {
      for (int x = 0; x < patches[y].length; x ++) {
        patches[y][x].setGrainHere(Math.floor(patches[y][x].getGrainHere()));
        patches[y][x].setMaxGrainHere(Double.valueOf(patches[y][x].getGrainHere()));
        patches[y][x].recolorPatch();
      }
    }
  }

  // setup the initial values for the turtle variables
  private void setupTurtles() {
    turtles = new Turtle[NUM_PEOPLE];
    // TODO: what is this?
    // setDefaultShape(turtles, "person")
    for (int i = 0; i < turtles.length; i ++) {
      turtles[i] = new Turtle(0, 0, 0, 0, 0);
      // put turtles on patch centers
      turtles[i].fd(new Random().nextInt(NUM_PATCH_COLS), new Random().nextInt(NUM_PATCH_ROWS), patches);
      // easier to see
      // TODO: dont know what this is
      // turtles[i].setSize(1.5)
      turtles[i].setInitialTurtleVars();
      turtles[i].setAge();
    }
    for (int i = 0; i < turtles.length; i ++) {
      turtles[i].recolorTurtles();
    }
  }

  private void resetTicks() {
    ticks = 0;
  }

  // go and helpers
  public void go() {
    for (ticks = 0; ticks < MAX_TICKS + 1; ticks ++) {
      for (int i = 0; i < turtles.length; i ++) {
        // choose direction holding most grain within the turtle's vision
        turtles[i].turnTowardsGrain(patches);
        turtles[i].harvest(patches);
        turtles[i].moveEatAgeDie(patches);
        turtles[i].recolorTurtles();
      }
      // grow grain every GRAIN_GROWTH_INTERVAL clock ticks
      if (ticks % GRAIN_GROWTH_INTERVAL == 0) {
        for (int y = 0; y < patches.length; y ++) {
          for (int x = 0; x < patches[y].length; x ++) {
            patches[y][x].growGrain();
          }
        }
      }
      calculator.updateLorenzAndGini(turtles, patches);
      // graphical output
      graphics.update(turtles, patches);
      // print output
      visualiseModel();
    }
  }

  // generates a console visualisation of the model
  private void visualiseModel() {
    System.out.println("Tick " + ticks);
    for (int y = 0; y < patches.length; y ++) {
      for (int x = 0; x < patches[y].length; x ++) {
        if (patches[y][x].getCountTurtlesHere() > 0) {
          System.out.printf("%2d", patches[y][x].getCountTurtlesHere());
        } else {
          if (patches[y][x].getGrainHere() == 0) {
            System.out.printf("__");
          } else {
            System.out.printf("..");
          }
        }
      }
      System.out.print("\n");
    }
    System.out.print("\n");
  }

  public static void main(String[] args) {
    WealthDistribution model = new WealthDistribution();
    model.setup();
    model.go();
  }

}
