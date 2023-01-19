package svgtree;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.lang.Math;
import java.util.HashMap;
import java.util.Arrays;
import svgtree.TagCreator;
import svgtree.Utils;

public class Tree {

  private Document doc;
  private Element svg;
  private Element tree;

  private int x;
  private int y;
  private int height;
  private float radio;
  private float diameter;
  private float half;
  private TagCreator tagCreator;

  public Tree(int height, float radio) 
  throws ParserConfigurationException {
    this.height = height;
    this.radio = radio;
    this.diameter = 2 * radio;
    this.half = radio / 2;
    float x = (float) Math.pow(2, this.height + 1) * this.radio;
    float y = this.diameter;
    this.tagCreator = new TagCreator(this.radio);
  }

  public Tree(int height)
  throws ParserConfigurationException {
    this(height, 0.5f);
  }

  private void
  createSvg() {
    svg = this.tagCreator.createElementNS("svg");
    svg.setAttribute("style", "background-color: rgb(42, 42, 42);");
    this.tagCreator.getDocument().appendChild(svg);
  }
  
  private void
  createTree() {
    tree = this.tagCreator.createElementNS("g");
    svg.appendChild(tree);
  }

  public void
  drawTree() 
  throws ParserConfigurationException {
    createSvg();
    createTree();
    float x = (float) Math.pow(2, this.height + 1) * this.radio;
    float y = this.diameter;
    configureViewBox(x, y);
    draw(x, y, this.height);
  }

  private void
  draw(float x, float y, int height) {
    drawNode(x, y, height);
    if (height == 0) return;
    float margin = (float) Math.pow(2, height) * this.radio;
    float hypotenuse =
      (float) Math.pow(2, height) * this.diameter;
    float x1 = x - margin;
    float y1 = Utils.calculateY(hypotenuse, x1, x, y, 1);
    drawLine(x, y, x1, y1, 1);
    draw(x1, y1, height - 1);
    float x2 = x + margin;
    float y2 = Utils.calculateY(hypotenuse, x2, x, y, 1);
    drawLine(x, y, x2, y2, -1);
    draw(x2, y2, height - 1);
  }

  private void
  drawNode(float cx, float cy, int height) {
    drawCircle(cx, cy, height);
    drawText(cx, cy + this.half, height);
  }

  private void
  drawCircle(float cx, float cy, int height) {
    String color = "#fff";
    Element root = this.tagCreator.createCircle(cx, cy, this.radio);
    tree.appendChild(root);
  }

  private void
  drawText(float x, float y, int height) {
    Element text = this.tagCreator.createText(x, y, height);
    tree.appendChild(text);
  }

  private void
  drawLine(float x1, float y1, float x2, float y2, float direction) {
    float hypotenuse = this.radio;
    float xl1 = x1 - direction * this.half;
    float yl1 = Utils.calculateY(hypotenuse, xl1, x1, y1, 1);
    float xl2 = x2 + direction * this.half;
    float yl2 = Utils.calculateY(hypotenuse, xl2, x2, y2, -1);
    Element line = this.tagCreator.createLine(xl1, yl1, xl2, yl2);
    tree.appendChild(line);
  }

  private void
  configureViewBox(float x, float y) {
    HashMap<String,Float> coordinates
      = calculateCoordinates(this.height, x, y);
    float minX = 0;
    float minY = 0;
    float width = coordinates.get("cx") + this.diameter;
    float height = coordinates.get("cy") + this.diameter;
    Float[] numbers = {minX, minY, width, height};
    String viewBox = Arrays
      .stream(numbers)
      .map(number -> Float.toString(number))
      .reduce("", (result, value)
          -> result.equals("") ? value : result + " " + value);
    svg.setAttribute("viewBox", viewBox);
  }
  
  private HashMap<String,Float>
  calculateCoordinates(float height, float x2, float y2) {
    HashMap<String,Float> coordinates
      = new HashMap<String,Float>();
    float nextPower = (float) Math.pow(2, height + 1);
    float x1 = (nextPower - 1) * this.diameter;
    float hypotenuse = (nextPower - 2) * this.diameter;
    float opposite = x1 - x2;
    float adjacent = Utils.calculateLeg(hypotenuse, opposite);
    float y1 = adjacent + y2;
    coordinates.put("cx", x1);
    coordinates.put("cy", y1);
    return coordinates;
  }

  public void
  save(String filename) {
    try {
      Document document = this.tagCreator.getDocument();
      DOMSource source = new DOMSource(document);
      Transformer transformer = TransformerFactory
        .newInstance()
        .newTransformer();
      StreamResult result = new StreamResult(filename);
      transformer.transform(source, result);
    } catch (TransformerException e) {
      e.printStackTrace();
    }
  }

} 
