/**
 * Generates console output
 * @author Ivan Ken Weng Chee 736901
 * @author Shorye Chopra 689913
 * @author Saksham Agrawal 866102
 */
public class Console {

  private Integer red;
  private Integer green;
  private Integer blue;
  
  /**
   * Console Constructor
   * @param model : Model
   */
  public Console(WealthDistribution model) {
    this.red = 0;
    this.green = 0;
    this.blue = 0;
  }

  /**
   * Updates the console with model state
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
    System.out.println("Tick " + ticks);
    debug(turtles, patches);
  }

  /**
   * Console visualisation of model state
   * @param turtles : Array of turtles
   * @param patches : Array of patch rows
   */
  private void debug(Turtle[] turtles, Patch[][] patches) {
    int maxWealth = 0;
    this.red = 0;
    this.green = 0;
    this.blue = 0;
    for (int i=0; i<turtles.length; i ++){
      if (turtles[i].getWealth() > maxWealth)
        maxWealth = turtles[i].getWealth();
    }
    for (int y = 0; y < patches.length; y ++) {
      for (int x = 0; x < patches[y].length; x ++) {
        if (patches[y][x].getCountTurtlesHere() > 0) {
          System.out.printf("%2d", patches[y][x].getCountTurtlesHere());
          for (int i=0; i<turtles.length; i ++){
            if ((turtles[i].getX()==x) && (turtles[i].getY()==y)){
              int wealth = turtles[i].getWealth();
              if (wealth <= maxWealth/3) this.red+=1;
              else if (wealth <= (maxWealth * 2 / 3)) this.green+=1;
              else this.blue+=1;
            }
          }
        } else {
          if (patches[y][x].getGrainHere() > 0) {
            System.out.printf("..");
          } else {
            System.out.printf("__");
          }
        }
      }
      System.out.print("\n");
    }
    System.out.print("\n");
  }

  /**
   * Red Getter
   * @return : Number of red turtles
   */
  public Integer getRed() {
    return red;
  }

  /**
   * Green Getter
   * @return : Number of green turtles
   */
  public Integer getGreen() {
    return green;
  }

  /**
   * Blue Getter
   * @return : Number of blue turtles
   */
  public Integer getBlue() {
    return blue;
  }

}
