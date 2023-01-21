package svgtree;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import svgtree.PseudoTag;

public class Circle extends PseudoTag {

  private static String radio;
  private static final String TYPE = "circle";
  private static final String COLOR = "#fff";

  public Circle(Point point) {
    this.element = document.createElement(TYPE);
    this.element.setAttribute("cx", Float.toString(point.getX()));
    this.element.setAttribute("cy", Float.toString(point.getY()));
    this.element.setAttribute("r", radio);
    this.element.setAttribute("fill", COLOR);
  }

  public static void setRadio(float radio) {
    Circle.radio = Float.toString(radio);
  }

} 
