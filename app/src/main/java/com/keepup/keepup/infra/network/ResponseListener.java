package com.keepup.keepup.infra.network;


import com.android.volley.Response;
import com.keepup.keepup.infra.factories.NodeFactory;
import com.keepup.keepup.infra.factories.RequestFactory;

/**
 * Created by david.uzan on 4/19/2016.
 */
public class ResponseListener implements Response.Listener{
    private ResponseListenerCallback mResponseListenerCallback = NetworkManager.getInstance();
    private NodeFactory.NodeType mNodeType;
    private RequestFactory.RequestType mRequestType;
    private Object mExtra;

    public ResponseListener(NodeFactory.NodeType nodeType, RequestFactory.RequestType requestType, Object extra){
        this.mNodeType = nodeType;
        this.mRequestType = requestType;
        this.mExtra = extra;
    }

    @Override
    public void onResponse(Object response) {
        mResponseListenerCallback.onResponse(mNodeType, mRequestType, response, mExtra);
        mResponseListenerCallback = null;
        mExtra = null;
    }

    public interface ResponseListenerCallback {
        public void onResponse(NodeFactory.NodeType nodeType, RequestFactory.RequestType requestType, Object response, Object extra);
    }
}
