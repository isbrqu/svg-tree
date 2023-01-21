package svgtree;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class PseudoTag {

  protected static Document document;
  protected Element element;

  public Element getElement() {
    return this.element;
  }

  public static void setDocument(Document doc) {
    document = doc;
  }

} 
