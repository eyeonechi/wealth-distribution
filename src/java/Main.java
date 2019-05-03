public class Main {

  private static WealthDistribution model;

  public static void main(String[] args) {
    model = new WealthDistribution();
    model.setup();
    model.go();
  }

}
