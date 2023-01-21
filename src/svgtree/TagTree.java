package svgtree;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import svgtree.PseudoTag;

public class TagTree extends PseudoTag {

  private static final String TYPE = "g";

  public TagTree(Document document) {
    this.element = document.createElement(TYPE);
  }

  public void appendChild(PseudoTag pseudotag) {
    this.element.appendChild(pseudotag.getElement());
  }

} 
