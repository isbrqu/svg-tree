package svgtree;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import conjuntistas.arbol.bb.ArbolBinarioBase;
import svgtree.CSSStyle;

public class Html {

  private static String URI = "http://www.w3.org/1999/xhtml";

  private String name;
  private Document document;
  private Element html;
  private Element head;
  private Element body;
  private Element style;
  private Element content;

  public Html() {
    try {
      this.document = DocumentBuilderFactory
        .newInstance()
        .newDocumentBuilder()
        .newDocument();
      this.html = this.document.createElementNS(URI, "html");
      this.head = this.document.createElement("head");
      this.style = this.document.createElement("style");
      this.body = this.document.createElement("body");
      this.content = this.document.createElement("div");
      this.content.setAttribute("class", "content");
      this.head.appendChild(style);
      this.body.appendChild(content);
      this.html.appendChild(head);
      this.html.appendChild(body);
      this.document.appendChild(html);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Html(String name) {
    this();
    this.name = name;
  }

  public void draw(ArbolBinarioBase arbol, String text) {
    this.initCss();
    Element div = this.document.createElement("div");
    if (text != null) {
      Element h2 = this.document.createElement("h2");
      h2.setTextContent(text);
      div.appendChild(h2);
    }
    SvgTree svgTree = new SvgTree(this.document);
    svgTree.draw(arbol);
    Element svg = svgTree.getElement();
    div.appendChild(svg);
    this.content.appendChild(div);
  }

  public void draw(ArbolBinarioBase arbol) {
    this.draw(arbol, null);
  }

  public void initCss() {
    StringBuilder rules = new StringBuilder();
    CSSStyle content = new CSSStyle(".content");
    content.addProperty("display", "flex");
    content.addProperty("justify-content", "center");
    rules.append(content.toString());
    CSSStyle div = new CSSStyle("div");
    div.addProperty("margin", "0 1px");
    div.addProperty("text-align", "center");
    rules.append(div.toString());
    CSSStyle svg = new CSSStyle("svg");
    svg.addProperty("border-radius", "1%");
    svg.addProperty("width", "500px");
    svg.addProperty("height", "500px");
    svg.addProperty("background-color", "rgb(42, 42, 42)");
    rules.append(svg.toString());
    CSSStyle circle = new CSSStyle("circle");
    circle.addProperty("fill", "#ffffff");
    rules.append(circle.toString());
    CSSStyle line = new CSSStyle("line");
    line.addProperty("stroke", "#fff");
    rules.append(line.toString());
    CSSStyle text = new CSSStyle("text");
    text.addProperty("fill", "#000000");
    text.addProperty("text-anchor", "middle");
    text.addProperty("alignment-baseline", "middle");
    rules.append(text.toString());
    this.style.setTextContent(rules.toString());
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
