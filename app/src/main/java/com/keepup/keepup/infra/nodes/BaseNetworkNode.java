package com.keepup.keepup.infra.nodes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.keepup.keepup.infra.factories.NodeFactory;
import com.keepup.keepup.infra.network.NetworkManager;

/**
 * Created by david.uzan on 4/19/2016.
 */
public abstract class BaseNetworkNode extends BaseNode implements NetworkManager.NetworkClientCallbacks {
    protected static Gson mGson = new GsonBuilder().setPrettyPrinting().create();
    private BaseDataNode mDataNode;

    protected BaseNetworkNode(BaseDataNode dataNode) {
        this.mDataNode = dataNode;
        NetworkManager.getInstance().registerNetworkNode(this);
    }

    final protected BaseDataNode getDataNodeFromSuper() {
        return mDataNode;
    }

    final public boolean isConnected() {
        return NetworkManager.isConnected();
    }

    final public boolean isWifiConnected() {
        return NetworkManager.isWifiConnected();
    }

    final public boolean isMobileConnected() {
        return NetworkManager.isMobileConnected();
    }

    @Override
    public NodeFactory.NodeType getNodeType() {
        return mDataNode.getNodeType();
    }

    protected abstract BaseDataNode getDataNode();

    @Override
    public void destroy() {
        super.destroy();
        NetworkManager.getInstance().unregisterNetworkNode(getNodeType());
        mDataNode = null;
    }

    public interface NetworkCallbacks {
        void onConnectivityError();
    }

}

