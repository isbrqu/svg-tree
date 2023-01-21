package svgtree;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import svgtree.PseudoTag;

public class Line extends PseudoTag {

  private static String strokeWidth;
  private final String type = "line";
  private final String color = "#fff";

  public Line(Point start, Point end) {
    this.element = document.createElement(type);
    this.element.setAttribute("x1", Float.toString(start.getX()));
    this.element.setAttribute("y1", Float.toString(start.getY()));
    this.element.setAttribute("x2", Float.toString(end.getX()));
    this.element.setAttribute("y2", Float.toString(end.getY()));
    this.element.setAttribute("stroke", this.color);
    this.element.setAttribute("stroke-width", strokeWidth);
  }

  public static void setStrokeWidth(float strokeWidth) {
    Line.strokeWidth = Float.toString(strokeWidth);
  }

} 
