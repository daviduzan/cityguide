package com.treats.treats.nodes;

import com.android.volley.VolleyError;
import com.treats.treats.infra.factories.NodeFactory;
import com.treats.treats.infra.factories.RequestFactory;
import com.treats.treats.infra.nodes.BaseDataNode;
import com.treats.treats.infra.nodes.BaseNetworkNode;

/**
 * Created by david.uzan on 9/3/2016.
 */
public class CategoriesNetworkNode extends BaseNetworkNode {

    protected CategoriesNetworkNode(BaseDataNode dataNode) {
        super(dataNode);
    }

    @Override
    protected CategoriesDataNode getDataNode() {
        return (CategoriesDataNode) getDataNodeFromSuper();
    }

    @Override
    public <T> void onResponseSuccess(NodeFactory.NodeType nodeType, RequestFactory.RequestType requestType, T response, Object extra) {

    }

    @Override
    public void onResponseFailure(NodeFactory.NodeType nodeType, RequestFactory.RequestType requestType, VolleyError error, Object extra) {

    }

    @Override
    public void onConnectivityError(NodeFactory.NodeType nodeType, RequestFactory.RequestType requestType, Object extra) {

    }
}
