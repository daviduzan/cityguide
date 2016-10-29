package com.keepup.keepup.infra.network;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.keepup.keepup.App;
import com.keepup.keepup.infra.factories.NodeFactory;
import com.keepup.keepup.infra.factories.RequestFactory;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Locale;
import java.util.Map;

/**
 * Created by david.uzan on 4/19/2016.
 */
public class TreatsRequest<T> extends Request<T> {

    public static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";
    private static final String CONTENT_CHARSET_UTF_8 = "utf-8";

    private Response.Listener<T> mResponseListener;
    private Class<T> mResponseClass;
    private Type mGenericType;
    private Map<String, String> mHeaders;
    private String mBody;
    private String mContentType;
    private String mCharset = CONTENT_CHARSET_UTF_8;
    private ResponseType mResponseType = ResponseType.STRING;

    public TreatsRequest(int method, String url, NodeFactory.NodeType nodeType, RequestFactory.RequestType requestType) {
        this(method, url, nodeType, requestType, null);
    }

    @SuppressWarnings("unchecked")
    public TreatsRequest(int method, String url, NodeFactory.NodeType nodeType, RequestFactory.RequestType requestType, Object extra) {
        super(method, url, new ErrorListener(nodeType, requestType, extra));
        App.log("Request " + url);
        mResponseListener = new ResponseListener(nodeType, requestType, extra);
    }

    public TreatsRequest<T> setHeaders(Map<String, String> headers) {
        mHeaders = headers;
        return this;
    }

    public TreatsRequest<T> setBody(String body) {
        mBody = body;
        return this;
    }

    public String getStringBody() {
        return mBody;
    }

    public TreatsRequest<T> setResponseType(ResponseType responseType) {
        this.mResponseType = responseType;
        return this;
    }

    public TreatsRequest<T> setContentType(String contentType) {
        this.mContentType = contentType;
        return this;
    }

    public TreatsRequest<T> setCharset(String charset) {
        this.mCharset = charset;
        return this;
    }

    public TreatsRequest<T> setGenericType(Type genericType) {
        this.mGenericType = genericType;
        return this;
    }

    public TreatsRequest<T> setResponseClass(Class<T> responseClass) {
        this.mResponseClass = responseClass;
        return this;
    }

    public TreatsRequest<T> shouldCache(boolean shouldCache) {
        this.setShouldCache(shouldCache);
        return this;
    }

    public TreatsRequest<T> setCustomRetryPolicy(RetryPolicy retryPolicy) {
        if (retryPolicy != null) {
            super.setRetryPolicy(retryPolicy);
        }
        return this;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders == null ? super.getHeaders() : mHeaders;
    }


    @Override
    public byte[] getBody() throws AuthFailureError {
        if (mBody == null) return super.getBody();
        try {
            if (TextUtils.isEmpty(mCharset)) {
                return mBody.getBytes();
            } else {
                return mBody.getBytes(mCharset);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return super.getBody();
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mResponseListener.onResponse(response);
    }

    @Override
    public String getBodyContentType() {
        if (!TextUtils.isEmpty(mContentType) && !TextUtils.isEmpty(mCharset)) {
            return String.format(Locale.US, "%s; charset=%s", mContentType, mCharset);
        } else {
            return super.getBodyContentType();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String responseString = "";
        try {

            responseString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Object parsedResponse;
            switch (mResponseType) {
                case OBJECT:
                    if (mResponseClass == null) {
                        throw new IllegalStateException("Response Class not set");
                    }
                    parsedResponse = new Gson().fromJson(responseString, mResponseClass);
                    break;
                case GENERIC_TYPE:
                    if (mGenericType == null) {
                        throw new IllegalStateException("Generic Type not set");
                    }
                    parsedResponse = new Gson().fromJson(responseString, mGenericType);
                    break;
                case DOUBLE:
                    parsedResponse = Double.parseDouble(responseString);
                    break;
                case INTEGER:
                    parsedResponse = Integer.parseInt(responseString);
                    break;
                default:
                    parsedResponse = responseString;
            }
            return (Response<T>) Response.success(parsedResponse, HttpHeaderParser.parseCacheHeaders(response));

        } catch (final Exception e) {
            return Response.error(new ParseError(e));
        }
    }

    public enum ResponseType {
        OBJECT,
        GENERIC_TYPE,
        DOUBLE,
        INTEGER,
        STRING
    }
}