package com.kioli.rx.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MemeData implements Parcelable {
	public ArrayList<Meme> memes;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeTypedList(memes);
	}

	protected MemeData(Parcel in) {
		this.memes = in.createTypedArrayList(Meme.CREATOR);
	}

	public static final Parcelable.Creator<MemeData> CREATOR = new Parcelable.Creator<MemeData>() {
		public MemeData createFromParcel(Parcel source) {
			return new MemeData(source);
		}

		public MemeData[] newArray(int size) {
			return new MemeData[size];
		}
	};

}