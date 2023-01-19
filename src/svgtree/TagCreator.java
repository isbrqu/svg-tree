package svgtree;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.DOMException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import svgtree.Proportion;

public class TagCreator {

  private static final String SVG = "http://www.w3.org/2000/svg";

  private Document document;
  private float radio;
  private String fontSize;
  private String strokeWidth;

  public
  TagCreator()
  throws ParserConfigurationException {
    this.document = DocumentBuilderFactory
      .newInstance()
      .newDocumentBuilder()
      .newDocument();
  }

  public
  TagCreator(float radio)
  throws ParserConfigurationException {
    this.radio = radio;
    this.fontSize = Float.toString(radio + radio / 2);
    this.strokeWidth = Float.toString(radio / 4);
    this.document = DocumentBuilderFactory
      .newInstance()
      .newDocumentBuilder()
      .newDocument();
  }

  public Document
  getDocument() {
    return this.document;
  }

  public Element
  createElementNS(String type) {
    Element element = null;
    try {
      element = this.document.createElementNS(SVG, type);
    } catch (DOMException e) {
      e.printStackTrace();
    }
    return element;
  }

  public Element
  createElement(String type) {
    Element element = null;
    try {
      element = this.document.createElement(type);
    } catch (DOMException e) {
      e.printStackTrace();
    }
    return element;
  }

  public Element
  createCircle(float cx, float cy, float r) {
    Element circle = null;
    circle = createElement("circle");
    circle.setAttribute("cx", Float.toString(cx));
    circle.setAttribute("cy", Float.toString(cy));
    circle.setAttribute("r", Float.toString(r));
    circle.setAttribute("fill", "#fff");
    return circle;
  }

  public Element
  createText(float cx, float cy, int num) {
    Element text = createElement("text");
    text.setTextContent(Integer.toString(num));
    text.setAttribute("x", Float.toString(cx));
    text.setAttribute("y", Float.toString(cy + Proportion.HALF));
    text.setAttribute("font-size", this.fontSize);
    text.setAttribute("fill", "#000");
    text.setAttribute("text-anchor", "middle");
    // text.setAttribute("alignment-baseline", "middle");
    return text;
  }

  public Element
  createLine(float x1, float y1, float x2, float y2) {
    Element line = createElement("line");
    line.setAttribute("x1", Float.toString(x1));
    line.setAttribute("y1", Float.toString(y1));
    line.setAttribute("x2", Float.toString(x2));
    line.setAttribute("y2", Float.toString(y2));
    line.setAttribute("stroke", "#fff");
    line.setAttribute("stroke-width", this.strokeWidth);
    return line;
  }

}
