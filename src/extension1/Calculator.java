import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Updates the numerical calculations of the model
 * @author Ivan Ken Weng Chee 736901
 * @author Shorye Chopra 689913
 * @author Saksham Agrawal 866102
 */
public class Calculator {

  private Double[] sortedWealths;
  private Double totalWealth;
  private Double wealthSumSoFar;
  private Integer index;
  private Double giniIndexReserve;
  private Double giniIndex;
  private List<Double> lorenzPoints;

  /**
   * Calculator Constructor
   */
  public Calculator() {
    totalWealth = 0.0;
    wealthSumSoFar = 0.0;
    index = 0;
    giniIndexReserve = 0.0;
    giniIndex = 0.0;
  }

  /**
   * Updates Lorenz and Gini Index values
   * @param turtles : Array of turtles
   * @param patches : Array of patch rows
   */
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
    for (int i = 0; i < sortedWealths.length; i ++) {
      wealthSumSoFar += sortedWealths[index];
      lorenzPoints.add((wealthSumSoFar / totalWealth) * 100);
      index += 1;
      giniIndexReserve
        = giniIndexReserve
        + (index / sortedWealths.length)
        - (wealthSumSoFar / totalWealth);
    }
    giniIndex = 1 + (giniIndexReserve / sortedWealths.length) / 0.5;
  }

  /**
   * Lorenz Points Getter
   * @return : List of Lorenz Points
   */
  public List<Double> getLorenzPoints() {
    return lorenzPoints;
  }

  /**
   * Gini Index Getter
   * @return : Gini Index
   */
  public Double getGiniIndex() {
    return giniIndex;
  }

  /**
   * Reinitialises Lorenz Points and Gini Index
   */
  public void resetAll() {
    lorenzPoints = new ArrayList<Double>();
    giniIndex = 0.0;
  }

}
