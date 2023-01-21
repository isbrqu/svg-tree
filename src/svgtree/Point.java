package svgtree;

public class Point {

  private float x;
  private float y;

  public Point(float x, float y) {
    this.x = x;
    this.y = y;
  }

  public float getX() {
    return this.x;
  }

  public float getY() {
    return this.y;
  }

  public void translateInX(float x) {
    this.x += x;
  }

  public void translateInY(float y) {
    this.y += y;
  }

} 
