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

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    // @RequestMapping("/greeting")
    // public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        // return new Greeting(counter.incrementAndGet(),
                            // String.format(template, name));
    // }
    
    @RequestMapping("/login")
    public LoginResult login(@RequestParam(value="netuser", defaultValue="netuser") String name) {
        int response = 99;
		try {
			//Establish Connection
			Class.forName("com.ibm.as400.access.AS400JDBCDriver");
			Properties prop = new Properties();
			prop.put("user", "PSTELLER");
			prop.put("password", "TELLER");
			prop.put("prompt", "false");
			prop.put("errors", "full");
			Connection as400 = DriverManager.getConnection("jdbc:as400://S657274B", prop);
			as400.setAutoCommit(true);
			
			//Call the Stored Procedure
			CallableStatement cstmt = as400.prepareCall("CALL YMYLES1.VERIFYUSR(?)");
			cstmt.registerOutParameter(1, Types.CHAR);
			cstmt.execute();
            
            //Get result
			response = Integer.parseInt(cstmt.getString(1));
			
			//clean up
			cstmt.close();
			as400.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

        return new LoginResult(counter.incrementAndGet(), response);
    }
    
}