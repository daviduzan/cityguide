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
import com.treats.treats.models.Place;
import com.treats.treats.models.ServerModels;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by david.uzan on 9/3/2016.
 */
public class PlacesDataNode extends BaseDataNode implements ValueEventListener {

    private HashMap<String, Place> mPlaces;
    private DatabaseReference mDatabaseReference;

    public PlacesDataNode(NodeFactory.NodeType nodeType) {
        super(nodeType);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabaseReference = database.getReference("places");
        mDatabaseReference.addValueEventListener(this);
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
        HashMap<String, Place> placeHashMap = new HashMap<>();
        for (DataSnapshot child : dataSnapshot.getChildren()) {
            ServerModels.PlaceSM placeSM = child.getValue(ServerModels.PlaceSM.class);
            placeSM.name = child.getKey();
            placeHashMap.put(child.getKey(), Place.fromSM(placeSM));
        }
        mPlaces = placeHashMap;
        for (ClientCallback callback : getClientCallbacks()) {
            ((PlacesClientCallback) callback).onPlacesDataSuccess();
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        App.log("Failed to read value." + databaseError.toException());
        if (mPlaces == null || mPlaces.isEmpty()) {
            for (ClientCallback callback : getClientCallbacks()) {
                callback.onConnectivityError();
            }
        }
    }

    public Place getPlace(String name) {
        if (mPlaces == null) return null;
        return mPlaces.get(name);
    }

    public interface PlacesClientCallback extends ClientCallback {
        void onPlacesDataSuccess();
    }
}
