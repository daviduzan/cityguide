package com.treats.treats.fragments.categories.category_list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter;
import com.treats.treats.R;
import com.treats.treats.models.ServerModels;

import java.util.ArrayList;

/**
 * Created by david.uzan on 9/1/2016.
 */
public class CategorizedCollectionSectionedAdapter extends SectionedRecyclerViewAdapter<RecyclerView.ViewHolder> {

    private OnItemClickListener mOnItemClickListener;
    private ArrayList<ServerModels.CollectionCategorySM> mCollectionCategorySMs;

    public CategorizedCollectionSectionedAdapter(ArrayList<ServerModels.CollectionCategorySM> categorySMs, OnItemClickListener onItemClickListener) {
        mCollectionCategorySMs = categorySMs;
        mOnItemClickListener = onItemClickListener;

//        mOnClickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (mOnItemClickListener != null) {
//
//                    mOnItemClickListener.onItemClick((int) view.getTag(TAG_KEY_SECTION), (int) view.getTag(TAG_KEY_RELATIVE_POSITION));
//                }
//            }
//        };
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Change inflated layout based on 'header'.
        View v = LayoutInflater.from(parent.getContext())
                .inflate(viewType == VIEW_TYPE_HEADER ? R.layout.category_list_group_header : R.layout.category_list_item, parent, false);
        return new CategorizedCollectionViewHolder(v, mOnItemClickListener);

    }

    @Override
    public int getSectionCount() {
        return mCollectionCategorySMs.size();
    }

    @Override
    public int getItemCount(int section) {
        return mCollectionCategorySMs.get(section).getMembers().size();
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int section) {
        // Setup header view.
        ((CategorizedCollectionViewHolder) holder).bindHeaderData(section, mCollectionCategorySMs.get(section).getTitle());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int section, int relativePosition, int absolutePosition) {
        // Setup non-header view.
        // 'section' is section index.
        // 'relativePosition' is index in this section.
        // 'absolutePosition' is index out of all non-header items.
        // See sample project for a visual of how these indices work.


        String member = mCollectionCategorySMs.get(section).getMembers().get(relativePosition);
        ((CategorizedCollectionViewHolder) holder).bindItemData(section, relativePosition, member);
        // TODO get the member from events/places table and set values
    }

    public void setCategorizedCollectionData(ArrayList<ServerModels.CollectionCategorySM> data) {
        mCollectionCategorySMs = data;
        notifyDataSetChanged();
    }

    interface OnItemClickListener {
        void onItemClick(int section, int relativePosition);
    }

    private static class CategorizedCollectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvItem;
        TextView tvLabel;
        int mSection;
        int mRelativePosition;
        OnItemClickListener mOnClickListener;

        CategorizedCollectionViewHolder(View v, OnItemClickListener onItemClickListener) {
            super(v);
            v.setOnClickListener(this);
            tvItem = (TextView) v.findViewById(R.id.temp_text_list);
            tvLabel = (TextView) v.findViewById(R.id.tv_category);
            mOnClickListener = onItemClickListener;
        }

        void bindItemData(int section, int relativePosition, String data) {
            tvItem.setText(data);
            mSection = section;
            mRelativePosition = relativePosition;
        }


        public void bindHeaderData(int section, String data) {
            tvLabel.setText(data);
            mSection = section;
            mRelativePosition = -1;
        }

        @Override
        public void onClick(View view) {
            if (mRelativePosition != -1 && mOnClickListener != null) {
                mOnClickListener.onItemClick(mSection, mRelativePosition);
            }
        }
    }
}

