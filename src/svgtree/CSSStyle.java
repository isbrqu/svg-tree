package svgtree;

import java.util.Map;
import java.util.HashMap;
import java.lang.StringBuilder;

public class CSSStyle {
  private String selector;
  private Map<String, String> properties;

  public CSSStyle(String selector) {
    this.selector = selector;
    this.properties = new HashMap<>();
  }

  public void addProperty(String property, String value) {
    properties.put(property, value);
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(selector).append("{");
    for (Map.Entry<String, String> entry : properties.entrySet()) {
      sb.append(entry.getKey())
        .append(":")
        .append(entry.getValue())
        .append(";");
    }
    sb.append("}");
    return sb.toString();
  }
}
