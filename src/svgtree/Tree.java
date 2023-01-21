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
  private Element tagTree;

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
    this.tagCreator = new TagCreator(this.radio);
    this.svg = this.tagCreator.getTagSVG();
    this.tagTree = this.tagCreator.getTagTree();
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
    configureViewBox(x, y, this.radio, this.height);
    draw(x, y, this.height);
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
    this.tagTree.appendChild(root);
  }

  private void
  drawText(float x, float y, int height) {
    Element text = this.tagCreator.createText(x, y, height);
    this.tagTree.appendChild(text);
  }

  private void
  drawLine(float x1, float y1, float x2, float y2, float direction) {
    float hypotenuse = this.radio;
    float xl1 = x1 - direction * this.half;
    float yl1 = Utils.calculateY(hypotenuse, xl1, x1, y1, 1);
    float xl2 = x2 + direction * this.half;
    float yl2 = Utils.calculateY(hypotenuse, xl2, x2, y2, -1);
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
