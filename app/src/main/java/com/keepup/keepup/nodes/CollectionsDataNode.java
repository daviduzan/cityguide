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
import com.keepup.keepup.models.CollectionCategory;
import com.keepup.keepup.models.ServerModels;
import com.keepup.keepup.models.SimpleCollection;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by david.uzan on 9/3/2016.
 */
public class CollectionsDataNode extends BaseDataNode implements ValueEventListener {

    private HashMap<String, SimpleCollection> mSimpleCollections;
    private HashMap<String, ArrayList<CollectionCategory>> mCategorizedCollections;

    private DatabaseReference mSimpleCollectionsDatabaseReference;
    private DatabaseReference mCategorizedCollectionsDatabaseReference;

    public CollectionsDataNode(NodeFactory.NodeType nodeType) {
        super(nodeType);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mSimpleCollectionsDatabaseReference = database.getReference("collections/simple-collections");
        mSimpleCollectionsDatabaseReference.addValueEventListener(this);

        mCategorizedCollectionsDatabaseReference = database.getReference("collections/categorized-collections");
        mCategorizedCollectionsDatabaseReference.addValueEventListener(this);
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
        switch (dataSnapshot.getKey()) {
            case "simple-collections":
                HashMap<String, SimpleCollection> simpleCollections = new HashMap<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    ServerModels.SimpleCollectionSM simpleCollectionSM = child.getValue(ServerModels.SimpleCollectionSM.class);
                    simpleCollections.put(child.getKey(), SimpleCollection.fromSM(simpleCollectionSM));
                }
                mSimpleCollections = simpleCollections;
                for (ClientCallback callback : getClientCallbacks()) {
                    ((CollectionsClientCallback) callback).onSimpleCollectionsDataSuccess();
                }
                break;

            case "categorized-collections":
                HashMap<String, ArrayList<CollectionCategory>> categorizedCollections = new HashMap<>();
                for (DataSnapshot categorizedChild : dataSnapshot.getChildren()) {
                    ArrayList<CollectionCategory> categories = new ArrayList<>();
                    for (DataSnapshot categoryData : categorizedChild.getChildren()) {
                        ServerModels.CollectionCategorySM collectionCategorySM = categoryData.getValue(ServerModels.CollectionCategorySM.class);
                        categories.add(CollectionCategory.fromSM(collectionCategorySM));
                    }
                    categorizedCollections.put(categorizedChild.getKey(), categories);
                }
                mCategorizedCollections = categorizedCollections;
                for (ClientCallback callback : getClientCallbacks()) {
                    ((CollectionsClientCallback) callback).onCategorizedCollectionDataSuccess();
                }

                break;
        }


    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        App.logError("Failed to read value: " + databaseError.getMessage());
//        if (mCollections == null || mCollections.isEmpty()) {
//            for (ClientCallback callback : getClientCallbacks()) {
//                callback.onConnectivityError();
//            }
//        }
    }

    public SimpleCollection getSimpleCollection(String collectionName) {
        if (mSimpleCollections == null) return null;
        SimpleCollection collection = mSimpleCollections.get(collectionName);
        return collection;
    }

    public ArrayList<CollectionCategory> getCategorizedCollection(String collectionName) {
        if (mCategorizedCollections == null) return null;
        return mCategorizedCollections.get(collectionName);
    }

    public interface CollectionsClientCallback extends ClientCallback {
        void onSimpleCollectionsDataSuccess();

        void onCategorizedCollectionDataSuccess();
    }
}
