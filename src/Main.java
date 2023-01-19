import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import svgtree.Tree;

public class Main {

  private static final String output = "out/tree.svg";
  public static final int HEIGHT = 3;

  public static void
  main(String[] args) {
    try {
      Tree tree = new Tree(HEIGHT);
      tree.drawTree(HEIGHT, .5f);
      Document doc = tree.getDocument();
      Transformer transformer = TransformerFactory
        .newInstance()
        .newTransformer();
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(output);
      transformer.transform(source, result);
    } catch (ParserConfigurationException 
        | TransformerException e) {
      e.printStackTrace();
    }
  }

}
