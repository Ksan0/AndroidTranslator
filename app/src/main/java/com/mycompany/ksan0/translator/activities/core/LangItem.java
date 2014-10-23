package com.mycompany.ksan0.translator.activities.core;


import android.os.Parcel;
import android.os.Parcelable;

public class LangItem implements Parcelable {
    public static final Parcelable.Creator<LangItem> CREATOR = new Parcelable.Creator<LangItem>() {
        public LangItem createFromParcel(Parcel parcel) {
            return new LangItem(parcel);
        }

        public LangItem[] newArray(int size) {
            return new LangItem[size];
        }
    };

    private String from;
    private String to;
    private String fromTitle;
    private String toTitle;

    private LangItem(Parcel parcel) {
        from    = parcel.readString();
        to      = parcel.readString();
        fromTitle = parcel.readString();
        toTitle = parcel.readString();
    }

    public LangItem(String from, String to, String fromTitle, String toTitle) {
        this.from       = from;
        this.to         = to;
        this.fromTitle  = fromTitle;
        this.toTitle    = toTitle;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getFromTitle() {
        return fromTitle;
    }

    public String getToTitle() {
        return toTitle;
    }

    @Override
    public String toString() {
        return String.format("%s --> %s", fromTitle, toTitle);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(from);
        parcel.writeString(to);
        parcel.writeString(fromTitle);
        parcel.writeString(toTitle);
    }

}
