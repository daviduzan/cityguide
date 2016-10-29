package com.keepup.keepup.fragments.categories.category_list;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keepup.keepup.App;
import com.keepup.keepup.R;
import com.keepup.keepup.infra.factories.NodeFactory;
import com.keepup.keepup.infra.fragments.BaseFragment;
import com.keepup.keepup.infra.nodes.NodesProvider;
import com.keepup.keepup.models.CollectionCategory;
import com.keepup.keepup.models.SimpleCollection;
import com.keepup.keepup.nodes.CollectionsDataNode;

import java.util.ArrayList;


public class CategoryListFragment extends BaseFragment implements CollectionsDataNode.CollectionsClientCallback, SimpleCollectionAdapter.OnItemClickListener, CategorizedCollectionSectionedAdapter.OnItemClickListener {

    public static final String ARGS_KEY_COLLECTION_NAME = "args_key_collection_name";

    private CollectionsDataNode mCollectionsDataNode;
    private SimpleCollectionAdapter mSimpleCollectionAdapter;
    private CategorizedCollectionSectionedAdapter mCategorizedCollectionSectionedAdapter;
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
        if (simpleCollection != null) {
            mSimpleCollectionAdapter = new SimpleCollectionAdapter(simpleCollection);
            mCollectionsDataNode.registerClientCallback(this);
        } else {
            ArrayList<CollectionCategory> categorizedCollection = mCollectionsDataNode.getCategorizedCollection(mCollectionName);
            if (categorizedCollection != null) {
                mCategorizedCollectionSectionedAdapter = new CategorizedCollectionSectionedAdapter(categorizedCollection, this);
                mCollectionsDataNode.registerClientCallback(this);
            } else {
                getFragmentManager().popBackStack();
                App.toast("Collection " + mCollectionName + " not found");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categoty_list, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_category_list);

        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        if (mSimpleCollectionAdapter != null) {
            recyclerView.setAdapter(mSimpleCollectionAdapter);
            mSimpleCollectionAdapter.setOnItemClickListener(this);
        } else if (mCategorizedCollectionSectionedAdapter != null) {
            recyclerView.setAdapter(mCategorizedCollectionSectionedAdapter);
        }

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCollectionsDataNode.unregisterClientCallback(this);
    }

    @Override
    public void onItemClick(int position) {
        getMainActivity().showPlaceFragment(mCollectionsDataNode.getSimpleCollection(mCollectionName).getMembers().get(position));
    }

    @Override
    public void onItemClick(int section, int relativePosition) {
        getMainActivity().showPlaceFragment(mCollectionsDataNode.getCategorizedCollection(mCollectionName).get(section).getMembers().get(relativePosition));
    }

    @Override
    public void onSimpleCollectionsDataSuccess() {
        mSimpleCollectionAdapter.setSimpleCollectionsData(mCollectionsDataNode.getSimpleCollection(mCollectionName));
    }

    @Override
    public void onCategorizedCollectionDataSuccess() {
        mCategorizedCollectionSectionedAdapter.setCategorizedCollectionData(mCollectionsDataNode.getCategorizedCollection(mCollectionName));
    }

    @Override
    public void onPendingDataFromServer() {

    }

    @Override
    public void onConnectivityError() {

    }
}

