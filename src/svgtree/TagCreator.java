package svgtree;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.DOMException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import svgtree.Circle;
import svgtree.Text;

public class TagCreator {

  private static final String SVG = "http://www.w3.org/2000/svg";

  private Document document;
  private String fontSize;
  private String strokeWidth;
  private Element tagTree;
  private Element tagSvg;
  private Circle circle;
  private Text text;

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
    this();
    this.circle = new Circle(this.document, radio);
    float fontSize = radio + radio / 2;
    this.text = new Text(this.document, fontSize);
    this.strokeWidth = Float.toString(radio / 4);
    this.tagSvg = this.createElementNS("svg");
    String style = "background-color: rgb(42, 42, 42);";
    this.tagSvg.setAttribute("style", style);
    this.tagTree = this.createElementNS("g");
    this.tagSvg.appendChild(this.tagTree);
    this.document.appendChild(this.tagSvg);
  }

  public Document
  getDocument() {
    return this.document;
  }

  public Element
  getTagSVG() {
    return this.tagSvg;
  }

  public Element
  getTagTree() {
    return this.tagTree;
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
    return this.circle.create(cx, cy, r);
  }

  public Element
  createText(float x, float y, int num) {
    return this.text.create(x, y, Integer.toString(num));
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
