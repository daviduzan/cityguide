package com.treats.treats.infra.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.treats.treats.activities.MainActivity;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by kobiisha on 2/25/15.
 */
public class BaseFragment extends Fragment {
    public static final String ARGS_KEY_MAIN_ID = "args_key_main_id";
    public static final String ARGS_KEY_MAIN_NAME = "args_key_main_name";
    public static final String ARGS_KEY_MAIN_EXTRA = "args_key_main_extra";
    public static final String ARGS_KEY_TAG = "args_key_tag";

    private boolean mActive = false;
    private AtomicBoolean mViewCreated = new AtomicBoolean(false);

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewCreated.set(true);
        mActive = true;
    }

    @Override
    final public void onResume() {
        super.onResume();
        mActive = true;
        onResume(mViewCreated.getAndSet(false));
    }

    public void onResume(boolean newViewCreated) {
    }

    @Override
    public void onPause() {
        super.onPause();
        mActive = false;
    }

    public boolean onBackPressed() {
        return false;
    }

    public boolean isActive() {
        return mActive;
    }

    public void drillDown(PendingFragmentEntry<TopLevelFragment.FragmentType> pendingFragmentEntry, boolean returnToExistingEntry) {
        Fragment fragment = this;
        Fragment parentFragment;
        do {
            parentFragment = fragment.getParentFragment();
            fragment = parentFragment;
        } while (parentFragment != null && !(parentFragment instanceof TopLevelFragment));
        if (parentFragment != null) {
            ((TopLevelFragment) parentFragment).drillDown(pendingFragmentEntry, returnToExistingEntry);
        }
    }

    protected MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }

}