import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import svgtree.Tree;

public class Main {

  private static final String output = "out/tree.svg";
  public static final int HEIGHT = 3;

  public static void
  main(String[] args) {
    try {
      Tree tree1 = new Tree(3);
      tree1.drawTree();
      tree1.save(output);
    } catch (ParserConfigurationException | TransformerException e) {
      e.printStackTrace();
    }
  }

}
