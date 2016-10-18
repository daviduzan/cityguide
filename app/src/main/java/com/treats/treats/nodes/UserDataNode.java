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
import com.treats.treats.models.User;
import com.treats.treats.models.UserList;

import java.util.ArrayList;

/**
 * Created by david.uzan on 9/3/2016.
 */
public class UserDataNode extends BaseDataNode implements ValueEventListener {

    private DatabaseReference mDatabaseReference;
    private User mUser;

    public UserDataNode(NodeFactory.NodeType nodeType) {
        super(nodeType);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabaseReference = database.getReference("users/moshe"); //TODO
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
        ServerModels.UserSM userSM = dataSnapshot.getValue(ServerModels.UserSM.class);
        mUser = User.fromSM(userSM);
        for (ClientCallback callback : getClientCallbacks()) {
            ((UserDataNode.UserClientCallback) callback).onUserDataSuccess(mUser);
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        App.log("Failed to read value." + databaseError.toException());

    }

    public User getUser() {
        if (mUser == null) {
            return new User();
        }
        return mUser;
    }

    public UserList getUserList(String name) {
        User user = getUser();
        for (UserList userList : user.getUserLists()) {
            if (userList.getName() == name) {
                return userList;
            }
        }
        return null;
    }

    public interface UserClientCallback extends ClientCallback {
        void onUserDataSuccess(User user);
    }
}
