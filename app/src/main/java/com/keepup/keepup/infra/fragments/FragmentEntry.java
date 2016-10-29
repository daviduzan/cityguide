package com.keepup.keepup.infra.fragments;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kobiisha on 30/12/2015.
 */
public class FragmentEntry<E extends Enum<?>> implements Parcelable {
    protected E mFragmentType;
    protected String mFragmentTag;
    protected boolean mRetainFragmentInstance = true;

    public FragmentEntry() {}

    public FragmentEntry(E fragmentType) {
        this(fragmentType, null);
    }

    public FragmentEntry(E fragmentType, String tagSuffix) {
        if(fragmentType == null){
            throw new IllegalArgumentException("null fragmentType");
        }
        mFragmentType = fragmentType;
        mFragmentTag = tagSuffix == null ? fragmentType.name() : fragmentType.name() + tagSuffix;
    }


    public E getFragmentType() {
        return mFragmentType;
    }

    public String getFragmentTag() {
        return mFragmentTag;
    }

    public boolean isRetainFragmentInstance() {
        return mRetainFragmentInstance;
    }

    public void setRetainFragmentInstance(boolean retainFragmentInstance) {
        mRetainFragmentInstance = retainFragmentInstance;
    }

    @Override
    public boolean equals(Object o) {
        if(super.equals(o)){
            return true;
        }
        if(o instanceof FragmentEntry){
            FragmentEntry other = (FragmentEntry) o;
            if(mFragmentType.getClass() == other.getFragmentType().getClass()){
                return mFragmentType == other.getFragmentType() && mFragmentTag.contentEquals(other.getFragmentTag());
            }
        }
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mFragmentTag);
        dest.writeSerializable(this.mFragmentType.getClass());
        dest.writeInt(this.mFragmentType == null ? -1 : this.mFragmentType.ordinal());
        dest.writeInt(this.mRetainFragmentInstance ? 1 : 0);
    }


    @SuppressWarnings("unchecked")
    protected FragmentEntry(Parcel in) {
        this.mFragmentTag = in.readString();
        Class<?> type = (Class<?>) in.readSerializable();
        int tmpType = in.readInt();
        this.mFragmentType = tmpType == -1 ? null : (E) type.getEnumConstants()[tmpType];
        this.mRetainFragmentInstance = in.readInt() == 1;
    }

    public static final Creator<FragmentEntry> CREATOR = new Creator<FragmentEntry>() {
        public FragmentEntry createFromParcel(Parcel source) {
            return new FragmentEntry(source);
        }

        public FragmentEntry[] newArray(int size) {
            return new FragmentEntry[size];
        }
    };
}
