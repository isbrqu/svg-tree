package svgtree;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Circle {

  private final String type = "circle";
  private final String color = "#fff";
  private String radio;
  private Document document;

  public Circle(Document document, float radio) {
    this.document = document;
    this.radio = Float.toString(radio);
  }

  public Element
  create(float x, float y) {
    Element element = this.document.createElement(type);
    element.setAttribute("cx", Float.toString(x));
    element.setAttribute("cy", Float.toString(y));
    element.setAttribute("r", radio);
    element.setAttribute("fill", this.color);
    return element;
  }

  public Element
  create(float x, float y, float radio) {
    Element element = this.document.createElement(type);
    element.setAttribute("cx", Float.toString(x));
    element.setAttribute("cy", Float.toString(y));
    element.setAttribute("r", Float.toString(radio));
    element.setAttribute("fill", this.color);
    return element;
  }

} 
