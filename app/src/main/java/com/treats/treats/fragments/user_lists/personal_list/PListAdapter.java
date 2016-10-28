package com.treats.treats.fragments.user_lists.personal_list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.treats.treats.R;
import com.treats.treats.infra.factories.NodeFactory;
import com.treats.treats.infra.nodes.NodesProvider;
import com.treats.treats.models.Place;
import com.treats.treats.models.UserList;
import com.treats.treats.nodes.PlacesDataNode;

/**
 * Created by david.uzan on 9/1/2016.
 */
public class PListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private View.OnClickListener mOnClickListener;
    private OnItemClickListener mOnItemClickListener;
    private UserList mUserList;
    PlacesDataNode mPlacesDataNode;

    public PListAdapter(UserList userList) {
        mUserList = userList;
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
        View v0 = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_list_item, parent, false);
        return new ListItemViewHolder(v0, mOnClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Place place = mPlacesDataNode.getPlace(mUserList.getPlaces().get(position));
        if (place != null) {
            ((ListItemViewHolder) holder).bindData(position, place.mTitle);
        }
    }

    @Override
    public int getItemCount() {
        return mUserList.getPlaces().size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setUserList(UserList userList) {
        mUserList = userList;
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

