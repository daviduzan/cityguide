package com.treats.treats.nodes;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.treats.treats.App;
import com.treats.treats.infra.factories.NodeFactory;
import com.treats.treats.infra.nodes.BaseDataNode;
import com.treats.treats.models.ServerModels;

import java.util.ArrayList;

/**
 * Created by david.uzan on 9/3/2016.
 */
public class CategoriesDataNode extends BaseDataNode implements ValueEventListener {

    private ArrayList<ServerModels.CategorySM> mCategories;
    private DatabaseReference mDatabaseReference;

    public CategoriesDataNode(NodeFactory.NodeType nodeType) {
        super(nodeType);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabaseReference = database.getReference("categories");
        mDatabaseReference.addValueEventListener(this);
    }

    @Override
    protected CategoriesNetworkNode getNetworkNode() {
        return (CategoriesNetworkNode) getNetworkNodeFromSuper();
    }

    @Override
    protected CategoriesNetworkNode onCreateNetworkNode() {
        return new CategoriesNetworkNode(this);
    }

    @Override
    protected ArrayList<ClientCallback> getClientCallbacks() {
        return getClientCallbacksFromSuper();
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        App.log("onDataChange");
        ArrayList<ServerModels.CategorySM> categories = new ArrayList<ServerModels.CategorySM>();
        for (DataSnapshot child : dataSnapshot.getChildren()) {
            ServerModels.CategorySM categorySM = child.getValue(ServerModels.CategorySM.class);
            categories.add(categorySM);
        }

        mCategories = categories;
        for (ClientCallback callback : getClientCallbacks()) {
            ((CategoriesClientCallback) callback).onCatgoriesDataSuccess(mCategories);
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        App.log("Failed to read value." + databaseError.toException());
        if (mCategories == null || mCategories.isEmpty()) {
            for (ClientCallback callback : getClientCallbacks()) {
                callback.onConnectivityError();
            }
        }
    }

    public ArrayList<ServerModels.CategorySM> getCategories() {
        if (mCategories == null) {
            return new ArrayList<>();
        }
        return mCategories;
    }

    public interface CategoriesClientCallback extends ClientCallback {
        void onCatgoriesDataSuccess(ArrayList<ServerModels.CategorySM> categories);
    }
}
