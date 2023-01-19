package svgtree;

import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.lang.Math;
import java.util.HashMap;
import java.util.Arrays;
import svgtree.TagCreator;

public class Tree {

  private Document doc;
  private Element svg;
  private Element tree;

  private int x;
  private int y;
  private int HEIGHT;
  private float radio;
  private float diameter;
  private float half;
  private TagCreator tagCreator;

  public Tree(int height, float radio) 
  throws ParserConfigurationException {
    this.HEIGHT = height;
    this.radio = radio;
    this.diameter = 2 * radio;
    this.half = radio / 2;
    float x = (float) Math.pow(2, HEIGHT + 1) * this.radio;
    float y = this.diameter;
    this.tagCreator = new TagCreator(this.radio);
    this.doc = this.tagCreator.getDocument();
    // configureViewBox(x, y);
  }

  public Tree(int height)
  throws ParserConfigurationException {
    this(height, 0.5f);
  }

  public Document getDocument() {
    return this.doc;
  }

  private void
  createSvg() {
    svg = this.tagCreator.createElementNS("svg");
    svg.setAttribute("style", "background-color: rgb(42, 42, 42);");
    doc.appendChild(svg);
  }
  
  private void
  createTree() {
    tree = this.tagCreator.createElementNS("g");
    svg.appendChild(tree);
  }

  private void
  draw(int height, float x, float y) {
    drawCircle(height, x, y);
    if (height == 0) return;
    float margin = (float) Math.pow(2, height) * this.radio;
    float hypotenuse =
      (float) Math.pow(2, height) * this.diameter;
    float x1 = x - margin;
    float y1 = calculateY(hypotenuse, x1, x, y, 1);
    drawLine(x, y, x1, y1, 1);
    draw(height - 1, x1, y1);
    float x2 = x + margin;
    float y2 = calculateY(hypotenuse, x2, x, y, 1);
    drawLine(x, y, x2, y2, -1);
    draw(height - 1, x2, y2);
  }

  private void
  drawCircle(int height, float x, float y) {
    String color = "#fff";
    Element root = this.tagCreator.createCircle(x, y, this.radio);
    System.out.println("height: " + height);
    Element text = this.tagCreator.createText(x, y, height);
    tree.appendChild(root);
    tree.appendChild(text);
  }

  private void
  drawLine(float x1, float y1, float x2, float y2, float direction) {
    float hypotenuse = this.radio;
    float xl1 = x1 - direction * this.half;
    float yl1 = calculateY(hypotenuse, xl1, x1, y1, 1);
    float xl2 = x2 + direction * this.half;
    float yl2 = calculateY(hypotenuse, xl2, x2, y2, -1);
    Element line = this.tagCreator.createLine(xl1, yl1, xl2, yl2);
    tree.appendChild(line);
  }

  public void
  drawTree(int treeHeight, float radio) 
  throws ParserConfigurationException {
    doc = this.tagCreator.getDocument();
    createSvg();
    createTree();
    float x = (float) Math.pow(2, HEIGHT + 1) * this.radio;
    float y = this.diameter;
    configureViewBox(x, y);
    draw(HEIGHT, x, y);
  }

  private void
  configureViewBox(float x, float y) {
    HashMap<String,Float> coordinates
      = calculateCoordinates(HEIGHT, x, y);
    calculateCoordinates(HEIGHT, x, y);
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
  
  private float
  calculateY(float hypotenuse, float x1, float x2, float y2, int d) {
    float opposite = x1 - x2;
    float adjacent = d * calculateLeg(hypotenuse, opposite);
    float y1 = adjacent + y2;
    return y1;
  }

  private float
  calculateLeg(float hypotenuse, float leg) {
    float hypotenuse2 = (float) Math.pow(hypotenuse, 2);
    float leg2 = (float) Math.pow(leg, 2);
    float result = (float) Math.sqrt(hypotenuse2 - leg2);
    return result;
  }

  private HashMap<String,Float>
  calculateCoordinates(float height, float x2, float y2) {
    HashMap<String,Float> coordinates
      = new HashMap<String,Float>();
    float nextPower = (float) Math.pow(2, height + 1);
    float x1 = (nextPower - 1) * this.diameter;
    float hypotenuse = (nextPower - 2) * this.diameter;
    float opposite = x1 - x2;
    float adjacent = calculateLeg(hypotenuse, opposite);
    float y1 = adjacent + y2;
    coordinates.put("cx", x1);
    coordinates.put("cy", y1);
    return coordinates;
  }

} 
