package com.bcm.app;

/**
 * A simple POJO (plain old java object) that represent the result
 * of date() method defined in class CentralController
 *
 */
public class DateResult {
    
    private final long mId;
    private final int mDate;
    
    public DateResult(long id, int date) {
        this.mId = id;
        this.mDate = date;
    }

    public long getId() {
        return this.mId;
    }
    
    public int getDate() {
        return this.mDate;
    }

}
