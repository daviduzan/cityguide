package com.treats.treats.infra.nodes;


import com.treats.treats.infra.factories.NodeFactory;

/**
 * Created by david.uzan on 4/19/2016.
 */
public abstract class BaseNode {
    private  boolean mAlive = true;

    public boolean isAlive() {
        return mAlive;
    }

    public void destroy() {
        mAlive = false;
    }

    public abstract NodeFactory.NodeType getNodeType();

}
