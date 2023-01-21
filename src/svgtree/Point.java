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

  public Point translate(float dx, float dy) {
    return new Point(this.x + dx, this.y + dy);
  }

  public Point translateInX(float distance) {
    return new Point(this.x + distance, this.y);
  }

  public Point translateInY(float distance) {
    return new Point(this.x, this.y + distance);
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

  public String toString() {
    return "(" + this.x + ", " + this.y + ")";
  }

} 
