package com.treats.treats.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.treats.treats.R;
import com.treats.treats.infra.factories.NodeFactory;
import com.treats.treats.infra.nodes.NodesProvider;
import com.treats.treats.models.Collection;
import com.treats.treats.models.Place;
import com.treats.treats.nodes.PlacesDataNode;

/**
 * Created by david.uzan on 9/1/2016.
 */
public class TreatsGeneralListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM_TYPE_TITLE = 0;
    public static final int ITEM_TYPE_NORMAL = 1;

    private View.OnClickListener mOnClickListener;
    private OnItemClickListener mOnItemClickListener;
    private Collection mCategoryCollection;
    private PlacesDataNode mPlacesDataNode;

    public TreatsGeneralListAdapter(Collection categoryCollection) {
        mCategoryCollection = categoryCollection;
        mPlacesDataNode = (PlacesDataNode) NodesProvider.getInstance().getDataNode(NodeFactory.NodeType.PLACES);

        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mOnItemClickListener != null) {

                    mOnItemClickListener.onItemClick((int) view.getTag());
                }
            }
        };
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        switch (viewType) {
            case ITEM_TYPE_TITLE:
                View v1 = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.category_list_group_header, parent, false);
                return new ListCategoryViewHolder(v1);
            case ITEM_TYPE_NORMAL:
                View v0 = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.category_list_item, parent, false);
                return new ListItemViewHolder(v0, mOnClickListener);
        }

        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type) {
            case ITEM_TYPE_TITLE:
                ((ListCategoryViewHolder) holder).bindData(mCategoryCollection.getMembers().get(position));
                break;
            case ITEM_TYPE_NORMAL:
                String memberName = mCategoryCollection.getMembers().get(position);
                // TODO get the member from events/places table and set values
                Place place = mPlacesDataNode.getPlace(memberName);
                if (place == null) {
                    ((ListItemViewHolder) holder).bindData(position, memberName);
                } else {
                    ((ListItemViewHolder) holder).bindData(position, place.mTitle);
                }
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mCategoryCollection.getMembers().size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mCategoryCollection.getTypes() != null && (position < mCategoryCollection.getTypes().size()) && mCategoryCollection.getTypes().get(position) != null && mCategoryCollection.getTypes().get(position) == 1)
            return ITEM_TYPE_TITLE;
        else return ITEM_TYPE_NORMAL;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setCategoryCollection(Collection categoryCollection) {
        mCategoryCollection = categoryCollection;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class ListItemViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ListItemViewHolder(View v, View.OnClickListener clickListener) {
            super(v);
            this.itemView.setOnClickListener(clickListener);
            mTextView = (TextView) v.findViewById(R.id.temp_text_list);
        }

        public void bindData(int position, String data) {
            itemView.setTag(position);
            mTextView.setText(data);
        }
    }

    public static class ListCategoryViewHolder extends RecyclerView.ViewHolder {

        private TextView tvLabel;

        public ListCategoryViewHolder(View view) {
            super(view);
            tvLabel = (TextView) view.findViewById(R.id.tv_category);
        }

        public void bindData(String data) {
            tvLabel.setText(data);
        }
    }
}

