/**
 * Simple Java implementation of NetLogo
 * @author Ivan Ken Weng Chee 736901
 * @author Shorye Chopra 689913
 * @author Saksham Agrawal 866102
 */
public class JNetLogo {

  private static WealthDistribution model;

  /**
   * Entry point for the program
   * @param args : Console arguments
   */
  public static void main(String[] args) {
    setModel(new WealthDistribution());
  }

  /**
   * Model Getter
   * @return : Model
   */
  public static WealthDistribution getModel() {
	return model;
  }

  /**
   * Model Setter
   * @param model : Model
   */
  public static void setModel(WealthDistribution model) {
	JNetLogo.model = model;
  }

}
