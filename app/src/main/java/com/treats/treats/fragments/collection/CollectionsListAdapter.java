package com.treats.treats.fragments.collection;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.treats.treats.R;

/**
 * Created by david.uzan on 9/1/2016.
 */
public class CollectionsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM_TYPE_CATEGORY = 0;
    public static final int ITEM_TYPE_NORMAL = 1;

    private View.OnClickListener mOnClickListener;
    private OnItemClickListener mOnItemClickListener;
    private String[] mDataset;

    public CollectionsListAdapter(String[] myDataset) {
        mDataset = myDataset;

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
            case ITEM_TYPE_NORMAL:
                View v0 = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.collections_list_item, parent, false);
                return new ListItemViewHolder(v0, mOnClickListener);
        }

        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type) {
            case ITEM_TYPE_NORMAL:
                ((ListItemViewHolder) holder).bindData(position, mDataset[position]);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM_TYPE_NORMAL;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
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

        public void setCategoryText(String text) {
            tvLabel.setText(text);
        }
    }
}

