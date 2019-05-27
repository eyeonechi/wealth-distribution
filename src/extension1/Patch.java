/**
 * Simulates a Patch in NetLogo
 * @author Ivan Ken Weng Chee 736901
 * @author Shorye Chopra 689913
 * @author Saksham Agrawal 866102
 */
public class Patch {

  public static final Integer NUM_GRAIN_GROWN = 1;

  private Integer x;
  private Integer y;
  private Integer grainHere;
  private Integer maxGrainHere;
  private Integer countTurtlesHere;

  /**
   * Patch Constructor
   * @param x            : X coordinate of patch
   * @param y            : Y coordinate of patch
   * @param grainHere    : Amount of grain
   * @param maxGrainHere : Maximum amount of grain
   */
  public Patch(
    Integer x,
    Integer y,
    Integer grainHere,
    Integer maxGrainHere
  ) {
    this.setX(x);
    this.setY(y);
    this.grainHere = grainHere;
    this.maxGrainHere = maxGrainHere;
    this.countTurtlesHere = 0;
  }

  /**
   * Use color to indicate grain level
   */
  public void recolorPatch() {}

  /**
   * Grows grain on the patch
   */
  public void growGrain() {
    // if a patch does not have it's maximum amount of grain, add
    // NUM_GRAIN_GROWN to its grain amount
    if (grainHere < maxGrainHere) {
      grainHere += NUM_GRAIN_GROWN;
      // if the new amount of grain on a patch is over its maximum
      // capacity, set it to its maximum
      if (grainHere > maxGrainHere) {
        grainHere = maxGrainHere;
      }
      recolorPatch();
    }
  }

  /**
   * Grain Getter
   * @return : Amount of grain
   */
  public Integer getGrainHere() {
    return grainHere;
  }

  /**
   * Maximum Grain Getter
   * @return : Maximum amount of grain
   */
  public Integer getMaxGrainHere() {
    return maxGrainHere;
  }

  /**
   * Turtle Count Getter
   * @return : Turtle count
   */
  public Integer getCountTurtlesHere() {
    return countTurtlesHere;
  }

  /**
   * Increases turtle count
   */
  public void increaseCountTurtlesHere() {
    countTurtlesHere += 1;
  }

  /**
   * Decreases turtle count
   */
  public void decreaseCountTurtlesHere() {
    countTurtlesHere -= 1;
  }

  
  /**
   * Grain Here Setter
   * @param newGrainHere : New grain here
   */
  public void setGrainHere(Double newGrainHere) {
    grainHere = (int) Math.round(newGrainHere);
  }

  /**
   * Diffuse grain here
   * @param diffuseGrain : Grain to diffuse
   */
  public void diffuseGrainHere(Double diffuseGrain) {
    grainHere += (int) Math.round(diffuseGrain);
  }

  /**
   * Maximum Grain Here Setter
   * @param newMaxGrainHere : New maximum grain here
   */
  public void setMaxGrainHere(Double newMaxGrainHere) {
    maxGrainHere = (int) Math.round(newMaxGrainHere);
  }

  /**
   * X Getter
   * @return : X
   */
  public Integer getX() {
	return x;
  }

  /**
   * X Setter
   * @param x : New X
   */
  public void setX(Integer x) {
	this.x = x;
  }

  /**
   * Y Getter
   * @return : Y
   */
  public Integer getY() {
	return y;
  }

  /**
   * Y Setter
   * @param y : New Y
   */
  public void setY(Integer y) {
	this.y = y;
  }

}