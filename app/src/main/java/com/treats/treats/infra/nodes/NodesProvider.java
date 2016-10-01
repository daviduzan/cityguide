package com.treats.treats.infra.nodes;

import com.treats.treats.infra.factories.NodeFactory;

import java.util.HashMap;

/**
 * Created by david.uzan on 4/19/2016.
 */
public class NodesProvider {
    private static final String FILE_NAME = "nodes_provider_initial_id";

    private final HashMap<NodeFactory.NodeType, BaseDataNode> mNodesMap = new HashMap<>();

    public static NodesProvider getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public BaseDataNode getDataNode(NodeFactory.NodeType nodeType) {
        synchronized (mNodesMap) {
            BaseDataNode baseDataNode = mNodesMap.get(nodeType);
            if (baseDataNode == null) {
                baseDataNode = (BaseDataNode) NodeFactory.createNewNode(nodeType);
                mNodesMap.put(nodeType, baseDataNode);
            }
            return baseDataNode;
        }
    }

    public void destroyDataNode(NodeFactory.NodeType nodeType) {
        synchronized (mNodesMap) {
            BaseDataNode baseDataNode = mNodesMap.get(nodeType);
            if (baseDataNode != null) {
                baseDataNode.destroy();
            }
        }
    }

    private static class SingletonHolder {
        static final NodesProvider INSTANCE = new NodesProvider();
    }
}
