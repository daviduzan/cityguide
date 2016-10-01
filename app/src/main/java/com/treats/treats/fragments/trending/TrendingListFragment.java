package com.treats.treats.fragments.trending;


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
import com.treats.treats.models.ServerModels;
import com.treats.treats.nodes.TrendingDataNode;


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

        mAdapter = new TrendingListSectionedAdapter(mTrendingDataNode.getGroups());
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
        ServerModels.TrendingItemSM trendingItemSM = mTrendingDataNode.getGroups().get(section).getMembers().get(relativePosition);
        if (trendingItemSM.getPlace() != null) {
            getMainActivity().showPlaceFragment(trendingItemSM.getPlace());
        } else if (trendingItemSM.getCollection() != null) {
            getMainActivity().showCategoryListFragment(trendingItemSM.getCollection());
        }
    }
}

