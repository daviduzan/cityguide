package com.treats.treats.fragments.user_lists.add_to_list_dialog;


import android.app.DialogFragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.treats.treats.R;
import com.treats.treats.infra.factories.NodeFactory;
import com.treats.treats.infra.nodes.NodesProvider;
import com.treats.treats.models.Place;
import com.treats.treats.models.User;
import com.treats.treats.models.UserList;
import com.treats.treats.nodes.PlacesDataNode;
import com.treats.treats.nodes.UserDataNode;

import java.util.ArrayList;

public class AddToListDialogFragment extends DialogFragment implements View.OnClickListener, UserDataNode.UserClientCallback {

    static final String ARGS_KEY_PLACE_NAME = "args_key_place_name";

    private String mPlaceName;
    private Place mPlace;
    private AddToListAdapter mAddToListAdapter;
    private EditText etNewList;
    private Button btnNewList;
    private UserDataNode mUserDataNode;

    public AddToListDialogFragment() {
    }

    public static AddToListDialogFragment newInstance(String placeName) {
        Bundle args = new Bundle();
        AddToListDialogFragment addToListDialogFragment = new AddToListDialogFragment();
        args.putString(ARGS_KEY_PLACE_NAME, placeName);
        addToListDialogFragment.setArguments(args);
        addToListDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AddToListDialogStyle);
        return addToListDialogFragment;

    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setGravity(Gravity.BOTTOM);
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.dialog_height));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPlaceName = getArguments().getString(ARGS_KEY_PLACE_NAME);
        PlacesDataNode placesDataNode = (PlacesDataNode) NodesProvider.getInstance().getDataNode(NodeFactory.NodeType.PLACES);
        mPlace = placesDataNode.getPlace(mPlaceName);

        mUserDataNode = (UserDataNode) NodesProvider.getInstance().getDataNode(NodeFactory.NodeType.USER);
        mUserDataNode.registerClientCallback(this);

        mAddToListAdapter = new AddToListAdapter(mUserDataNode.getUser().getUserLists(), mPlaceName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_to_list_dialog, container, false);
        ((TextView) (view.findViewById(R.id.tv_place_name))).setText(mPlace.mTitle);

        ListView listView = (ListView) view.findViewById(R.id.lv_add_to_list);

        listView.setAdapter(mAddToListAdapter);

        view.findViewById(R.id.btn_add_lists).setOnClickListener(this);
        btnNewList = (Button) view.findViewById(R.id.btn_new_list);
        btnNewList.setOnClickListener(this);
        btnNewList.setTag(true);
        etNewList = (EditText) view.findViewById(R.id.et_new_list);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUserDataNode.unregisterClientCallback(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_lists:
                ArrayList<UserList> userLists = mAddToListAdapter.getUserLists();
                for (UserList userList : userLists) {
                    if (userList.isChecked()) {
                        mUserDataNode.addPlaceToUserList(userList.getName(), mPlaceName);
                    } else {
                        mUserDataNode.removePlaceFromUserList(userList.getName(), mPlaceName);
                    }

                }
                dismiss();

                break;

            case R.id.btn_new_list:

                if ((boolean) btnNewList.getTag()) {
                    etNewList.setVisibility(View.VISIBLE);
                    btnNewList.setText("CREATE");
                    btnNewList.setTag(false);

                } else {
                    String text = etNewList.getText().toString();
                    if (!TextUtils.isEmpty(text)) {
                        mUserDataNode.createUserList(text);
                        btnNewList.setText("NEW");
                        btnNewList.setTag(true);
                        etNewList.setVisibility(View.GONE);
                    }
                    break;
                }
        }
    }

    @Override
    public void onPendingDataFromServer() {

    }

    @Override
    public void onConnectivityError() {

    }

    @Override
    public void onUserDataSuccess(User user) {
        mAddToListAdapter.setUserLists(user.getUserLists());
    }
}
