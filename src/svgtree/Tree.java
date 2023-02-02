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

import svgtree.Text;
import svgtree.Svg;
import svgtree.Line;
import svgtree.TagTree;
import conjuntistas.arbol.bb.ArbolBinarioBase;
import conjuntistas.arbol.bb.Nodo;
import conjuntistas.arbol.avl.NodoAVL;

public class Tree {

  private int height;
  private float radio;
  private float diameter;
  private float half;

  private ArbolBinarioBase arbol;
  private Document document;
  private Element svg;
  private TagTree tagTree;

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
    this.svg = (new Svg(this.document)).create();
    this.document.appendChild(this.svg);
    this.tagTree = new TagTree(this.document);
    this.svg.appendChild(this.tagTree.getElement());
  }

  public void initElement() {
    // Posible efecto secundario en PseudoTag.
    // Investigar: Cambiar document entre elementos distintos.
    Text.setDocument(this.document);
    Text.setFontSize(this.radio + this.half);
    Line.setDocument(this.document);
    Line.setStrokeWidth(this.half / 2);
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
    this.initElement();
    NodoAVL raiz = (NodoAVL) this.arbol.getRaiz();
    int height = raiz.getAltura();
    float x = (float) Math.pow(2, height + 1) * this.radio;
    float y = this.diameter;
    Point point = new Point(x, y);
    this.initViewBox(point, raiz);
    draw(point, raiz, height);
  }

  private void draw(Point point, NodoAVL nodo, int level) {
    // crea el circulo
    Element circle = this.document.createElement("circle");
    circle.setAttribute("cx", Float.toString(point.getX()));
    circle.setAttribute("cy", Float.toString(point.getY()));
    circle.setAttribute("r", Float.toString(this.radio));
    circle.setAttribute("fill", "#fff");
    this.tagTree.appendChild(circle);
    // crea el texto dentro del circulo
    Point textPoint = point.translateInY(this.half);
    String elemento = nodo.getElemento().toString();
    Text text = new Text(textPoint, elemento);
    this.tagTree.appendChild(text);
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
      Line line1 = new Line(start, end);
      this.tagTree.appendChild(line1);
      draw(center, izquierdo, level - 1);
    }
    // right node
    NodoAVL derecho = (NodoAVL) nodo.getDerecho();
    if (derecho != null) {
      center = point.bottomRight(xshift, distance);
      start = point.bottomRight(this.half, this.radio);
      end = center.bottomRight(this.half, this.radio);
      Line line2 = new Line(start, end);
      this.tagTree.appendChild(line2);
      draw(center, derecho, level - 1);
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
