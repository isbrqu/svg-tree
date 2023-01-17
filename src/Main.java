import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.lang.Math;
import java.util.HashMap;
import java.util.Arrays;
import svgtree.TagCreator;
import svgtree.Proportion;

public class Main {

  private static Document doc;
  private static Element svg;
  private static Element tree;
  private static final String SVG = "http://www.w3.org/2000/svg";
  private static final String output = "out/tree.svg";

  public static final int HEIGHT = 3;

  public static void
  main(String[] args) {
    try {
      drawTree(HEIGHT, Proportion.RADIO);
      Transformer transformer = TransformerFactory
        .newInstance()
        .newTransformer();
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(output);
      transformer.transform(source, result);
    } catch (ParserConfigurationException 
        | TransformerException e) {
      e.printStackTrace();
    }
  }

  private static void
  createSvg() {
    svg = TagCreator.createElementNS("svg");
    svg.setAttribute("style", "background-color: rgb(42, 42, 42);");
    doc.appendChild(svg);
  }
  
  private static void
  createTree() {
    tree = TagCreator.createElementNS("g");
    svg.appendChild(tree);
  }

  private static void
  draw(int height, float x, float y) {
    drawCircle(height, x, y);
    if (height == 0) return;
    float margin = (float) Math.pow(2, height) * Proportion.RADIO;
    float hypotenuse =
      (float) Math.pow(2, height) * Proportion.DIAMETER;
    float x1 = x - margin;
    float y1 = calculateY(hypotenuse, x1, x, y, 1);
    drawLine(x, y, x1, y1, 1);
    draw(height - 1, x1, y1);
    float x2 = x + margin;
    float y2 = calculateY(hypotenuse, x2, x, y, 1);
    drawLine(x, y, x2, y2, -1);
    draw(height - 1, x2, y2);
  }

  private static void
  drawCircle(int height, float x, float y) {
    String color = "#fff";
    Element root = TagCreator.createCircle(x, y, Proportion.RADIO);
    System.out.println("height: " + height);
    Element text = TagCreator.createText(x, y, height);
    tree.appendChild(root);
    tree.appendChild(text);
  }

  private static void
  drawLine(float x1, float y1, float x2, float y2, float direction) {
    float hypotenuse = Proportion.RADIO;
    float xl1 = x1 - direction * Proportion.HALF;
    float yl1 = calculateY(hypotenuse, xl1, x1, y1, 1);
    float xl2 = x2 + direction * Proportion.HALF;
    float yl2 = calculateY(hypotenuse, xl2, x2, y2, -1);
    Element line = TagCreator.createLine(xl1, yl1, xl2, yl2);
    tree.appendChild(line);
  }

  private static void
  drawTree(int treeHeight, float radio) 
  throws ParserConfigurationException {
    doc = TagCreator.getInstance().getDocument();
    createSvg();
    createTree();
    float x = (float) Math.pow(2, HEIGHT + 1) * Proportion.RADIO;
    float y = Proportion.DIAMETER;
    configureViewBox(x, y);
    draw(HEIGHT, x, y);
  }

  private static void
  configureViewBox(float x, float y) {
    HashMap<String,Float> coordinates
      = calculateCoordinates(HEIGHT, x, y);
    calculateCoordinates(HEIGHT, x, y);
    float minX = 0;
    float minY = 0;
    float width = coordinates.get("cx") + Proportion.DIAMETER;
    float height = coordinates.get("cy") + Proportion.DIAMETER;
    Float[] numbers = {minX, minY, width, height};
    String viewBox = Arrays
      .stream(numbers)
      .map(number -> Float.toString(number))
      .reduce("", (result, value)
          -> result.equals("") ? value : result + " " + value);
    svg.setAttribute("viewBox", viewBox);
  }
  
  private static float
  calculateY(float hypotenuse, float x1, float x2, float y2, int d) {
    float opposite = x1 - x2;
    float adjacent = d * calculateLeg(hypotenuse, opposite);
    float y1 = adjacent + y2;
    return y1;
  }

  private static float
  calculateLeg(float hypotenuse, float leg) {
    float hypotenuse2 = (float) Math.pow(hypotenuse, 2);
    float leg2 = (float) Math.pow(leg, 2);
    float result = (float) Math.sqrt(hypotenuse2 - leg2);
    return result;
  }

  private static HashMap<String,Float>
  calculateCoordinates(float height, float x2, float y2) {
    HashMap<String,Float> coordinates
      = new HashMap<String,Float>();
    float nextPower = (float) Math.pow(2, height + 1);
    float x1 = (nextPower - 1) * Proportion.DIAMETER;
    float hypotenuse = (nextPower - 2) * Proportion.DIAMETER;
    float opposite = x1 - x2;
    float adjacent = calculateLeg(hypotenuse, opposite);
    float y1 = adjacent + y2;
    coordinates.put("cx", x1);
    coordinates.put("cy", y1);
    return coordinates;
  }

}
