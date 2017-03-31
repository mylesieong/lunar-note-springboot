package com.bcm.app;

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
