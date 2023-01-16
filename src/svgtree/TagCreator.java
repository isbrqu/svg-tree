package svgtree;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import svgtree.Proportion;

public class TagCreator {

  private static final String SVG = "http://www.w3.org/2000/svg";
  private static final String FONT_SIZE
    = Float.toString(Proportion.RADIO + Proportion.HALF);
  private static final String STROKE_WIDTH
    = Float.toString(Proportion.HALF / 2);
  private static TagCreator instance;

  private Document document;

  public
  TagCreator()
  throws ParserConfigurationException {
    this.document = DocumentBuilderFactory
      .newInstance()
      .newDocumentBuilder()
      .newDocument();
  }

  public Document
  getDocument() {
    return this.document;
  }

  public static TagCreator
  getInstance()
  throws ParserConfigurationException {
    if (instance == null) {
      instance = new TagCreator();
    }
    return instance;
  }

  public static Element
  createElementNS(String type) {
    Element element = null;
    try {
      element = getInstance()
        .getDocument()
        .createElementNS(SVG, type);
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    }
    return element;
  }

  public static Element
  createCircle(float cx, float cy, float r) {
    Element circle = null;
    circle = createElementNS("circle");
    circle.setAttribute("cx", Float.toString(cx));
    circle.setAttribute("cy", Float.toString(cy));
    circle.setAttribute("r", Float.toString(r));
    circle.setAttribute("fill", "#fff");
    return circle;
  }

  public static Element
  createText(float cx, float cy, int num) {
    Element text = createElementNS("text");
    text.setTextContent(Integer.toString(num));
    text.setAttribute("x", Float.toString(cx));
    text.setAttribute("y", Float.toString(cy + Proportion.HALF));
    text.setAttribute("font-size", FONT_SIZE);
    text.setAttribute("fill", "#000");
    text.setAttribute("text-anchor", "middle");
    // text.setAttribute("alignment-baseline", "middle");
    return text;
  }

  public static Element
  createLine(float x1, float y1, float x2, float y2) {
    Element line = createElementNS("line");
    line.setAttribute("x1", Float.toString(x1));
    line.setAttribute("y1", Float.toString(y1));
    line.setAttribute("x2", Float.toString(x2));
    line.setAttribute("y2", Float.toString(y2));
    line.setAttribute("stroke", "#fff");
    line.setAttribute("stroke-width", STROKE_WIDTH);
    return line;
  }

}
