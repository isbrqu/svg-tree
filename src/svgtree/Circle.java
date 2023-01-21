package svgtree;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import svgtree.PseudoTag;

public class Circle extends PseudoTag {

  private static String radio;
  private final String type = "circle";
  private final String color = "#fff";

  public Circle(Point point) {
    this.element = document.createElement(type);
    this.element.setAttribute("cx", Float.toString(point.getX()));
    this.element.setAttribute("cy", Float.toString(point.getY()));
    this.element.setAttribute("r", radio);
    this.element.setAttribute("fill", this.color);
  }

  public static void setRadio(float radio) {
    Circle.radio = Float.toString(radio);
  }

} 
