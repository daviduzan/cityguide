package com.keepup.keepup.nodes;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.keepup.keepup.App;
import com.keepup.keepup.infra.factories.NodeFactory;
import com.keepup.keepup.infra.nodes.BaseDataNode;
import com.keepup.keepup.infra.nodes.BaseNetworkNode;
import com.keepup.keepup.models.ServerModels;
import com.keepup.keepup.models.TrendingGroup;
import com.keepup.keepup.models.TrendingItem;

import java.util.ArrayList;

/**
 * Created by david.uzan on 9/3/2016.
 */
public class TrendingDataNode extends BaseDataNode implements ValueEventListener {

    private ArrayList<TrendingGroup> mTrendingItemsData;
    private DatabaseReference mDatabaseReference;

    public TrendingDataNode(NodeFactory.NodeType nodeType) {
        super(nodeType);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabaseReference = database.getReference("trending");
        mDatabaseReference.orderByKey().addValueEventListener(this);
    }

    @Override
    protected BaseNetworkNode getNetworkNode() {
        return null;
    }

    @Override
    protected BaseNetworkNode onCreateNetworkNode() {
        return null;
    }

    @Override
    protected ArrayList<ClientCallback> getClientCallbacks() {
        return getClientCallbacksFromSuper();
    }

    @Override
    public void registerValueEventListener() {
        if (mDatabaseReference != null) {
            mDatabaseReference.addValueEventListener(this);
        }
    }

    @Override
    public void unregisterValueEventListener() {
        if (mDatabaseReference != null) {
            mDatabaseReference.removeEventListener(this);
        }
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        App.log("TDN onDataChange");
        ArrayList<TrendingGroup> allTrendingGroups = new ArrayList<>();
        for (DataSnapshot dateData : dataSnapshot.getChildren()) {
            ServerModels.TrendingGroupSM groupSM = dateData.getValue(ServerModels.TrendingGroupSM.class);
            allTrendingGroups.add(0, TrendingGroup.fromSM(groupSM));
        }
        mTrendingItemsData = allTrendingGroups;

        for (ClientCallback callback : getClientCallbacks()) {
            ((TrendingClientCallback) callback).onDataSuccess();
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        App.logError("Failed to read value: " + databaseError.getMessage());
        if (mTrendingItemsData == null || mTrendingItemsData.isEmpty()) {
            for (ClientCallback callback : getClientCallbacks()) {
                callback.onConnectivityError();
            }
        }
    }

    public ArrayList<TrendingGroup> getGroups() {
        if (mTrendingItemsData == null) return new ArrayList<>();
        return mTrendingItemsData;
    }

    public ArrayList<TrendingItem> getItemsAsList() {
        if (mTrendingItemsData == null) return new ArrayList<>();
        ArrayList<TrendingItem> trendingItems = new ArrayList<>();
        for (TrendingGroup trendingGroup : mTrendingItemsData) {
            trendingItems.addAll(trendingGroup.getMembers());
        }
        return trendingItems;
    }

    public interface TrendingClientCallback extends ClientCallback {
        void onDataSuccess();
    }
}
