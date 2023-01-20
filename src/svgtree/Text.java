package svgtree;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Text {

  private final String type = "text";
  private final String color = "#000";
  private String fontSize;
  private Document document;

  public Text(Document document, float fontSize) {
    this.document = document;
    this.fontSize = Float.toString(fontSize);
  }

  public Element
  create(float x, float y, String text) {
    Element element = this.document.createElement(type);
    element.setTextContent(text);
    element.setAttribute("x", Float.toString(x));
    element.setAttribute("y", Float.toString(y));
    element.setAttribute("fill", this.color);
    element.setAttribute("font-size", this.fontSize);
    element.setAttribute("text-anchor", "middle");
    element.setAttribute("alignment-baseline", "middle");
    return element;
  }

} 
