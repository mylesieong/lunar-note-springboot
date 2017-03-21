package com.bcm.app;

public class LoginResult {
    
    private final long id;
    private final int resultCode;
    
    public LoginResult(long id, int resultCode) {
        this.id = id;
        this.resultCode = resultCode;
    }

    public long getId() {
        return id;
    }

    public int getResultCode() {
        return resultCode;
    }
    
    
}