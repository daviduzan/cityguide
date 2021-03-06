package com.keepup.keepup.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.keepup.keepup.R;
import com.keepup.keepup.fragments.categories.caterories_grid.CategoriesGridFragment;
import com.keepup.keepup.fragments.categories.category_list.CategoryListFragment;
import com.keepup.keepup.fragments.user_lists.personal_lists.PListsFragment;
import com.keepup.keepup.fragments.user_lists.personal_list.PListFragment;
import com.keepup.keepup.fragments.place.PlaceFragment;
import com.keepup.keepup.fragments.trending.TrendingListFragment;
import com.keepup.keepup.infra.factories.NodeFactory;
import com.keepup.keepup.infra.nodes.NodesProvider;

public class MainActivity extends AppCompatActivity {

//    private CustomToolbar mToolbar;
    private boolean mRTL = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        mToolbar.setTitle("TREATS");
        setSupportActionBar(mToolbar);
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getFragmentManager(),
                MainActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        // Create some nodes to pre-fetch data
        NodesProvider.getInstance().getDataNode(NodeFactory.NodeType.COLLECTIONS);
        NodesProvider.getInstance().getDataNode(NodeFactory.NodeType.PLACES);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        NodesProvider.getInstance().registerAliveValueEventListeners();
    }

    @Override
    protected void onStop() {
//        NodesProvider.getInstance().unregisterActiveValueEventListeners();
        super.onStop();
    }

    public void showCategoryListFragment(String categoryCollectionName) {
        CategoryListFragment fragment = CategoryListFragment.newInstance(categoryCollectionName);
        int[] animations = getAnimationsBasedOnRTLInOnly();
        getFragmentManager().beginTransaction()
        .setCustomAnimations(animations[0], animations[1], animations[2], animations[3])
        .addToBackStack(null)
        .add(R.id.fullscreen_fragment_container, fragment)
        .commit();
    }

public void showPListFragment(String personalCollectionName) {
        PListFragment fragment = PListFragment.newInstance(personalCollectionName);
        int[] animations = getAnimationsBasedOnRTLInOnly();
        getFragmentManager().beginTransaction()
                .setCustomAnimations(animations[0], animations[1], animations[2], animations[3])
                .addToBackStack(null)
                .add(R.id.fullscreen_fragment_container, fragment)
                .commit();
    }

    public void showPlaceFragment(String placeName) {
        PlaceFragment fragment = PlaceFragment.newInstance(placeName);
        int[] animations = getAnimationsBasedOnRTLInOnly();
        getFragmentManager().beginTransaction()
                .setCustomAnimations(animations[0], animations[1], animations[2], animations[3])
                .addToBackStack(null)
                .add(R.id.fullscreen_fragment_container, fragment)
                .commit();
    }

    private int[] getAnimationsBasedOnRTL(){
        if(mRTL){
            return new int[]{R.animator.slide_in_from_left, R.animator.slide_out_to_right, R.animator.slide_in_from_right, R.animator.slide_out_to_left};
        }else {
            return new int[]{R.animator.slide_in_from_right, R.animator.slide_out_to_left, R.animator.slide_in_from_left, R.animator.slide_out_to_right};
        }
    }

    private int[] getAnimationsBasedOnRTLInOnly(){
        if(mRTL){
            return new int[]{R.animator.slide_in_from_left, 0, R.animator.slide_in_from_right, 0};
        }else {
            return new int[]{R.animator.slide_in_from_right, 0, R.animator.slide_in_from_left, 0};
        }
    }

    public static class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 3;
        private String tabTitles[] = new String[]{"TRENDING", "CATEGORIES", "Collections"};

        public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return TrendingListFragment.newInstance();
                case 1:
                    return CategoriesGridFragment.newInstance();
                case 2:
                    return PListsFragment.newInstance();
                default:
                    return TrendingListFragment.newInstance();
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }
    }
}
