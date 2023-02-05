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

public class TagTree {

  private float radio;
  private float diameter;
  private float half;

  private Document document;
  private Element element;

  public TagTree(Document document, float radio) {
    this.document = document;
    this.element = this.document.createElement("g");
    this.setRadio(radio);
  }

  public TagTree(Document document) {
    this(document, 0.5f);
  }

  public void setRadio(float radio) {
    this.radio = radio;
    this.diameter = 2 * radio;
    this.half = radio / 2;
  }

  public Element getElement() {
    return this.element;
  }

  public void draw(Nodo nodo, Point point) {
    NodoAVL raiz = (NodoAVL) nodo;
    this.draw(raiz, point, raiz.getAltura());
  }

  private void draw(NodoAVL nodo, Point point, int level) {
    // crea el circulo
    Element circle = this.document.createElement("circle");
    circle.setAttribute("cx", Float.toString(point.getX()));
    circle.setAttribute("cy", Float.toString(point.getY()));
    circle.setAttribute("r", Float.toString(this.radio));
    this.element.appendChild(circle);
    // crea el texto dentro del circulo
    Point textPoint = point.translateInY(this.half);
    String elemento = nodo.getElemento().toString();
    Element text = this.document.createElement("text");
    text.setTextContent(nodo.getElemento().toString());
    text.setAttribute("x", Float.toString(textPoint.getX()));
    text.setAttribute("y", Float.toString(textPoint.getY()));
    text.setAttribute("font-size",
        Float.toString(this.radio + this.half));
    this.element.appendChild(text);
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
      line1.setAttribute("stroke-width",
          Float.toString(this.half / 2));
      this.element.appendChild(line1);
      this.draw(izquierdo, center, level - 1);
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
      line2.setAttribute("stroke-width",
          Float.toString(this.half / 2));
      this.element.appendChild(line2);
      this.draw(derecho, center, level - 1);
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
