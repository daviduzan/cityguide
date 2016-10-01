package com.treats.treats.nodes;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.treats.treats.App;
import com.treats.treats.infra.factories.NodeFactory;
import com.treats.treats.infra.nodes.BaseDataNode;
import com.treats.treats.infra.nodes.BaseNetworkNode;
import com.treats.treats.models.ServerModels;

import java.util.ArrayList;

/**
 * Created by david.uzan on 9/3/2016.
 */
public class TrendingDataNode extends BaseDataNode implements ValueEventListener {

    //    private HashMap<String, ArrayList<TrendingItem>> mTrendingItemsData;
    private ArrayList<ServerModels.TrendingGroupSM> mTrendingItemsData;
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
    public void onDataChange(DataSnapshot dataSnapshot) {
        ArrayList<ServerModels.TrendingGroupSM> groupSMArrayList = new ArrayList<>();
        for (DataSnapshot dateData : dataSnapshot.getChildren()) {
            ServerModels.TrendingGroupSM groupSM = dateData.getValue(ServerModels.TrendingGroupSM.class);
            groupSMArrayList.add(0, groupSM);
        }
        mTrendingItemsData = groupSMArrayList;

        for (ClientCallback callback : getClientCallbacks()) {
            ((TrendingClientCallback) callback).onDataSuccess();
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        App.log("Failed to read value." + databaseError.toException());
        if (mTrendingItemsData == null || mTrendingItemsData.isEmpty()) {
            for (ClientCallback callback : getClientCallbacks()) {
                callback.onConnectivityError();
            }
        }
    }

    public ArrayList<ServerModels.TrendingGroupSM> getGroups() {
        if (mTrendingItemsData == null) return new ArrayList<>();
        return mTrendingItemsData;
    }

    public ArrayList<ServerModels.TrendingItemSM> getItemsAsList() {
        if (mTrendingItemsData == null) return new ArrayList<>();
        ArrayList<ServerModels.TrendingItemSM> itemSMs = new ArrayList<>();
        for (ServerModels.TrendingGroupSM groupSM : mTrendingItemsData) {
            itemSMs.addAll(groupSM.getMembers());
        }
        return itemSMs;
    }

    public interface TrendingClientCallback extends ClientCallback {
        void onDataSuccess();
    }
}
