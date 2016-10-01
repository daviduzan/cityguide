package com.treats.treats.infra.factories;

import com.treats.treats.nodes.CategoriesDataNode;
import com.treats.treats.nodes.CollectionsDataNode;
import com.treats.treats.infra.exceptions.UndefinedCaseException;
import com.treats.treats.infra.nodes.BaseNode;
import com.treats.treats.nodes.PlacesDataNode;
import com.treats.treats.nodes.TrendingDataNode;

/**
 * Created by david.uzan on 4/19/2016.
 */
public class NodeFactory {

        public enum NodeType {
            CATEGORIES,
            COLLECTIONS,
            PLACES,
            TRENDING
        }

        public static BaseNode createNewNode(NodeType nodeType) {
            switch (nodeType) {
                case CATEGORIES:
                    return new CategoriesDataNode(nodeType);
                case COLLECTIONS:
                    return new CollectionsDataNode(nodeType);
                case PLACES:
                    return new PlacesDataNode(nodeType);
                case TRENDING:
                    return new TrendingDataNode(nodeType);
            }
                    throw new UndefinedCaseException(nodeType.name(), NodeType.class);
        }
}
