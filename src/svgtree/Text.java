package svgtree;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import svgtree.PseudoTag;

public class Text extends PseudoTag {

  private static String fontSize;
  private static final String TYPE = "text";
  private static final String COLOR = "#000";

  public Text(Point point, String text) {
    this.element = document.createElement(TYPE);
    this.element.setTextContent(text);
    this.element.setAttribute("x", Float.toString(point.getX()));
    this.element.setAttribute("y", Float.toString(point.getY()));
    this.element.setAttribute("fill", COLOR);
    this.element.setAttribute("font-size", fontSize);
    this.element.setAttribute("text-anchor", "middle");
    this.element.setAttribute("alignment-baseline", "middle");
  }

  public static void setFontSize(float fontSize) {
    Text.fontSize = Float.toString(fontSize);
  }

} 
