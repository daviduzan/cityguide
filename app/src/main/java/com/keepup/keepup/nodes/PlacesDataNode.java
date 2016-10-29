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
import com.keepup.keepup.models.Place;
import com.keepup.keepup.models.ServerModels;

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
        App.logError("Failed to read value: " + databaseError.getMessage());
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
