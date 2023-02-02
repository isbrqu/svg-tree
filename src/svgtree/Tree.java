package svgtree;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.lang.Math;

import conjuntistas.arbol.bb.ArbolBinarioBase;
import conjuntistas.arbol.bb.Nodo;
import conjuntistas.arbol.avl.NodoAVL;

public class Tree {

  private float radio;
  private float diameter;
  private float half;

  private ArbolBinarioBase arbol;
  private Document document;
  private Element svg;
  private Element group;

  public Tree(ArbolBinarioBase arbol, float radio)
  throws ParserConfigurationException {
    this.arbol = arbol;
    this.radio = radio;
    this.diameter = 2 * radio;
    this.half = radio / 2;
  }

  public Tree(ArbolBinarioBase arbol)
  throws ParserConfigurationException {
    this(arbol, 0.5f);
  }

  public void initDocument()
  throws ParserConfigurationException {
    // Tags and elements.
    this.document = DocumentBuilderFactory
      .newInstance()
      .newDocumentBuilder()
      .newDocument();
    String URI = "http://www.w3.org/2000/svg";
    this.svg = this.document.createElementNS(URI, "svg");
    this.svg.setAttribute("style",
        "background-color: rgb(42, 42, 42);");
    this.document.appendChild(this.svg);
    this.group = this.document.createElement("g");
    this.svg.appendChild(this.group);
  }

  public void initViewBox(Point point, Nodo raiz) {
    // Establece los límites de vista del svg.
    // Cálcula el punto inferior derecho y hace un corrimiento
    // a la derecha.
    int height = ((NodoAVL) raiz).getAltura();
    float power = (float) Math.pow(2, height);
    float nextPower = 2 * power;
    float xshift = (power - 1) * diameter;
    float distance = (nextPower - 2) * diameter;
    Point dim = point
      .bottomRight(xshift, distance)
      .translate(diameter, diameter);
    String viewBox = "0 0 " + dim.getX() + " " + dim.getY();
    this.svg.setAttribute("viewBox", viewBox);
  }

  public void draw()
  throws ParserConfigurationException {
    this.initDocument();
    NodoAVL raiz = (NodoAVL) this.arbol.getRaiz();
    int height = raiz.getAltura();
    float x = (float) Math.pow(2, height + 1) * this.radio;
    float y = this.diameter;
    Point point = new Point(x, y);
    this.initViewBox(point, raiz);
    this.draw(point, raiz, height);
  }

  private void draw(Point point, NodoAVL nodo, int level) {
    // crea el circulo
    Element circle = this.document.createElement("circle");
    circle.setAttribute("cx", Float.toString(point.getX()));
    circle.setAttribute("cy", Float.toString(point.getY()));
    circle.setAttribute("r", Float.toString(this.radio));
    circle.setAttribute("fill", "#fff");
    this.group.appendChild(circle);
    // crea el texto dentro del circulo
    Point textPoint = point.translateInY(this.half);
    String elemento = nodo.getElemento().toString();
    Element text = this.document.createElement("text");
    text.setTextContent(nodo.getElemento().toString());
    text.setAttribute("x", Float.toString(textPoint.getX()));
    text.setAttribute("y", Float.toString(textPoint.getY()));
    text.setAttribute("fill", "#00");
    text.setAttribute("font-size",
        Float.toString(this.radio + this.half));
    text.setAttribute("text-anchor", "middle");
    text.setAttribute("alignment-baseline", "middle");
    this.group.appendChild(text);
    // children
    int height = nodo.getAltura();
    if (height == 0) return;
    Point center, start, end;
    float xshift = (float) Math.pow(2, level) * this.radio;
    float distance = (float) Math.pow(2, level) * this.diameter;
    // left node
    NodoAVL izquierdo = (NodoAVL) nodo.getIzquierdo();
    if (izquierdo != null) {
      center = point.bottomLeft(xshift, distance);
      start = point.bottomLeft(this.half, this.radio);
      end = center.bottomLeft(this.half, this.radio);
      Element line1 = document.createElement("line");
      line1.setAttribute("x1", Float.toString(start.getX()));
      line1.setAttribute("y1", Float.toString(start.getY()));
      line1.setAttribute("x2", Float.toString(end.getX()));
      line1.setAttribute("y2", Float.toString(end.getY()));
      line1.setAttribute("stroke", "#fff");
      line1.setAttribute("stroke-width",
          Float.toString(this.half / 2));
      this.group.appendChild(line1);
      draw(center, izquierdo, level - 1);
    }
    // right node
    NodoAVL derecho = (NodoAVL) nodo.getDerecho();
    if (derecho != null) {
      center = point.bottomRight(xshift, distance);
      start = point.bottomRight(this.half, this.radio);
      end = center.bottomRight(this.half, this.radio);
      Element line2 = document.createElement("line");
      line2.setAttribute("x1", Float.toString(start.getX()));
      line2.setAttribute("y1", Float.toString(start.getY()));
      line2.setAttribute("x2", Float.toString(end.getX()));
      line2.setAttribute("y2", Float.toString(end.getY()));
      line2.setAttribute("stroke", "#fff");
      line2.setAttribute("stroke-width",
          Float.toString(this.half / 2));
      this.group.appendChild(line2);
      this.draw(center, derecho, level - 1);
    }
  }

  public void save(String filename)
  throws TransformerException {
    DOMSource source = new DOMSource(document);
    Transformer transformer = TransformerFactory
      .newInstance()
      .newTransformer();
    StreamResult result = new StreamResult(filename);
    transformer.transform(source, result);
  }

} 
