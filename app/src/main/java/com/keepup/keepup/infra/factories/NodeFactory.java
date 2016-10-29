package com.keepup.keepup.infra.factories;

import com.keepup.keepup.nodes.CategoriesDataNode;
import com.keepup.keepup.nodes.CollectionsDataNode;
import com.keepup.keepup.infra.exceptions.UndefinedCaseException;
import com.keepup.keepup.infra.nodes.BaseNode;
import com.keepup.keepup.nodes.PlacesDataNode;
import com.keepup.keepup.nodes.TrendingDataNode;
import com.keepup.keepup.nodes.UserDataNode;

/**
 * Created by david.uzan on 4/19/2016.
 */
public class NodeFactory {

        public enum NodeType {
            CATEGORIES,
            COLLECTIONS,
            PLACES,
            TRENDING,
            USER
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
                case USER:
                    return new UserDataNode(nodeType);
            }
                    throw new UndefinedCaseException(nodeType.name(), NodeType.class);
        }
}
