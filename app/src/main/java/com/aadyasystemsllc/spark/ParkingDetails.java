package com.aadyasystemsllc.spark;

import com.google.firebase.firestore.GeoPoint;

public class ParkingDetails {
    String mobileNumber,parkingLocation,parkingName,parkingPoint,ImageUrl;

    private GeoPoint community_location;

   /* public ParkingDetails(String mobileNumber, String parkingLocation, String parkingName, String parkingPoint,String ImageUrl) {
        this.mobileNumber = mobileNumber;
        this.parkingLocation = parkingLocation;
        this.parkingName = parkingName;
        this.parkingPoint = parkingPoint;
        this.ImageUrl=ImageUrl;
    }
*/

    public GeoPoint getCommunity_location() {
        return community_location;
    }

    public void setCommunity_location(GeoPoint community_location) {
        this.community_location = community_location;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getParkingLocation() {
        return parkingLocation;
    }

    public void setParkingLocation(String parkingLocation) {
        this.parkingLocation = parkingLocation;
    }

    public String getParkingName() {
        return parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }

    public String getParkingPoint() {
        return parkingPoint;
    }

    public void setParkingPoint(String parkingPoint) {
        this.parkingPoint = parkingPoint;
    }
}
