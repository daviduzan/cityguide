package com.treats.treats.fragments.categories.caterories_grid;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.treats.treats.R;
import com.treats.treats.models.ServerModels;

import java.util.ArrayList;

/**
 * Created by david.uzan on 9/1/2016.
 */
public class CategoriesGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private View.OnClickListener mOnClickListener;
    private OnItemClickListener mOnItemClickListener;
    private ArrayList<ServerModels.CategorySM> mCategories;

    public CategoriesGridAdapter(ArrayList<ServerModels.CategorySM> categories) {
        mCategories = categories;

        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mOnItemClickListener != null) {

                    mOnItemClickListener.onItemClick((int) view.getTag());
                }
            }
        };
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v0 = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_grid_item, parent, false);
        return new GridItemViewHolder(v0, mOnClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((GridItemViewHolder) holder).bindData(position, mCategories.get(position).getName());
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setCategories(ArrayList<ServerModels.CategorySM> categories) {
        mCategories = categories;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class GridItemViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public GridItemViewHolder(View v, View.OnClickListener clickListener) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.temp_text_list);
            this.itemView.setOnClickListener(clickListener);
        }

        public void bindData(int position, String data) {
            itemView.setTag(position);
            mTextView.setText(data);
        }
    }
}

