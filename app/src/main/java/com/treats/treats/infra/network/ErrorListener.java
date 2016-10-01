package com.treats.treats.infra.network;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.treats.treats.infra.factories.NodeFactory;
import com.treats.treats.infra.factories.RequestFactory;

/**
 * Created by david.uzan on 4/19/2016.
 */
public class ErrorListener implements Response.ErrorListener{
    private RequestFactory.RequestType mRequestType;
    private ErrorListenerCallback mErrorListenerCallback = NetworkManager.getInstance();
    private NodeFactory.NodeType mNodeType;
    private Object mExtra;

    public ErrorListener(NodeFactory.NodeType nodeType, RequestFactory.RequestType requestType, Object extra){
        this.mNodeType = nodeType;
        this.mRequestType = requestType;
        this.mExtra = extra;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mErrorListenerCallback.onErrorResponse(mNodeType, mRequestType, error, mExtra);
        mErrorListenerCallback = null;
        mExtra = null;
    }

    public interface ErrorListenerCallback {
        void onErrorResponse(NodeFactory.NodeType nodeType, RequestFactory.RequestType requestType, VolleyError error, Object extra);
    }
}

