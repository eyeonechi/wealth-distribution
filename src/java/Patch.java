public class Patch {

  public static final Integer NUM_GRAIN_GROWN = 4;

  private Integer x;
  private Integer y;
  private Integer grainHere;
  private Integer maxGrainHere;

  private Integer countTurtlesHere;

  public Patch(Integer x, Integer y, Integer grainHere, Integer maxGrainHere) {
    this.x = x;
    this.y = y;
    this.grainHere = grainHere;
    this.maxGrainHere = maxGrainHere;
    this.countTurtlesHere = 0;
  }

  // use color to indicate grain level
  public void recolorPatch() {}

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

  public Integer getGrainHere() {
    return grainHere;
  }

  public Integer getMaxGrainHere() {
    return maxGrainHere;
  }

  public Integer getCountTurtlesHere() {
    return countTurtlesHere;
  }

  public void increaseCountTurtlesHere() {
    countTurtlesHere += 1;
  }

  public void decreaseCountTurtlesHere() {
    countTurtlesHere -= 1;
  }

  public void setGrainHere(Double newGrainHere) {
    grainHere = (int) Math.round(newGrainHere);
  }

  public void diffuseGrainHere(Double diffuseGrain) {
    grainHere += (int) Math.round(diffuseGrain);
  }

  public void setMaxGrainHere(Double newMaxGrainHere) {
    maxGrainHere = (int) Math.round(newMaxGrainHere);
  }

}