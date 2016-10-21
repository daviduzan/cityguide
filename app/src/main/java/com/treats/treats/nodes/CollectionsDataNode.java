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
import com.treats.treats.models.SimpleCollection;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by david.uzan on 9/3/2016.
 */
public class CollectionsDataNode extends BaseDataNode implements ValueEventListener {

    private HashMap<String, SimpleCollection> mSimpleCollections;
    private HashMap<String, ArrayList<ServerModels.CollectionCategorySM>> mCategorizedCollections;

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
                HashMap<String, ArrayList<ServerModels.CollectionCategorySM>> categorizedCollections = new HashMap<>();
                for (DataSnapshot categorizedChild : dataSnapshot.getChildren()) {
                    ArrayList<ServerModels.CollectionCategorySM> categories = new ArrayList<>();
                    for (DataSnapshot categoryData : categorizedChild.getChildren()) {
                        ServerModels.CollectionCategorySM collectionCategorySM = categoryData.getValue(ServerModels.CollectionCategorySM.class);
                        categories.add(collectionCategorySM);
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
        App.log("Failed to read value." + databaseError.toException());
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

    public ArrayList<ServerModels.CollectionCategorySM> getCategorizedCollection(String collectionName) {
        if (mCategorizedCollections == null) return null;
        return mCategorizedCollections.get(collectionName);
    }

    public interface CollectionsClientCallback extends ClientCallback {
        void onSimpleCollectionsDataSuccess();

        void onCategorizedCollectionDataSuccess();
    }
}
