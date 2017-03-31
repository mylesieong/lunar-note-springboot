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
import java.lang.Integer; 
import java.lang.Math;

import com.ibm.as400.access.*;

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
    
    @RequestMapping("/date")
    public DateResult date(){
       
        int result = 0;
        
        try{  
          String systemName="S657274B"; 
          String userName="ZCSERVICE";
          String password="ECIVRESCZ";
          String programName="/QSYS.LIB/IMODULE.LIB/DICBSYMD.PGM";
          AS400 system = new AS400(systemName, userName , password);
          ProgramParameter[] parmList= new ProgramParameter[1];
          parmList[0] = new ProgramParameter(8);
          ProgramCall program = new ProgramCall(system);
          program.setProgram(programName, parmList);
          if (program.run()!= true){
            AS400Message[] messagelist = program.getMessageList();
              for (int i = 0; i < messagelist.length; ++i){
                System.out.println(messagelist[i]);
              }
          }else{
            /* Get the result set */
            result = toInt(parmList[0].getOutputData());
          }
        }catch(Exception e){
          e.printStackTrace();
          result = -1;
        }
        
        return new DateResult(counter.incrementAndGet(), result);
    }
    
    public static int toInt(byte[] bytes) {
        int result = 0;
        for (int i=0; i<8; i++) {
            //Packed decimal consum ceiling((x+2)/2) bytes for a x digit number 
            //and it start with 4 bit 0 and end with 4 bit 1
            int index = (i+1)/2;
            int isHigh = i%2;
            System.out.println("index" + index + ", isHigh"+ isHigh);
            int value = isHigh==1?((int)bytes[index]>>4 & 0x0F):((int)bytes[index]& 0x0F);
            result = result * 10 + value;
        }
        return result;
    }
    
}
