package com.example.swiftcheckin;

import android.location.Location;
import android.media.Image;
import android.net.Uri;
import android.provider.Settings;

import java.util.Date;

public class Event {
    /*
    * What are the properties of an event?
    * Needs a Date
    * Needs a Title
    * Needs an Image
    * Needs a Location
    * */
    private Date date;
    private String eventTitle;
    private Uri eventPoster;
//    private Image poster;
    private String eventImageUrl;
    private String location;
    private String deviceId;
    private String description;
    private int maxAttendees;

    public Event(){
    }

    /**
     * This creates an event.

     * @param eventTitle - Represents the name of the event
     * @param location - Represents the event location.
     * @param description - Brief description of the event
     * @param deviceId - Unique identifier for the device.
     */
    public Event(String eventTitle, String description, String location, Uri eventPoster, String deviceId){
        this.eventTitle = eventTitle;
        this.location = location;
        this.description = description;
        this.deviceId = deviceId;
        this.eventPoster = eventPoster;
    }
    public Event(String eventTitle, String description, String location, String eventImageUrl, String deviceId){
        this.eventTitle = eventTitle;
        this.location = location;
        this.description = description;
        this.eventImageUrl = eventImageUrl;
        this.deviceId = deviceId;
    }


    public String getDeviceId() {
        return deviceId;
    }

    public Uri getEventPoster() {
        return eventPoster;
    }

    /**
     * This returns the maximum number of attendees.
     * @return
     * Returns maxAttendees
     */
    public int getMaxAttendees() {
        return maxAttendees;
    }


    /**
     * Returns the date
     * @return
     * Returns date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Returns the title of the event
     * @return
     * Returns eventTitle
     */
    public String getEventTitle() {
        return eventTitle;
    }

    /**
     * Returns location of the event
     * @return
     * Returns location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Returns the event description
     * @return
     * Returns description.
     */
    public String getDescription() {
        return description;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public void setEventPoster(Uri eventPoster) {
        this.eventPoster = eventPoster;
    }

    public String getEventImageUrl() {
        return eventImageUrl;
    }

    public void setEventImageUrl(String eventImageUrl) {
        this.eventImageUrl = eventImageUrl;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMaxAttendees(int maxAttendees) {
        this.maxAttendees = maxAttendees;
    }
}
