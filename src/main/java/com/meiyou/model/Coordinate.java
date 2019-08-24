package com.meiyou.model;

import java.io.Serializable;

public class Coordinate implements Serializable {

	
	private double latitude; //维度
	
	private double longitude; //经度

	private String key;

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "Coordinate [latitude=" + latitude + ", longitude=" + longitude + ", key=" + key + "]";
	}
}
