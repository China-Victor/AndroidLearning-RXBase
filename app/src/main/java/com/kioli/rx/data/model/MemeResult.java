package com.kioli.rx.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MemeResult implements Parcelable {
	public boolean success;
	public MemeData data;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeByte(success ? (byte) 1 : (byte) 0);
		dest.writeParcelable(this.data, 0);
	}

	protected MemeResult(Parcel in) {
		this.success = in.readByte() != 0;
		this.data = in.readParcelable(MemeData.class.getClassLoader());
	}

	public static final Parcelable.Creator<MemeResult> CREATOR = new Parcelable.Creator<MemeResult>() {
		public MemeResult createFromParcel(Parcel source) {
			return new MemeResult(source);
		}

		public MemeResult[] newArray(int size) {
			return new MemeResult[size];
		}
	};

}
