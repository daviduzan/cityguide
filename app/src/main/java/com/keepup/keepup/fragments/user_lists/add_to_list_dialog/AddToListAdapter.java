package com.keepup.keepup.fragments.user_lists.add_to_list_dialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.keepup.keepup.R;
import com.keepup.keepup.models.UserList;

import java.util.ArrayList;

/**
 * Created by david.uzan on 10/21/2016.
 */

public class AddToListAdapter extends BaseAdapter implements View.OnClickListener {

    private ArrayList<UserList> mUserLists;
    private String mPlaceName;
    private boolean[] mPlaceInList;

    AddToListAdapter(ArrayList<UserList> userLists, String placeName) {
        mUserLists = userLists;
        mPlaceName = placeName;
        initiateBooleanArray(userLists);
    }

    private void initiateBooleanArray(ArrayList<UserList> userLists) {
        mPlaceInList = new boolean[userLists.size()];
        for (int i = 0; i < mUserLists.size(); i++) {
            UserList list = mUserLists.get(i);
            if (list.getPlaces().contains(mPlaceName)) {
                mPlaceInList[i] = true;
            }
        }
    }

    @Override
    public int getCount() {
        return mUserLists.size();
    }

    @Override
    public Object getItem(int i) {
        return mUserLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_add_to_user_lists, parent, false);
            viewHolder.tvCollectionName = (TextView) convertView.findViewById(R.id.tv_collection_name);
            viewHolder.cbAddToCollection = (CheckBox) convertView.findViewById(R.id.cb_add);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvCollectionName.setText(mUserLists.get(i).getName());

        viewHolder.cbAddToCollection.setChecked(mPlaceInList[i]);

        viewHolder.cbAddToCollection.setTag(i);
        viewHolder.cbAddToCollection.setOnClickListener(this);

        return convertView;
    }

    ArrayList<UserList> getUserLists() {
        return mUserLists;
    }

    public boolean[] getPlaceInList() {
        return mPlaceInList;
    }

    void setUserLists(ArrayList<UserList> userLists) {
        mUserLists = userLists;
        initiateBooleanArray(userLists);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        int location = (int) view.getTag();
        mPlaceInList[location] = (((CheckBox) view).isChecked());
    }

    private static class ViewHolder {
        private TextView tvCollectionName;
        private CheckBox cbAddToCollection;
    }
}
