import java.lang.Math;
import java.util.Random;

public class Turtle {

  public static final Integer MAX_VISION = 5;
  public static final Integer METABOLISM_MAX = 15;
  public static final Integer LIFE_EXPECTANCY_MIN = 1;
  public static final Integer LIFE_EXPECTANCY_MAX = 83;

  private Integer age;
  private Integer wealth;
  private Integer lifeExpectancy;
  private Integer metabolism;
  private Integer vision;

  private Integer x;
  private Integer y;
  private Patch[][] patches;
  private Patch patch;
  private Integer maxWealth;
  private String color;
  private Integer heading;
  private Integer bestDirection;
  private Integer bestAmount;
  private Integer total;
  private Integer howFar;

  public Turtle(Integer age, Integer wealth, Integer lifeExpectancy, Integer metabolism, Integer vision, Patch[][] patches) {
    this.age = age;
    this.wealth = wealth;
    this.lifeExpectancy = lifeExpectancy;
    this.metabolism = metabolism;
    this.vision = vision;
    this.patches = patches;
  }

  public void setInitialTurtleVars() {
    age = 0;
    // TODO: face one-of neighbors4
    lifeExpectancy = LIFE_EXPECTANCY_MIN + new Random().nextInt(LIFE_EXPECTANCY_MAX - LIFE_EXPECTANCY_MIN + 1);
    metabolism = 1 + new Random().nextInt(METABOLISM_MAX);
    wealth = metabolism + new Random().nextInt(50);
    vision = 1 + new Random().nextInt(MAX_VISION);
    // set random location
    x = new Random().nextInt(WealthDistribution.NUM_PATCH_COLS);
    y = new Random().nextInt(WealthDistribution.NUM_PATCH_ROWS);
  }

  // set the class of the turtles
  // if a turtle has less than a third the wealth of the richest turtle, color it red.
  // if between one and two thirds, color it green.
  // if over two thirds, color it blue.
  public void recolorTurtles() {
    // TODO: let max-wealth max [wealth] of turtles
    maxWealth = 0;
    if (wealth <= maxWealth / 3) {
      color = "red";
    } else if (wealth <= (maxWealth * 2 / 3)) {
      color = "green";
    } else {
      color = "blue";
    }
  }

  // determine the direction which is most profitable for each turtle in
  // the surrounding patches within the turtles' vision
  public void turnTowardsGrain() {
    heading = 0;
    bestDirection = 0;
    bestAmount = grainAhead();
    heading = 90;
    if (grainAhead() > bestAmount) {
      bestDirection = 90;
      bestAmount = grainAhead();
    }
    heading = 180;
    if (grainAhead() > bestAmount) {
      bestDirection = 180;
      bestAmount = grainAhead();
    }
    heading = 270;
    if (grainAhead() > bestAmount) {
      bestDirection = 270;
      bestAmount = grainAhead();
    }
    heading = bestDirection;
  }

  // each turtle harvests the grain on its patch. if there are multiple
  // turtles on a patch, divide the grain evenly among the turtles
  public void harvest() {
    // have turtles harvest before any turtle sets the patch to 0
    wealth = (int) Math.round(Math.floor(wealth + (patch.getGrainHere() / patch.getCountTurtlesHere())));
    // now that the grain has been harvested, have the turtles make the
    // patches which they are on have no grain
    patch.setGrainHere(0.0);
    patch.recolorPatch();
  }

  public void moveEatAgeDie() {
    // TODO: what is this?
    // fd 1
    // consume some grain according to metabolism
    wealth -= metabolism;
    // grow older
    age += 1;
    // check for death conditions:
    // if you have no grain or you're older than the life expectancy
    // or if some random factor holds, then you "die" and are "reborn"
    // (in fact, your variables are just reset to new random values)
    if (wealth < 0 || age >= lifeExpectancy) {
      setInitialTurtleVars();
    }
  }

  public Integer grainAhead() {
    total = 0;
    howFar = 1;
    for (int i = 0; i < vision; i ++) {
      if (heading == 0) {
        if ((x + i) < (WealthDistribution.NUM_PATCH_COLS - 1)) {
          total += patches[y][x + i].getGrainHere();
          x += 1;
        }
      } else if (heading == 90) {
        if ((y - i) > 0) {
          total += patches[y - i][x].getGrainHere();
          y -= 1;
        }
      } else if (heading == 180) {
        if ((x - i) > 0) {
          total += patches[y][x - i].getGrainHere();
          x -= 1;
        }
      } else {
        if ((y + i) < (WealthDistribution.NUM_PATCH_ROWS - 1)) {
          total += patches[y + i][x].getGrainHere();
          y += 1;
        }
      }
      howFar = howFar + 1;
    }
    // TODO: should this be here?
    moveTo(x, y);
    return total;
  }

  public void moveTo(Integer x, Integer y) {
    if (patch != null) {
      patch.decreaseCountTurtlesHere();
    }
    patch = patches[y][x];
    patch.increaseCountTurtlesHere();
  }

  public void setAge() {
    age = new Random().nextInt(lifeExpectancy);
  }

  public Integer getWealth() {
    return wealth;
  }

}
