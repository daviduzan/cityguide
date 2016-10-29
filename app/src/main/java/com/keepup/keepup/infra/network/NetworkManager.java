package com.keepup.keepup.infra.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.keepup.keepup.App;
import com.keepup.keepup.infra.factories.NodeFactory;
import com.keepup.keepup.infra.factories.RequestFactory;
import com.keepup.keepup.infra.nodes.BaseNetworkNode;

import java.util.HashMap;

/**
 * Created by david.uzan on 4/19/2016.
 */
public class NetworkManager<T> implements
        ErrorListener.ErrorListenerCallback,
        ResponseListener.ResponseListenerCallback{

    public final static String BROADCAST_ACTION_UNAUTHORIZED_HTTP_ERROR = "broadcast_action_unauthorized_http_error";
    private final static int RETRY_POLICY_CUSTOM_TIMEOUT_MS = 10000;
    private final static int RETRY_POLICY_CUSTOM_MAX_RETRIES = 0;
    private RequestQueue mRequestQueue;
    private HashMap<NodeFactory.NodeType, NetworkClientCallbacks> mNetworkClientCallbacksMap = new HashMap<>();

    private NetworkManager() {
        this.mRequestQueue = Volley.newRequestQueue(App.getAppContext());
    }

    public static NetworkManager getInstance() {
        return SingletonHolder.sInstance;
    }

    public static boolean isConnected() {
        return isWifiConnected() || isMobileConnected();
    }

    public static boolean isWifiConnected() {
        final ConnectivityManager connectivityManager = (ConnectivityManager) App.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            final NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (wifi != null) {
                if ((wifi.isAvailable()) && (wifi.isConnected())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isMobileConnected() {
        final ConnectivityManager connectivityManager = (ConnectivityManager) App.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            final NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mobile != null) {
                if ((mobile.isAvailable()) && (mobile.isConnected())) {
                    return true;
                }
            }
        }
        return false;
    }

    private static RetryPolicy getAppRetryPolicy() {
        return new DefaultRetryPolicy(RETRY_POLICY_CUSTOM_TIMEOUT_MS, RETRY_POLICY_CUSTOM_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    }

    public void performRequest(TreatsRequest<T> request) {
        request.setCustomRetryPolicy(getAppRetryPolicy());
        mRequestQueue.add(request);
    }

    public synchronized void registerNetworkNode(BaseNetworkNode networkNode) {
        mNetworkClientCallbacksMap.put(networkNode.getNodeType(), networkNode);
    }

    public synchronized void unregisterNetworkNode(NodeFactory.NodeType nodeType) {
        mNetworkClientCallbacksMap.remove(nodeType);
    }

    @Override
    public synchronized void onResponse(NodeFactory.NodeType nodeType, RequestFactory.RequestType requestType, Object response, Object extra) {
        NetworkClientCallbacks listener = mNetworkClientCallbacksMap.get(nodeType);
        if (listener != null) {
            listener.onResponseSuccess(nodeType, requestType, response, extra);
        }
    }

    @Override
    public synchronized void onErrorResponse(NodeFactory.NodeType nodeType, RequestFactory.RequestType requestType, VolleyError error, Object extra) {
        NetworkClientCallbacks listener = mNetworkClientCallbacksMap.get(nodeType);
        if (listener != null) {
            listener.onResponseFailure(nodeType, requestType, error, extra);
        }
    }

    public interface NetworkClientCallbacks {
        <T> void onResponseSuccess(NodeFactory.NodeType nodeType, RequestFactory.RequestType requestType, T response, Object extra);

        void onResponseFailure(NodeFactory.NodeType nodeType, RequestFactory.RequestType requestType, VolleyError error, Object extra);

        void onConnectivityError(NodeFactory.NodeType nodeType, RequestFactory.RequestType requestType, Object extra);
    }

    private static class SingletonHolder {
        static final NetworkManager sInstance = new NetworkManager();
    }


}
