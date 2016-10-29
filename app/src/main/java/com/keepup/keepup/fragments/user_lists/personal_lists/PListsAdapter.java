package com.keepup.keepup.fragments.user_lists.personal_lists;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.keepup.keepup.R;
import com.keepup.keepup.models.UserList;

import java.util.ArrayList;

/**
 * Created by david.uzan on 9/1/2016.
 */
public class PListsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private View.OnClickListener mOnClickListener;
    private View.OnClickListener mOnButtonClickListener;
    private OnItemClickListener mOnItemClickListener;
    private ArrayList<UserList> mUserLists;

    public PListsAdapter(ArrayList<UserList> userLists) {
        mUserLists = userLists;

        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mOnItemClickListener != null) {

                    mOnItemClickListener.onItemClick((int) view.getTag());
                }
            }
        };

        mOnButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mOnItemClickListener != null) {

                    mOnItemClickListener.onButtonClick((int) view.getTag());
                }
            }
        };
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View v0 = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_lists_main_list_item, parent, false);
        return new ListItemViewHolder(v0, mOnClickListener, mOnButtonClickListener);
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

    public void setUserCollectionsData(ArrayList<UserList> userLists) {
        mUserLists = userLists;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onButtonClick(int position);
    }

    public static class ListItemViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public Button mButton;

        public ListItemViewHolder(View v, View.OnClickListener clickListener, View.OnClickListener onButtonClickListener) {
            super(v);
            this.itemView.setOnClickListener(clickListener);
            mTextView = (TextView) v.findViewById(R.id.tv_collection_name);
            mButton = (Button) v.findViewById(R.id.btn_delete);
            mButton.setOnClickListener(onButtonClickListener);
        }

        public void bindData(int position, String data) {
            itemView.setTag(position);
            mButton.setTag(position);
            mTextView.setText(data);
        }
    }
}

