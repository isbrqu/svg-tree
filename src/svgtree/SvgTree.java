package svgtree;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.lang.Math;

import svgtree.TagTree;

import conjuntistas.arbol.bb.ArbolBinarioBase;
import conjuntistas.arbol.bb.Nodo;
import conjuntistas.arbol.avl.NodoAVL;

public class SvgTree {

  private static String URI = "http://www.w3.org/2000/svg";
  private static final float RADIO = .5f;
  private static final float DIAMETER = 2 * RADIO;

  private Element element;
  private Document document;

  public SvgTree(Document document) {
    try {
      this.document = document;
      if (this.document == null)
        this.document = DocumentBuilderFactory
          .newInstance()
          .newDocumentBuilder()
          .newDocument();
      this.element = this.document.createElementNS(URI, "svg");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public SvgTree() {
    this(null);
  }

  public Element getElement() {
    return this.element;
  }

  public void draw(ArbolBinarioBase tree) {
    this.draw(tree.getRaiz());
  }

  public void draw(Nodo raiz) {
    int height = ((NodoAVL) raiz).getAltura();
    float x = (float) Math.pow(2, height + 1) * RADIO;
    float y = DIAMETER;
    Point point = new Point(x, y);
    this.setViewBox(height, point);
    // generate tree
    TagTree tree = new TagTree(this.document);
    tree.setRadio(RADIO);
    tree.draw(raiz, point);
    Element group = tree.getElement();
    while (this.element.hasChildNodes())
      this.element.removeChild(this.element.getFirstChild());
    this.element.appendChild(group);
  }

  private void setViewBox(int height, Point point) {
    float power = (float) Math.pow(2, height);
    float nextPower = 2 * power;
    float xshift = (power - 1) * DIAMETER;
    float distance = (nextPower - 2) * DIAMETER;
    Point dim = point
      .bottomRight(xshift, distance)
      .translate(DIAMETER, DIAMETER);
    StringBuilder viewBox = new StringBuilder();
    viewBox.append("0 ");
    viewBox.append("0 ");
    viewBox.append(dim.getX() + " ");
    viewBox.append(dim.getY());
    this.element.setAttribute("viewBox", viewBox.toString());
  }
  
  public void save(String filename) {
    try {
      DOMSource source = new DOMSource(this.document);
      Transformer transformer = TransformerFactory
        .newInstance()
        .newTransformer();
      StreamResult result = new StreamResult(filename);
      transformer.transform(source, result);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

} 
