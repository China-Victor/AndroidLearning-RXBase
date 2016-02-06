package com.kioli.rx.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Meme implements Parcelable {
	public String id;
	public String name;
	public String url;
	public Integer width;
	public Integer height;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.id);
		dest.writeString(this.name);
		dest.writeString(this.url);
		dest.writeValue(this.width);
		dest.writeValue(this.height);
	}

	protected Meme(Parcel in) {
		this.id = in.readString();
		this.name = in.readString();
		this.url = in.readString();
		this.width = (Integer) in.readValue(Integer.class.getClassLoader());
		this.height = (Integer) in.readValue(Integer.class.getClassLoader());
	}

	public static final Creator<Meme> CREATOR = new Creator<Meme>() {
		public Meme createFromParcel(Parcel source) {
			return new Meme(source);
		}

		public Meme[] newArray(int size) {
			return new Meme[size];
		}
	};

}