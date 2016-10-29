package com.keepup.keepup.fragments.trending;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keepup.keepup.R;
import com.keepup.keepup.infra.factories.NodeFactory;
import com.keepup.keepup.infra.fragments.BaseFragment;
import com.keepup.keepup.infra.nodes.NodesProvider;
import com.keepup.keepup.models.TrendingItem;
import com.keepup.keepup.nodes.TrendingDataNode;


public class TrendingListFragment extends BaseFragment implements TrendingDataNode.TrendingClientCallback, TrendingListSectionedAdapter.OnItemClickListener{

    private static final String mCollectionName = "trending";

    private TrendingDataNode mTrendingDataNode;
    private TrendingListSectionedAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public static TrendingListFragment newInstance() {
        Bundle args = new Bundle();
        TrendingListFragment fragment = new TrendingListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTrendingDataNode = (TrendingDataNode) NodesProvider.getInstance().getDataNode(NodeFactory.NodeType.TRENDING);
        mTrendingDataNode.registerClientCallback(this);

        mAdapter = new TrendingListSectionedAdapter(mTrendingDataNode.getGroups(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trending_list, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_trending_list);

        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTrendingDataNode.unregisterClientCallback(this);
    }

    @Override
    public void onDataSuccess() {
        mAdapter.setData(mTrendingDataNode.getGroups());
    }

    @Override
    public void onPendingDataFromServer() {

    }

    @Override
    public void onConnectivityError() {

    }

    @Override
    public void onItemClick(int section, int relativePosition) {
        TrendingItem trendingItem = mTrendingDataNode.getGroups().get(section).getMembers().get(relativePosition);
        if (trendingItem.getPlace() != null) {
            getMainActivity().showPlaceFragment(trendingItem.getPlace());
        } else if (trendingItem.getCollection() != null) {
            getMainActivity().showCategoryListFragment(trendingItem.getCollection());
        }
    }
}

