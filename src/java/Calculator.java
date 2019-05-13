import java.util.Arrays;

public class Calculator {

  private Patch[][] patches;
  private Turtle[] turtles;
  private Integer[] sortedWealths;
  private Integer totalWealth;
  private Integer wealthSumSoFar;
  private Integer index;
  private Integer giniIndexReserve;
  private Integer[] lorenzPoints;

  public Calculator(Turtle[] turtles, Patch[][] patches) {
    this.turtles = turtles;
    this.patches = patches;
  }

  public void updateLorenzAndGini() {
    sortedWealths = new Integer[turtles.length];
    for (int i = 0; i < turtles.length; i ++) {
      sortedWealths[i] = turtles[i].getWealth();
    }
    Arrays.sort(sortedWealths);
    totalWealth = 0;
    for (int i = 0; i < sortedWealths.length; i ++) {
      totalWealth += sortedWealths[i];
    }
    wealthSumSoFar = 0;
    index = 0;
    giniIndexReserve = 0;
    // TODO: confirm this
    lorenzPoints = new Integer[WealthDistribution.MAX_TICKS];
    // now actually plot the Lorenz curve
    // along the way, we also calculate the Gini index
    for (int i = 0; i < WealthDistribution.NUM_PEOPLE; i ++) {
      // TODO: verify these
      wealthSumSoFar += sortedWealths[i];
      lorenzPoints[i] = (wealthSumSoFar / totalWealth) * 100;
      index += 1;
      giniIndexReserve += (index / WealthDistribution.NUM_PEOPLE) - (wealthSumSoFar / totalWealth);
    }
  }

}
