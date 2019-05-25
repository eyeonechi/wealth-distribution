import java.io.*;
import java.util.*;

import javax.print.DocFlavor.STRING;

public class Console {

  private WealthDistribution model;
  public Integer red;
  public Integer green;
  public Integer blue;

  public Console(WealthDistribution model) {
    this.model = model;
    this.red = 0;
    this.green = 0;
    this.blue = 0;
  }

  // generates a console visualisation of the model
  public void update(Integer ticks, Turtle[] turtles, Patch[][] patches, Calculator calculator) {
    System.out.println("Tick " + ticks);
    
    debug(turtles, patches);
  }


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

}
