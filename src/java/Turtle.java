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
  private Integer maxWealth;
  private String color;
  private Integer heading;
  private Integer bestDirection;
  private Integer bestAmount;
  private Integer total;
  private Integer howFar;

  public Turtle(Integer x, Integer y) {
    this.x = x;
    this.y = y;
    this.heading = 0;
  }

  public void setInitialTurtleVars() {
    age = 0;
    // face one-of neighbors4
    lifeExpectancy
      = LIFE_EXPECTANCY_MIN
      + new Random().nextInt(LIFE_EXPECTANCY_MAX - LIFE_EXPECTANCY_MIN + 1);
    metabolism = 1 + new Random().nextInt(METABOLISM_MAX);
    wealth = metabolism + new Random().nextInt(50);
    vision = 1 + new Random().nextInt(MAX_VISION);
  }

  // set the class of the turtles
  // if a turtle has less than a third the wealth of the richest turtle,
  // color it red.
  // if between one and two thirds, color it green.
  // if over two thirds, color it blue.
  public void recolorTurtles(Integer maxWealth) {
    if (wealth <= maxWealth / 3.0) {
      color = "red";
    } else if (wealth <= (maxWealth * 2 / 3.0)) {
      color = "green";
    } else {
      color = "blue";
    }
  }

  // determine the direction which is most profitable for each turtle in
  // the surrounding patches within the turtles' vision
  public void turnTowardsGrain(Patch[][] patches) {
    heading = 0;
    bestDirection = 0;
    bestAmount = grainAhead(patches);
    heading = 90;
    if (grainAhead(patches) > bestAmount) {
      bestDirection = 90;
      bestAmount = grainAhead(patches);
    }
    heading = 180;
    if (grainAhead(patches) > bestAmount) {
      bestDirection = 180;
      bestAmount = grainAhead(patches);
    }
    heading = 270;
    if (grainAhead(patches) > bestAmount) {
      bestDirection = 270;
      bestAmount = grainAhead(patches);
    }
    heading = bestDirection;
  }

  // each turtle harvests the grain on its patch. if there are multiple
  // turtles on a patch, divide the grain evenly among the turtles
  public void harvest(Patch[][] patches) {
    // have turtles harvest before any turtle sets the patch to 0
    Integer income = (int) Math.round(Math.floor(patches[y][x].getGrainHere() / patches[y][x].getCountTurtlesHere()));

    if (income <= 20) {
      income = income - ((int) Math.round(Math.floor(income*32/100)));
    }
    else if (income <= 40 & income > 20) {
      income = income - ((int) Math.round(Math.floor(income*35/100)));
    }
    else {
      income = income - ((int) Math.round(Math.floor(income*37/100)));
    }

    wealth = wealth + income;
    // now that the grain has been harvested, have the turtles make the
    // patches which they are on have no grain
    patches[y][x].setGrainHere(0.0);
    patches[y][x].recolorPatch();
  }

  public void moveEatAgeDie(Patch[][] patches) {
    fd(patches, 1);
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

  public Integer grainAhead(Patch[][] patches) {
    total = 0;
    howFar = 1;
    for (int i = 0; i < vision; i ++) {
      if (heading == 0) {
        if((x+i) >= WealthDistribution.NUM_PATCH_COLS ){
          total += patches[y][
            (x + i) % (WealthDistribution.NUM_PATCH_COLS)
          ].getGrainHere();
        }
        else{
          total += patches[y][x + i].getGrainHere();
        }
      } else if (heading == 90) {
        if ((y-i) <0) {
          total += patches[
            WealthDistribution.NUM_PATCH_ROWS - y - i
          ][x].getGrainHere();
        }
        else{
          total += patches[y - i][x].getGrainHere();
        }
      } else if (heading == 180) {
        if ((x-i) <0) {
          total += patches[y][
            WealthDistribution.NUM_PATCH_COLS - x - i
          ].getGrainHere();
        }
        else {
          total += patches[y][x - i].getGrainHere();
        }
      } else {
          if((y+i) >= WealthDistribution.NUM_PATCH_ROWS ){
            total += patches[
              (y + i) % (WealthDistribution.NUM_PATCH_ROWS)
            ][x].getGrainHere();
          }
          else {
            total += patches[y + i][x].getGrainHere();
        }
      }
      howFar = howFar + 1;
    }
    return total;
  }

  // move to
  public void fd(Patch[][] patches, Integer steps) {
    if (steps != 0) {
      patches[y][x].decreaseCountTurtlesHere();
      if (heading == 0) {
        x += steps;
      } else if (heading == 90) {
        y -= steps;
      } else if (heading == 180) {
        x -= steps;
      } else {
        y += steps;
      }
      if (x < 0) {
        x = WealthDistribution.NUM_PATCH_COLS - 1;
      } else if (x > WealthDistribution.NUM_PATCH_COLS - 1) {
        x = 0;
      }
      if (y < 0) {
        y = WealthDistribution.NUM_PATCH_ROWS - 1;
      } else if (y > WealthDistribution.NUM_PATCH_ROWS - 1) {
        y = 0;
      }
    }
    patches[y][x].increaseCountTurtlesHere();
  }

  public void setAge() {
    age = new Random().nextInt(lifeExpectancy);
  }

  public Integer getWealth() {
    return wealth;
  }

  public Integer getX() {
    return x;
  }

  public Integer getY() {
    return y;
  }

  public String getColor() {
    return color;
  }

}
