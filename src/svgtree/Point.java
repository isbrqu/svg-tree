package svgtree;
import svgtree.Utils;

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

  public Point bottomLeft(float margin, float distance) {
    float x = this.x - margin;
    float y = Utils.calculateY(distance, x, this.x, this.y, 1);
    return new Point(x, y);
  }

  public Point bottomRight(float margin, float distance) {
    float x = this.x + margin;
    float y = Utils.calculateY(distance, x, this.x, this.y, 1);
    return new Point(x, y);
  }

} 
