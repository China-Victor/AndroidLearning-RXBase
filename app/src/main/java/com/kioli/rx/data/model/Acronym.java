package com.kioli.rx.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Acronym implements Parcelable {
	@SerializedName("lf")
	public String meaning;
	@SerializedName("freq")
	public int frequency;
	@SerializedName("since")
	public int yearOrigin;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.meaning);
		dest.writeInt(this.frequency);
		dest.writeInt(this.yearOrigin);
	}

	protected Acronym(Parcel in) {
		this.meaning = in.readString();
		this.frequency = in.readInt();
		this.yearOrigin = in.readInt();
	}

	public static final Parcelable.Creator<Acronym> CREATOR = new Parcelable.Creator<Acronym>() {
		public Acronym createFromParcel(Parcel source) {
			return new Acronym(source);
		}

		public Acronym[] newArray(int size) {
			return new Acronym[size];
		}
	};
}