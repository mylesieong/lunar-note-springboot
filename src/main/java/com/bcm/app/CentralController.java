package com.bcm.app;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Properties;
import java.util.StringTokenizer;

@RestController
public class CentralController {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/login")
    public LoginResult login(@RequestParam(value="netuser", defaultValue="netuser") String name) {
        int response = 99;
        return new LoginResult(counter.incrementAndGet(), 99);
    }

    @RequestMapping("/inquiry")
    public InquiryResult inquiry(@RequestParam(value="netuser", defaultValue="netuser") String name) {
	String sysId = "100123";
	String sysName = "CHAN DA MAN";
	String sysStatus = "PENDING";
	String sysReturnCode = "0";
        return new InquiryResult(counter.incrementAndGet(), sysId, sysName, sysStatus, sysReturnCode);
    }
    
}
