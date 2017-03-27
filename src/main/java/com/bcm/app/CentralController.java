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
        
		System.out.println("logon to AS400");
		AS400 system = new AS400(SYSNAME, USERNAME , PASSWORD);
		ProgramCall program=new ProgramCall(system);
		String programName = "/QSYS.LIB/YMYLES.LIB/JAVACALL.PGM";
		ProgramParameter[] parameterList = new ProgramParameter[0];
		AS400Text textData = new AS400Text(100, system);
		//parameterList[0] = new ProgramParameter(textData.toBytes("ABC"));

		try{
			System.out.println("Run Program");
			program.setProgram(programName);	
			program.setParameterList(parameterList);
			program.run();
			
			System.out.println("Present the changed variable");	
			AS400Message[] outputMessageList = program.getMessageList();
			for (int i = 0; i < outputMessageList.length; ++i){
					// Show each message.
					System.out.println(outputMessageList[i].getText());
					// Load additional message information.
					outputMessageList[i].load();
					//Show help text.
					System.out.println(outputMessageList[i].getHelp());
			}
            system.disconnectAllServices();
			
		}catch(Exception e){
			e.printStackTrace();
		}

        return new LoginResult(counter.incrementAndGet(), response);
    }
    
}