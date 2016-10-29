package com.keepup.keepup.infra.fragments;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kobiisha on 31/12/2015.
 */
public class PendingFragmentEntry<E extends Enum<?>> extends FragmentEntry<E> implements Parcelable {
    private Bundle mBundle;

    public PendingFragmentEntry(E fragmentType, Bundle extraBundle) {
        this(fragmentType, null, extraBundle);
    }

    public PendingFragmentEntry(E fragmentType, String tagSuffix, Bundle extraBundle) {
        super(fragmentType, tagSuffix);
        mBundle =  extraBundle;// == null ? new Bundle() : extraBundle; TODO
    }

    public Bundle getBundle() {
        return mBundle;
    }

    public FragmentEntry<E> toCurrent(){
        FragmentEntry<E> fragmentEntry =  new FragmentEntry<>();
        fragmentEntry.mFragmentType = this.mFragmentType;
        fragmentEntry.mFragmentTag = this.mFragmentTag;
        fragmentEntry.mRetainFragmentInstance = this.mRetainFragmentInstance;
        return fragmentEntry;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeBundle(this.mBundle);
    }

    private PendingFragmentEntry(Parcel in) {
        super(in);
        this.mBundle = in.readBundle();
    }

    public static final Creator<PendingFragmentEntry> CREATOR = new Creator<PendingFragmentEntry>() {
        public PendingFragmentEntry createFromParcel(Parcel source) {
            return new PendingFragmentEntry(source);
        }

        public PendingFragmentEntry[] newArray(int size) {
            return new PendingFragmentEntry[size];
        }
    };
}
