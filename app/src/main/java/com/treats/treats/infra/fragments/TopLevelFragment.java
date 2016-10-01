package com.treats.treats.infra.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.treats.treats.R;
import com.treats.treats.fragments.categories.category_list.CategoryListFragment;
import com.treats.treats.infra.exceptions.UndefinedCaseException;

import java.util.ArrayList;

/**
 * Created by kobiisha on 3/4/15.
 */
public class TopLevelFragment extends BaseFragment {
    public final static String TAG = TopLevelFragment.class.getSimpleName();

    public static final String ARGS_KEY_INITIAL_FRAGMENT_ENTRY = "args_key_initial_fragment_entry";
    public static final String ARGS_KEY_RTL = "args_key_rtl";
    private static final String ARGS_KEY_DRILL_DOWN_ENTRIES = "args_key_drill_down_entries";
    private ArrayList<FragmentEntry<FragmentType>> mDrillDownEntries = new ArrayList<>();
    private boolean mNeedToPopFullStack = false;
    private boolean mRTL;

    public enum FragmentType {
        CATEGORY_LIST,
    }

    public static TopLevelFragment newInstance(PendingFragmentEntry<FragmentType> initialFragmentEntry, boolean rtl) {
        if(initialFragmentEntry == null){
            throw new IllegalArgumentException("One should always provide a non null initialFragmentEntry");
        }
        TopLevelFragment fragment = new TopLevelFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGS_KEY_INITIAL_FRAGMENT_ENTRY, initialFragmentEntry);
        args.putBoolean(ARGS_KEY_RTL, rtl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRTL = getArguments().getBoolean(ARGS_KEY_RTL);
        if (savedInstanceState != null) {
            mDrillDownEntries = getArguments().getParcelableArrayList(ARGS_KEY_DRILL_DOWN_ENTRIES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drill_down, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentManager fragmentManager = getChildFragmentManager();
        Bundle args = getArguments();
        PendingFragmentEntry<FragmentType> initialFragmentEntry = args.getParcelable(ARGS_KEY_INITIAL_FRAGMENT_ENTRY);
        @SuppressWarnings("ConstantConditions")
        Fragment fragment = fragmentManager.findFragmentByTag(initialFragmentEntry.getFragmentTag());
        if (fragment == null) {
            fragment = createFragment(initialFragmentEntry);
            fragmentManager.beginTransaction().add(R.id.drill_down_container, fragment, initialFragmentEntry.getFragmentTag()).commit();
            mDrillDownEntries.add(initialFragmentEntry.toCurrent());
        }
    }

    @Override
    public void onResume(boolean newViewCreated) {
        super.onResume(newViewCreated);
        if (mNeedToPopFullStack) {
            mNeedToPopFullStack = false;
            popFullStack();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        getArguments().putParcelableArrayList(ARGS_KEY_DRILL_DOWN_ENTRIES, mDrillDownEntries);
    }

    public void drillDown(PendingFragmentEntry<FragmentType> pendingFragmentEntry, boolean returnToExistingEntry) {
        if(returnToExistingEntry){
            int entriesCount = mDrillDownEntries.size();
            if (pendingFragmentEntry.equals(mDrillDownEntries.get(entriesCount - 1))) {
                return;
            }
            int timesToPopBack = 0;
            for (int i = entriesCount - 2; i >= 0; i--) {
                timesToPopBack++;
                if (pendingFragmentEntry.equals(mDrillDownEntries.get(i))) {
                    popBack(timesToPopBack);
                    return;
                }
            }
        }
        Fragment fragment = createFragment(pendingFragmentEntry);
        mDrillDownEntries.add(pendingFragmentEntry.toCurrent());
        int[] animations = getAnimationsBasedOnRTL();
        getChildFragmentManager().
                beginTransaction().
                setCustomAnimations(animations[0], animations[1], animations[2], animations[3]).
                addToBackStack(null).
                replace(R.id.drill_down_container, fragment, pendingFragmentEntry.getFragmentTag()).
                commit();
    }

    private int[] getAnimationsBasedOnRTL(){
        if(mRTL){
            return new int[]{R.animator.slide_in_from_left, R.animator.slide_out_to_right, R.animator.slide_in_from_right, R.animator.slide_out_to_left};
        }else {
            return new int[]{R.animator.slide_in_from_right, R.animator.slide_out_to_left, R.animator.slide_in_from_left, R.animator.slide_out_to_right};
        }
    }

    private void popBack(int timesToPopBack) {
        for (int i = 0; i < timesToPopBack; i++) {
            popBack();
        }
    }

    private boolean popBack(){
        FragmentManager fragmentManager = getChildFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
            mDrillDownEntries.remove(mDrillDownEntries.size() - 1);
            return true;
        }
        return false;
    }

    public void popFullStack() {
        if (isActive()) {
            popBack(mDrillDownEntries.size() - 1);
        } else {
            mNeedToPopFullStack = true;
        }
    }

    @Override
    public boolean onBackPressed() {
        if (isActive() && mDrillDownEntries.size() > 0) {
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentEntry<FragmentType> topFragmentEntry = mDrillDownEntries.get(mDrillDownEntries.size() - 1);
            BaseFragment topFragment = (BaseFragment) fragmentManager.findFragmentByTag(topFragmentEntry.getFragmentTag());
            //If topFragment ==  null, back was pressed before transaction completed.
            if (topFragment != null && topFragment.onBackPressed()) {
                return true;
            }
            return popBack();
        }
        return false;
    }

    private Fragment createFragment(PendingFragmentEntry<FragmentType> pendingFragmentEntry) {
        switch (pendingFragmentEntry.getFragmentType()) {
            case CATEGORY_LIST:
                return CategoryListFragment.newInstance("");
            default:
                throw new UndefinedCaseException(pendingFragmentEntry.getFragmentTag(), pendingFragmentEntry.getFragmentType().getClass());
        }
    }
}
