import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Calculator {

  private Double[] sortedWealths;
  private Double totalWealth;
  private Double wealthSumSoFar;
  private Integer index;
  private Double giniIndexReserve;
  private List<Double> lorenzPoints;

  public Calculator() {}

  public void updateLorenzAndGini(Turtle[] turtles, Patch[][] patches) {
    sortedWealths = new Double[turtles.length];
    for (int i = 0; i < turtles.length; i ++) {
      sortedWealths[i] = Double.valueOf(turtles[i].getWealth());
    }
    Arrays.sort(sortedWealths);
    totalWealth = 0.0;
    for (int i = 0; i < sortedWealths.length; i ++) {
      totalWealth += sortedWealths[i];
    }
    wealthSumSoFar = 0.0;
    index = 0;
    giniIndexReserve = 0.0;
    lorenzPoints = new ArrayList<Double>();
    // now actually plot the Lorenz curve
    // along the way, we also calculate the Gini index
    for (int i = 0; i < WealthDistribution.NUM_PEOPLE; i ++) {
      wealthSumSoFar += sortedWealths[i];
      lorenzPoints.add((wealthSumSoFar / totalWealth) * 100);
      index += 1;
      giniIndexReserve += (index / WealthDistribution.NUM_PEOPLE) - (wealthSumSoFar / totalWealth);
    }
  }

  public List<Double> getLorenzPoints() {
    return lorenzPoints;
  }

  public Double getGiniIndexReserve() {
    return giniIndexReserve;
  }

}
