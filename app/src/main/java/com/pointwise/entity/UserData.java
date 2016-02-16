package com.pointwise.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.pointwise.util.PointwiseUtil;

import java.util.Date;

/**
 * Created by wbatista on 2/12/16.
 */
public class UserData extends BaseEntity implements Parcelable {

    private String data;

    private int weight;

    // Date will be used as a tiebraker to determine the order in PriorityQueue
    private Date date;

    public UserData() {

    }

    public UserData(final Parcel in) {
        this.data = in.readString();
        this.weight = in.readInt();
        this.date = new Date(in.readLong());
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.data == null) ? 0 : this.data.hashCode());
        result = prime * result + ((this.weight == 0) ? 0 : String.valueOf(this.weight).hashCode());
        result = prime * result + ((this.date == null) ? 0 : this.date.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null) {
            return false;
        }

        if(!(o instanceof UserData)) {
            return false;
        }

        final UserData userData = (UserData) o;
        try {
            if (!(userData.getData().equals(this.data)) || userData.getWeight() != this.weight ||
                    !PointwiseUtil.isDateEqual(this.date, userData.getDate())) {
                return false;
            }
        } catch(IllegalArgumentException e) {
            return false;
        }

        return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.data);
        dest.writeInt(this.weight);

        if(this.date == null) {
            dest.writeLong(0);
        } else {
            dest.writeLong(this.date.getTime());
        }
    }

    public static final Parcelable.Creator<UserData> CREATOR = new Parcelable.Creator<UserData>() {

        @Override
        public UserData createFromParcel(Parcel source) {
            return new UserData(source);
        }

        @Override
        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };

    @Override
    public String toString() {
//        final StringBuilder builder = new StringBuilder();
//        builder.append(this.data).append(":").append(this.weight).append(":").append(this.date.toString());
//        return builder.toString();
        return String.valueOf(this.weight);
    }
}