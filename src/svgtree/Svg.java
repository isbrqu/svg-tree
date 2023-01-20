package svgtree;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Svg {

  private static final String TYPE = "svg";
  private static final String URI = "http://www.w3.org/2000/svg";
  private static final String COLOR = "rgb(42, 42, 42);";
  private static final String STYLE = "background-color: " + COLOR;
  private Document document;

  public Svg(Document document) {
    this.document = document;
  }

  public Element
  create() {
    Element element = this.document.createElementNS(URI, TYPE);
    element.setAttribute("style", STYLE);
    return element;
  }

} 
