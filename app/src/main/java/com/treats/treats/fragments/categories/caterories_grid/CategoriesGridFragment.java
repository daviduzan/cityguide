package com.treats.treats.fragments.categories.caterories_grid;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.treats.treats.R;
import com.treats.treats.infra.factories.NodeFactory;
import com.treats.treats.infra.fragments.BaseFragment;
import com.treats.treats.infra.nodes.NodesProvider;
import com.treats.treats.models.ServerModels;
import com.treats.treats.nodes.CategoriesDataNode;

import java.util.ArrayList;


public class CategoriesGridFragment extends BaseFragment implements CategoriesGridAdapter.OnItemClickListener, CategoriesDataNode.CategoriesClientCallback {

    private static final int SPAN_COUNT_GRID = 2;

    private CategoriesGridAdapter mAdapter;
    private CategoriesDataNode mCategoriesDataNode;


    public CategoriesGridFragment() {
        // Required empty public constructor
    }

    public static CategoriesGridFragment newInstance() {
        Bundle args = new Bundle();
        CategoriesGridFragment fragment = new CategoriesGridFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategoriesDataNode = (CategoriesDataNode) NodesProvider.getInstance().getDataNode(NodeFactory.NodeType.CATEGORIES);
        mAdapter = new CategoriesGridAdapter(mCategoriesDataNode.getCategories());
        mCategoriesDataNode.registerClientCallback(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories_grid, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_categories_grid);

        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT_GRID);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCategoriesDataNode.unregisterClientCallback(this);
    }

    @Override
    public void onItemClick(int position) {
        getMainActivity().showCategoryListFragment(mCategoriesDataNode.getCategories().get(position).getCollection());
    }

    @Override
    public void onPendingDataFromServer() {

    }

    @Override
    public void onConnectivityError() {
        // TODO handle
    }

    @Override
    public void onCatgoriesDataSuccess(ArrayList<ServerModels.CategorySM> categories) {
        mAdapter.setCategories(categories);
    }
}
