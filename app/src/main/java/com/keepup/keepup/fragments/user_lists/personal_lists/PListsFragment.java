package com.keepup.keepup.fragments.user_lists.personal_lists;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keepup.keepup.R;
import com.keepup.keepup.infra.factories.NodeFactory;
import com.keepup.keepup.infra.fragments.BaseFragment;
import com.keepup.keepup.infra.nodes.NodesProvider;
import com.keepup.keepup.models.User;
import com.keepup.keepup.nodes.UserDataNode;

public class PListsFragment extends BaseFragment implements UserDataNode.UserClientCallback, PListsAdapter.OnItemClickListener {

    private PListsAdapter mAdapter;
    private UserDataNode mUserDataNode;

    public static PListsFragment newInstance() {
        Bundle args = new Bundle();
        PListsFragment fragment = new PListsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public PListsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserDataNode = (UserDataNode) NodesProvider.getInstance().getDataNode(NodeFactory.NodeType.USER);
        mUserDataNode.registerClientCallback(this);
        mAdapter = new PListsAdapter(mUserDataNode.getUser().getUserLists());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_lists, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_user_lists_main);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
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
        getMainActivity().showPListFragment(mUserDataNode.getUser().getUserLists().get(position).getName());
    }

    @Override
    public void onButtonClick(final int position) {

        AlertDialog.Builder removeListDialogBuilder = new AlertDialog.Builder(getActivity());
        removeListDialogBuilder.setMessage("Delete list?");
        removeListDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mUserDataNode.removeUserList(mUserDataNode.getUser().getUserLists().get(position).getName());
            }
        });
        removeListDialogBuilder.setNegativeButton("No", null);
        AlertDialog removeFromCartDialog = removeListDialogBuilder.create();
        removeFromCartDialog.show();

    }
}
