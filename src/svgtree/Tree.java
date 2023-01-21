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
import svgtree.Circle;

public class Tree {

  private Document doc;
  private Element svg;
  private Element tagTree;

  private int x;
  private int y;
  private int height;
  private float radio;
  private float diameter;
  private float half;
  private TagCreator tagCreator;
  private Circle circle;

  public Tree(int height, float radio) 
  throws ParserConfigurationException {
    this.height = height;
    this.radio = radio;
    this.diameter = 2 * radio;
    this.half = radio / 2;
    this.tagCreator = new TagCreator(this.radio);
    this.svg = this.tagCreator.getTagSVG();
    this.tagTree = this.tagCreator.getTagTree();
    Document document = this.tagCreator.getDocument();
    this.circle = new Circle(document, this.radio);
  }

  public Tree(int height)
  throws ParserConfigurationException {
    this(height, 0.5f);
  }

  public void
  drawTree() 
  throws ParserConfigurationException {
    float x = (float) Math.pow(2, this.height + 1) * this.radio;
    float y = this.diameter;
    Point point = new Point(x, y);
    draw(point, this.height);
    configureViewBox(x, y, this.radio, this.height);
  }

  private void
  configureViewBox(float x, float y, float radio, float treeHeight) {
    HashMap<String,Float> coordinates
      = coordinates(x, y, radio, treeHeight);
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
    this.svg.setAttribute("viewBox", viewBox);
  }
  
  private HashMap<String,Float>
  coordinates(float x2, float y2, float radio, float height) {
    HashMap<String,Float> coordinates = new HashMap<String,Float>();
    float diameter = 2 * radio;
    float nextPower = (float) Math.pow(2, height + 1);
    float x1 = (nextPower - 1) * diameter;
    float hypotenuse = (nextPower - 2) * diameter;
    float opposite = x1 - x2;
    float adjacent = Utils.calculateLeg(hypotenuse, opposite);
    float y1 = adjacent + y2;
    coordinates.put("cx", x1);
    coordinates.put("cy", y1);
    return coordinates;
  }

  private void
  draw(Point point, int height) {
    float x = point.getX();
    float y = point.getY();
    Point textPoint = point.translateInY(this.half);
    drawCircle(point, height);
    drawText(textPoint, height);
    if (height == 0) return;
    float margin = (float) Math.pow(2, height) * this.radio;
    float hypotenuse =
      (float) Math.pow(2, height) * this.diameter;
    Point point1 = point.bottomLeft(margin, hypotenuse);
    drawLine(point, point1, 1);
    draw(point1, height - 1);
    Point point2 = point.bottomRight(margin, hypotenuse);
    drawLine(point, point2, -1);
    draw(point2, height - 1);
  }

  private void
  drawCircle(Point point, int height) {
    Element root = this.circle.create(point);
    this.tagTree.appendChild(root);
  }

  private void
  drawText(Point point, int height) {
    float x = point.getX();
    float y = point.getY();
    Element text = this.tagCreator.createText(x, y, height);
    this.tagTree.appendChild(text);
  }

  // TODO: Implementar Point en drawLine
  private void
  drawLine(Point point1, Point point2, float direction) {
    float hypotenuse = this.radio;
    float margin = direction * this.half;
    Point start = point1.bottomLeft(margin, hypotenuse);
    Point end = point2.bottomLeft(margin, hypotenuse);
    float xl1 = start.getX();
    float yl1 = start.getY();
    float xl2 = end.getX();
    float yl2 = end.getY();
    Element line = this.tagCreator.createLine(xl1, yl1, xl2, yl2);
    this.tagTree.appendChild(line);
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
