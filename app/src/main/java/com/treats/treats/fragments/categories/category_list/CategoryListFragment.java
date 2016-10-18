package com.treats.treats.fragments.categories.category_list;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.treats.treats.App;
import com.treats.treats.R;
import com.treats.treats.models.SimpleCollection;
import com.treats.treats.nodes.CollectionsDataNode;
import com.treats.treats.infra.factories.NodeFactory;
import com.treats.treats.infra.fragments.BaseFragment;
import com.treats.treats.infra.nodes.NodesProvider;


public class CategoryListFragment extends BaseFragment implements CollectionsDataNode.CollectionsClientCallback, SimpleCollectionAdapter.OnItemClickListener {

    public static final String ARGS_KEY_COLLECTION_NAME = "args_key_collection_name";

    private CollectionsDataNode mCollectionsDataNode;
    private SimpleCollectionAdapter mSimpleCollectionAdapter;
    private String mCollectionName;

    public static CategoryListFragment newInstance(String collectionName) {
        Bundle args = new Bundle();
        args.putString(ARGS_KEY_COLLECTION_NAME, collectionName);
        CategoryListFragment fragment = new CategoryListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCollectionName = getArguments().getString(ARGS_KEY_COLLECTION_NAME);
        mCollectionsDataNode = (CollectionsDataNode) NodesProvider.getInstance().getDataNode(NodeFactory.NodeType.COLLECTIONS);
        SimpleCollection simpleCollection = mCollectionsDataNode.getSimpleCollection(mCollectionName);
        if (simpleCollection == null) {
            getFragmentManager().popBackStack();
            App.toast("Collection " + mCollectionName + " not found");
        }
        mSimpleCollectionAdapter = new SimpleCollectionAdapter(simpleCollection);
        mCollectionsDataNode.registerClientCallback(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categoty_list, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_category_list);

        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(mSimpleCollectionAdapter);

        mSimpleCollectionAdapter.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCollectionsDataNode.unregisterClientCallback(this);
    }

    @Override
    public void onItemClick(int position) {
//        getMainActivity().showPlaceFragment(mCollectionsDataNode.getCollection(mCollectionName).getMembers().get(position));
    }

    @Override
    public void onSimpleCollectionsDataSuccess() {
        mSimpleCollectionAdapter.setSimpleCollectionsData(mCollectionsDataNode.getSimpleCollection(mCollectionName));
    }

    @Override
    public void onPendingDataFromServer() {

    }

    @Override
    public void onConnectivityError() {

    }
}

