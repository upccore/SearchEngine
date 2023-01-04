package searchengine.services.impl.helpers;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Node {

    private final VariablesAndMethods VAM;
    private final String startURL;
    private final String url;
    private final Node ancestor;
    private int level;
    private final Set<Node> setOfNodesWithLinks = ConcurrentHashMap.newKeySet();

    public Node(String url, VariablesAndMethods VAM) {
        this.url = url;
        this.ancestor = null;
        this.level = 0;
        this.startURL = url;
        this.VAM = VAM;
    }

    public Node(String url, Node ancestor) {
        this.url = url;
        this.ancestor = ancestor;
        this.level = 1 + ancestor.getLevel();
        this.startURL = ancestor.startURL;
        this.VAM = ancestor.getVAM();
    }

    public synchronized void addChildren(Node node) {// метод добавления в набор со ссылками ноды
        setOfNodesWithLinks.add(node);
    }

    public String getString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\t".repeat(level)).append(level).append(" - ").append(url).append("\n");
        for (Node child : setOfNodesWithLinks) {
            builder.append(child.getString());
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Node node = (Node) obj;
        return Objects.equals(url, node.url);
    }

    @Override
    public int hashCode() {
        if (url != null) {
            return url.hashCode();
        } else return 0;
    }
}