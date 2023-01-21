package svgtree;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Line {

  private final String type = "line";
  private final String color = "#fff";
  private String strokeWidth;
  private Document document;

  public Line(Document document, float strokeWidth) {
    this.document = document;
    this.strokeWidth = Float.toString(strokeWidth);
  }

  public Element
  create(float x1, float y1, float x2, float y2) {
    Element element = this.document.createElement(type);
    element.setAttribute("x1", Float.toString(x1));
    element.setAttribute("y1", Float.toString(y1));
    element.setAttribute("x2", Float.toString(x2));
    element.setAttribute("y2", Float.toString(y2));
    element.setAttribute("stroke", this.color);
    element.setAttribute("stroke-width", this.strokeWidth);
    return element;
  }

  public Element
  create(Point start, Point end) {
    Element element = this.document.createElement(type);
    element.setAttribute("x1", Float.toString(start.getX()));
    element.setAttribute("y1", Float.toString(start.getY()));
    element.setAttribute("x2", Float.toString(end.getX()));
    element.setAttribute("y2", Float.toString(end.getY()));
    element.setAttribute("stroke", this.color);
    element.setAttribute("stroke-width", this.strokeWidth);
    return element;
  }

} 
