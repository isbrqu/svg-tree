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
      // document
      this.html = this.document.createElementNS(URI, "html");
      this.document.appendChild(html);
      // html
      this.head = this.document.createElement("head");
      this.html.appendChild(head);
      this.body = this.document.createElement("body");
      this.html.appendChild(body);
      // head
      this.style = this.document.createElement("style");
      this.style.setTextContent(generateCssRules());
      this.head.appendChild(style);
      // body
      this.content = this.document.createElement("div");
      this.content.setAttribute("class", "content");
      this.body.appendChild(content);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Html(String text) {
    this();
    Element h1 = this.document.createElement("h1");
    h1.setTextContent(text);
    this.body.insertBefore(h1, this.content);
  }

  public void draw(ArbolBinarioBase arbol, String text) {
    Element div = this.document.createElement("div");
    div.setAttribute("class", "item");
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

  public static String generateCssRules() {
    StringBuilder rules = new StringBuilder();
    CSSStyle content = new CSSStyle(".content");
    content.addProperty("display", "flex");
    content.addProperty("flex-flow", "row wrap");
    content.addProperty("gap", "10px");
    content.addProperty("justify-content", "space-evenly");
    rules.append(content.toString());
    CSSStyle hx = new CSSStyle("h1, h2");
    hx.addProperty("text-align", "center");
    rules.append(hx.toString());
    CSSStyle svg = new CSSStyle("svg");
    svg.addProperty("border-radius", "1%");
    svg.addProperty("width", "400px");
    svg.addProperty("height", "400px");
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
    return rules.toString();
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
