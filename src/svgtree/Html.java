package svgtree;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import conjuntistas.arbol.bb.ArbolBinarioBase;

public class Html {

  private static String URI = "http://www.w3.org/1999/xhtml";

  private String name;
  private Document document;
  private Element html;

  public Html() {
    try {
      this.document = DocumentBuilderFactory
        .newInstance()
        .newDocumentBuilder()
        .newDocument();
      this.html = this.document.createElementNS(URI, "html");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Html(String name) {
    this();
    this.name = name;
  }

  public void draw(ArbolBinarioBase arbol, String text) {
    SvgTree svgTree = new SvgTree(this.document);
    svgTree.draw(arbol);
    Element svg = svgTree.getElement();
    if (text != null) {
      Element h2 = this.document.createElement("h2");
      h2.setTextContent(text);
      this.html.appendChild(h2);
    }
    this.html.appendChild(svg);
  }

  public void draw(ArbolBinarioBase arbol) {
    this.draw(arbol, null);
  }

  public void save(String filename) {
    try {
      DOMSource source = new DOMSource(document);
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
