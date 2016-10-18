package com.treats.treats.fragments.user_lists;

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

public class UserListsMainFragment extends BaseFragment implements UserDataNode.UserClientCallback, UserListsMainAdapter.OnItemClickListener {

    private UserListsMainAdapter mAdapter;
    private UserDataNode mUserDataNode;

    public static UserListsMainFragment newInstance() {
        Bundle args = new Bundle();
        UserListsMainFragment fragment = new UserListsMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public UserListsMainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserDataNode = (UserDataNode) NodesProvider.getInstance().getDataNode(NodeFactory.NodeType.USER);
        mUserDataNode.registerClientCallback(this);
        mAdapter = new UserListsMainAdapter(mUserDataNode.getUser().getUserLists());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_lists_main, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_user_lists_main);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(this);

        return view;
    }


    @Override
    public void onPendingDataFromServer() {

    }

    @Override
    public void onConnectivityError() {

    }

    @Override
    public void onUserDataSuccess(User user) {
        mAdapter.setUserCollectionsData(user.getUserLists());
    }

    @Override
    public void onItemClick(int position) {
        getMainActivity().showUserListFragment(mUserDataNode.getUser().getUserLists().get(position).getName());
    }
}
