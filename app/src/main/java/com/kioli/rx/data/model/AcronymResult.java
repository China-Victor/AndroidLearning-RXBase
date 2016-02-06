package com.kioli.rx.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AcronymResult implements Parcelable {

	@SerializedName("sf")
	public boolean searchedAcronym;
	@SerializedName("lfs")
	public ArrayList<Acronym> acronyms;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeByte(searchedAcronym ? (byte) 1 : (byte) 0);
		dest.writeList(this.acronyms);
	}

	protected AcronymResult(Parcel in) {
		this.searchedAcronym = in.readByte() != 0;
		this.acronyms = new ArrayList<>();
		in.readList(this.acronyms, List.class.getClassLoader());
	}

	public static final Parcelable.Creator<AcronymResult> CREATOR = new Parcelable.Creator<AcronymResult>() {
		public AcronymResult createFromParcel(Parcel source) {
			return new AcronymResult(source);
		}

		public AcronymResult[] newArray(int size) {
			return new AcronymResult[size];
		}
	};
}
