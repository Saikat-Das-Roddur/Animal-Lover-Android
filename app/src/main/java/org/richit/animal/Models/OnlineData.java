package org.richit.animal.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class OnlineData implements Parcelable {
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<OnlineData> CREATOR = new Parcelable.Creator<OnlineData>() {
        @Override
        public OnlineData createFromParcel(Parcel in) {
            return new OnlineData(in);
        }

        @Override
        public OnlineData[] newArray(int size) {
            return new OnlineData[size];
        }
    };
    int versionCode;
    boolean cancellable;
    String url;
    ArrayList<AnimalModel> data = new ArrayList<>();

    protected OnlineData(Parcel in) {
        versionCode = in.readInt();
        cancellable = in.readByte() != 0x00;
        url = in.readString();
        if (in.readByte() == 0x01) {
            data = new ArrayList<AnimalModel>();
            in.readList(data, AnimalModel.class.getClassLoader());
        } else {
            data = null;
        }
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public boolean isCancellable() {
        return cancellable;
    }

    public void setCancellable(boolean cancellable) {
        this.cancellable = cancellable;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<AnimalModel> getData() {
        return data;
    }

    public void setData(ArrayList<AnimalModel> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(versionCode);
        dest.writeByte((byte) (cancellable ? 0x01 : 0x00));
        dest.writeString(url);
        if (data == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(data);
        }
    }
}