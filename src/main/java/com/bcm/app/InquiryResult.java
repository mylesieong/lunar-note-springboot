package com.bcm.app;

/**
 * A simple POJO (plain old java object) that represent the result
 * of inquiry() method defined in class CentralController
 *
 */
public class InquiryResult {
    
    private final long mId;
    private final String mSysId;
    private final String mSysName;
    private final String mSysStatus;
    private final String mSysReturnCode;

    
    public InquiryResult(long id, String sysId, String sysName, String sysStatus, String sysReturnCode) {
        this.mId = id;
	this.mSysId = sysId;
	this.mSysName = sysName;
	this.mSysStatus = sysStatus;
	this.mSysReturnCode = sysReturnCode;
    }

    public long getId() {
        return this.mId;
    }
    
    public String getSysId() {
        return this.mSysId;
    }

    public String getSysName() {
        return this.mSysName;
    }

    public String getSysStatus() {
        return this.mSysStatus;
    }

    public String getSysReturnCode() {
        return this.mSysReturnCode;
    }

}
