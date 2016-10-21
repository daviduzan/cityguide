package com.treats.treats.nodes;

import android.text.TextUtils;

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
import com.treats.treats.tools.SharedPrefsHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by david.uzan on 9/3/2016.
 */
public class UserDataNode extends BaseDataNode implements ValueEventListener {

    private DatabaseReference mDatabaseReference;
    private User mUser;

    public UserDataNode(NodeFactory.NodeType nodeType) {
        super(nodeType);
        final
        String userId = SharedPrefsHelper.getInstance(App.getAppContext()).getUserId();
        if (TextUtils.isEmpty(userId)) {
            createNewUser();
        } else {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            mDatabaseReference = database.getReference("users/" + userId);
            mDatabaseReference.addValueEventListener(this);
        }
    }

    private void createNewUser() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String userId;DatabaseReference mainRef = database.getReference();
        DatabaseReference usersRef = mainRef.child("users");
        DatabaseReference newUserRef = usersRef.push();
        ServerModels.UserSM userSM = new ServerModels.UserSM();
        userSM.lists = new HashMap<>();
        userId = newUserRef.getKey();
        SharedPrefsHelper.getInstance(App.getAppContext()).setUserId(userId);
        final String finalUserId = userId;
        newUserRef.setValue(userSM, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    mDatabaseReference = database.getReference("users/" + finalUserId);
                    mDatabaseReference.addValueEventListener(UserDataNode.this);
                }
            }
        });
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
        if (!dataSnapshot.exists()) {
            createNewUser();
        } else {
            ServerModels.UserSM userSM = dataSnapshot.getValue(ServerModels.UserSM.class);
            mUser = User.fromSM(userSM);
            for (ClientCallback callback : getClientCallbacks()) {
                ((UserDataNode.UserClientCallback) callback).onUserDataSuccess(mUser);
            }
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

    public void createUserList(String name) {
        Map<String, Object> userCreateList = new HashMap<>();
        userCreateList.put("name", name);
        mDatabaseReference.child("lists").child(name).updateChildren(userCreateList);

    }

    public void addPlaceToUserList(String listName, String place) {
        Map<String, Object> updateUserListMap = new HashMap<>();
        updateUserListMap.put(place, true);

        mDatabaseReference.child("lists").child(listName).child("places").updateChildren(updateUserListMap);
    }

    public interface UserClientCallback extends ClientCallback {
        void onUserDataSuccess(User user);
    }
}
