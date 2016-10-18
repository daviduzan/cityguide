package com.treats.treats.fragments.user_lists.user_list;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.treats.treats.R;
import com.treats.treats.infra.factories.NodeFactory;
import com.treats.treats.infra.fragments.BaseFragment;
import com.treats.treats.infra.nodes.NodesProvider;
import com.treats.treats.models.User;
import com.treats.treats.nodes.UserDataNode;


public class UserListFragment extends BaseFragment implements UserDataNode.UserClientCallback, UserListAdapter.OnItemClickListener {

    public static final String ARGS_KEY_USER_LIST_NAME = "args_key_user_list_name";

    private UserDataNode mUserDataNode;
    private UserListAdapter mAdapter;
    private String mCollectionName;

    public static UserListFragment newInstance(String personalCollectionName) {
        Bundle args = new Bundle();
        args.putString(ARGS_KEY_USER_LIST_NAME, personalCollectionName);
        UserListFragment fragment = new UserListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCollectionName = getArguments().getString(ARGS_KEY_USER_LIST_NAME);
        mUserDataNode = (UserDataNode) NodesProvider.getInstance().getDataNode(NodeFactory.NodeType.USER);
        mAdapter = new UserListAdapter(mUserDataNode.getUserList(mCollectionName));
        mUserDataNode.registerClientCallback(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_category_list);

        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUserDataNode.unregisterClientCallback(this);
    }

    @Override
    public void onItemClick(int position) {
        getMainActivity().showPlaceFragment(mUserDataNode.getUserList(mCollectionName).getPlaces().get(position));
    }

    @Override
    public void onPendingDataFromServer() {

    }

    @Override
    public void onConnectivityError() {

    }

    @Override
    public void onUserDataSuccess(User user) {
        mAdapter.setUserList(mUserDataNode.getUserList(mCollectionName));
    }
}

