package svgtree;

public class Utils {

  public static float calculateY(float hypotenuse, float x1, float x2, float y2, int d) {
    float opposite = x1 - x2;
    float adjacent = d * calculateLeg(hypotenuse, opposite);
    float y1 = adjacent + y2;
    return y1;
  }

  public static float calculateLeg(float hypotenuse, float leg) {
    float hypotenuse2 = (float) Math.pow(hypotenuse, 2);
    float leg2 = (float) Math.pow(leg, 2);
    float result = (float) Math.sqrt(hypotenuse2 - leg2);
    return result;
  }

} 
