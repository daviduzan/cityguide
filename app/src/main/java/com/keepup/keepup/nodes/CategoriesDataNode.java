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
import com.keepup.keepup.models.Category;
import com.keepup.keepup.models.ServerModels;

import java.util.ArrayList;

/**
 * Created by david.uzan on 9/3/2016.
 */
public class CategoriesDataNode extends BaseDataNode implements ValueEventListener {

    private ArrayList<Category> mCategories;
    private DatabaseReference mDatabaseReference;

    public CategoriesDataNode(NodeFactory.NodeType nodeType) {
        super(nodeType);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabaseReference = database.getReference("categories");
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
        App.log("onDataChange");
        ArrayList<Category> categories = new ArrayList<>();
        for (DataSnapshot child : dataSnapshot.getChildren()) {
            ServerModels.CategorySM categorySM = child.getValue(ServerModels.CategorySM.class);
            categories.add(Category.fromSM(categorySM));
        }

        mCategories = categories;
        for (ClientCallback callback : getClientCallbacks()) {
            ((CategoriesClientCallback) callback).onCatgoriesDataSuccess(mCategories);
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        App.logError("Failed to read value: " + databaseError.getMessage());
        if (mCategories == null || mCategories.isEmpty()) {
            for (ClientCallback callback : getClientCallbacks()) {
                callback.onConnectivityError();
            }
        }
    }

    public ArrayList<Category> getCategories() {
        if (mCategories == null) {
            return new ArrayList<>();
        }
        return mCategories;
    }

    public interface CategoriesClientCallback extends ClientCallback {
        void onCatgoriesDataSuccess(ArrayList<Category> categories);
    }
}
