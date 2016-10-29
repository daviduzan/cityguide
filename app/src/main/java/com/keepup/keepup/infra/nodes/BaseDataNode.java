package com.keepup.keepup.infra.nodes;

import com.keepup.keepup.infra.factories.NodeFactory;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by david.uzan on 4/19/2016.
 */
public abstract class BaseDataNode extends BaseNode {

    protected BaseNetworkNode mNetworkNode;
    private NodeFactory.NodeType mNodeType;
    private ArrayList<WeakReference<ClientCallback>> mClientCallbacks;
    private boolean mHasClientCallback;
    private AtomicInteger mClientsCount = new AtomicInteger(0);
    protected BaseDataNode(NodeFactory.NodeType nodeType) {
        mNodeType = nodeType;
        mNetworkNode = onCreateNetworkNode();
        mClientCallbacks = new ArrayList<>();
        mHasClientCallback = false;
    }

    @Override
    public void destroy() {
        super.destroy();
        if (mNetworkNode != null) {
            mNetworkNode.destroy();
            mNetworkNode = null;
        }
    }

    protected abstract BaseNetworkNode getNetworkNode();

    final protected BaseNetworkNode getNetworkNodeFromSuper() {
        return mNetworkNode;
    }

    public NodeFactory.NodeType getNodeType() {
        return mNodeType;
    }

    final protected ArrayList<ClientCallback> getClientCallbacksFromSuper() {
        ArrayList<ClientCallback> callbacks = new ArrayList<>();
        for (WeakReference<ClientCallback> callbackWeakReference : mClientCallbacks) {
            ClientCallback callback = callbackWeakReference.get();
            if (callback != null) {
                callbacks.add(callback);
            }
        }
        return callbacks;
    }

    final public void registerClientCallback(ClientCallback clientCallback) {
        mClientCallbacks.add(new WeakReference<>(clientCallback));
        mClientsCount.incrementAndGet();
        mHasClientCallback = true;
    }

    final public void unregisterClientCallback(ClientCallback callbackToRemove) {
        for (WeakReference<ClientCallback> callbackWeakReference : mClientCallbacks) {
            ClientCallback callback = callbackWeakReference.get();
            if (callback != null && callback.equals(callbackToRemove)) {
                mClientCallbacks.remove(callbackWeakReference);
                mClientsCount.decrementAndGet();
            }
        }
        if (mClientsCount.get() == 0){
            mHasClientCallback = false;
        }
    }

    protected abstract BaseNetworkNode onCreateNetworkNode();
    protected abstract ArrayList<ClientCallback> getClientCallbacks();

    public abstract void registerValueEventListener();

    public abstract void unregisterValueEventListener();

    public interface ClientCallback {
        void onPendingDataFromServer();

        void onConnectivityError();
    }

    public interface NodeBinder {
        NodeFactory.NodeType getNodeType();

        ClientCallback registerClientCallback();

        BaseDataNode getDataNode();
    }
}
