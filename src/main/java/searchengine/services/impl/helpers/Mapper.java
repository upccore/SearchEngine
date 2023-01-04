package searchengine.services.impl.helpers;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RecursiveTask;

public class Mapper extends RecursiveTask<Set<Node>> {

    private static final Set<Node> allNodes = ConcurrentHashMap.newKeySet();
    private final VariablesAndMethods VAM;
    private final Node node;

    public Mapper(Node node) {
        this.node = node;
        VAM = node.getVAM();
    }

    @Override
    protected Set<Node> compute() {
        allNodes.add(node);
        Set<Node> childSet = this.getChildren(node);
        List<Mapper> taskMapper = new ArrayList<>();
        for (Node child : childSet) {
            if ((!allNodes.contains(child)) && (child.getLevel() < VAM.getDepthOfCoverage())) {
                taskMapper
                        .add((Mapper) new Mapper(child).fork());
            }
        }
        for (Mapper task : taskMapper) {
            allNodes.addAll(task.join());
        }
        return allNodes;
    }

    public synchronized Set<Node> getChildren(Node ancestor) {
        if (ancestor != null) {
            Set<String> childrenUrlSet = VAM.parseAncestor(ancestor);
            if (childrenUrlSet != null) {
                for (String url : childrenUrlSet) {
                    Node child = new Node(url, ancestor);
                    if (child.getLevel() < VAM
                            .getDepthOfCoverage()) {
                        ancestor.addChildren(child);
                    }
                }
            }
            return ancestor.getSetOfNodesWithLinks();
        } else {
            return new LinkedHashSet<>();
        }
    }
}