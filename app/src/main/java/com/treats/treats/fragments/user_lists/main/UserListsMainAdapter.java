package com.treats.treats.fragments.user_lists.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.treats.treats.R;
import com.treats.treats.models.UserList;

import java.util.ArrayList;

/**
 * Created by david.uzan on 9/1/2016.
 */
public class UserListsMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private View.OnClickListener mOnClickListener;
    private OnItemClickListener mOnItemClickListener;
    private ArrayList<UserList> mUserLists;

    public UserListsMainAdapter(ArrayList<UserList> userLists) {
        mUserLists = userLists;

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
        View v0 = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_lists_main_list_item, parent, false);
        return new ListItemViewHolder(v0, mOnClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ListItemViewHolder) holder).bindData(position, mUserLists.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mUserLists.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setUserCollectionsData(ArrayList<UserList> userLists) {
        mUserLists = userLists;
        notifyDataSetChanged();
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
}

