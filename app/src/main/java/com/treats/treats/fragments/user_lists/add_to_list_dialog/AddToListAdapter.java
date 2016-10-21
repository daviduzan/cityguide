package com.treats.treats.fragments.user_lists.add_to_list_dialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.treats.treats.R;
import com.treats.treats.models.UserList;

import java.util.ArrayList;

/**
 * Created by david.uzan on 10/21/2016.
 */

public class AddToListAdapter extends BaseAdapter implements View.OnClickListener {

    private ArrayList<UserList> mUserLists;

    public AddToListAdapter(ArrayList<UserList> userLists) {
        mUserLists = userLists;
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
        viewHolder.cbAddToCollection.setTag(i);
        viewHolder.cbAddToCollection.setOnClickListener(this);

        return convertView;
    }

    public ArrayList<UserList> getUserLists() {
        return mUserLists;
    }

    public void setUserLists(ArrayList<UserList> userLists) {
        mUserLists = userLists;
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        int location = (int) view.getTag();
        mUserLists.get(location).setChecked(((CheckBox) view).isChecked());
    }

    private static class ViewHolder {
        private TextView tvCollectionName;
        private CheckBox cbAddToCollection;
    }
}
