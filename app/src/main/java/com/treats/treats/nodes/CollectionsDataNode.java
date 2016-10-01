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
import com.treats.treats.models.Collection;
import com.treats.treats.models.ServerModels;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by david.uzan on 9/3/2016.
 */
public class CollectionsDataNode extends BaseDataNode implements ValueEventListener {

    private HashMap<String, Collection> mCollections;
    private DatabaseReference mDatabaseReference;

    public CollectionsDataNode(NodeFactory.NodeType nodeType) {
        super(nodeType);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabaseReference = database.getReference("collections");
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
        HashMap<String, Collection> collections = new HashMap<>();
        for (DataSnapshot child : dataSnapshot.getChildren()) {
            ServerModels.CollectionSM collectionSM = child.getValue(ServerModels.CollectionSM.class);
            collections.put(child.getKey(), Collection.fromSM(collectionSM));
        }
        mCollections = collections;
        for (ClientCallback callback : getClientCallbacks()) {
            ((CollectionsClientCallback) callback).onCollectionsDataSuccess();
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        App.log("Failed to read value." + databaseError.toException());
        if (mCollections == null || mCollections.isEmpty()) {
            for (ClientCallback callback : getClientCallbacks()) {
                callback.onConnectivityError();
            }
        }
    }

    public Collection getCollection(String collectionName) {
        if (mCollections == null) return Collection.fromSM(new ServerModels.CollectionSM());
        Collection collection = mCollections.get(collectionName);
        if (collection == null) {
            collection = new Collection();
            collection.setMembers(new ArrayList<String>());
            collection.setTypes(new ArrayList<Integer>());
            return collection;
        }
        return collection;
    }

    public interface CollectionsClientCallback extends ClientCallback {
        void onCollectionsDataSuccess();
    }
}
