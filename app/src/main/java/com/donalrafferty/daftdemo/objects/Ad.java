package com.donalrafferty.daftdemo.objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Ad
 * Object for holding the data related to each property
 * It follows the naming from the JSON so it can be easily parsed by the GSON library
 * It is also parcelable so it can be sent via Intents
 */
public class Ad implements Parcelable{

    //Variables
    String daft_url;
    String property_type;
    String house_type;
    String description;
    double price;
    int bedrooms;
    int bathrooms;
    double square_metres;
    String[] features;
    String ber_rating;
    String full_address;
    String contact_name;
    String phone1;
    String main_email;
    String large_thumbnail_url;

    //Empty constructor
    public Ad(){

    };

    //Parcelable constuctor
    public Ad(Parcel inParcel){
        this.daft_url = inParcel.readString();
        this.property_type = inParcel.readString();
        this.house_type = inParcel.readString();
        this.description = inParcel.readString();
        this.price = inParcel.readDouble();
        this.bedrooms = inParcel.readInt();
        this.bathrooms = inParcel.readInt();
        this.square_metres = inParcel.readDouble();
        this.features = inParcel.createStringArray();
        this.ber_rating = inParcel.readString();
        this.full_address = inParcel.readString();
        this.contact_name = inParcel.readString();
        this.phone1 = inParcel.readString();
        this.main_email = inParcel.readString();
        this.large_thumbnail_url = inParcel.readString();
    }

    //Parcelable CREATOR
    public static final Creator CREATOR = new Creator() {
        @Override
        public Ad createFromParcel(Parcel parcel) {
            return new Ad(parcel);
        }

        @Override
        public Ad[] newArray(int i) {
            return new Ad[i];
        }
    };


    //GETTERS & SETTERS
    public String getProperty_type() {
        return property_type;
    }

    public void setProperty_type(String property_type) {
        this.property_type = property_type;
    }

    public void setSquare_metres(double square_metres) {
        this.square_metres = square_metres;
    }

    public int getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(int bathrooms) {
        this.bathrooms = bathrooms;
    }

    public int getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }

    public String getBer_rating() {
        return ber_rating;
    }

    public void setBer_rating(String ber_rating) {
        this.ber_rating = ber_rating;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getDaft_url() {
        return daft_url;
    }

    public void setDaft_url(String daft_url) {
        this.daft_url = daft_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getFeatures() {
        return features;
    }

    public void setFeatures(String[] features) {
        this.features = features;
    }

    public String getFull_address() {
        return full_address;
    }

    public void setFull_address(String full_address) {
        this.full_address = full_address;
    }

    public String getHouse_type() {
        return house_type;
    }

    public void setHouse_type(String house_type) {
        this.house_type = house_type;
    }

    public String getMain_email() {
        return main_email;
    }

    public void setMain_email(String main_email) {
        this.main_email = main_email;
    }

    public String getLarge_thumbnail_url() {
        return large_thumbnail_url;
    }

    public void setLarge_thumbnail_url(String medium_thumbnail_url) {
        this.large_thumbnail_url = medium_thumbnail_url;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSquare_metres() {
        return square_metres;
    }

    public void setSquare_metres(long square_metres) {
        this.square_metres = square_metres;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //Parcelable Write
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(daft_url);
        parcel.writeString(property_type);
        parcel.writeString(house_type);
        parcel.writeString(description);
        parcel.writeDouble(price);
        parcel.writeInt(bedrooms);
        parcel.writeInt(bathrooms);
        parcel.writeDouble(square_metres);
        parcel.writeStringArray(features);
        parcel.writeString(ber_rating);
        parcel.writeString(full_address);
        parcel.writeString(contact_name);
        parcel.writeString(phone1);
        parcel.writeString(main_email);
        parcel.writeString(large_thumbnail_url);
    }
}
