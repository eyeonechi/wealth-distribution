public class Console {

  private WealthDistribution model;

  public Console(WealthDistribution model) {
    this.model = model;
  }

  // generates a console visualisation of the model
  public void update(Integer ticks, Turtle[] turtles, Patch[][] patches, Calculator calculator) {
    System.out.println("Tick " + ticks);
    
    debug(turtles, patches);
  }

  private void debug(Turtle[] turtles, Patch[][] patches) {
    for (int y = 0; y < patches.length; y ++) {
      for (int x = 0; x < patches[y].length; x ++) {
        if (patches[y][x].getCountTurtlesHere() > 0) {
          System.out.printf("%2d", patches[y][x].getCountTurtlesHere());
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

}
