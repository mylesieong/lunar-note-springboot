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

/**
 * This is the controllor of a springboot project. Two url mapping 
 * are declared and implemented in this class. 
 *
 */
@RestController
public class CentralController {

    // Counter to identify API result
    private final AtomicLong counter = new AtomicLong();

    /**
     * Method inquiry maps the /inquiry url. When user call this API, it will
     * read parameter sent from user and invoke AS400 program YMYLES1/DSNTEINQCL 
     * and return the result to user.
     *
     * @return A DateResult object that contains the date
     */
    @RequestMapping("/inquiry")
    public InquiryResult inquiry(@RequestParam(value="key", defaultValue="") String key) {
       
        String sysId = key;
        String sysName = "";
        String sysStatus = "";
        String sysReturnCode = "";
      
        try{  
            String systemName="S657274B"; 
            String userName="ZCSERVICE";
            String password="ECIVRESCZ";
            String programName="/QSYS.LIB/YMYLES1.LIB/DSNTEINQCL.PGM"; 

            AS400 system = new AS400(systemName, userName , password);
            ProgramParameter[] parmList= new ProgramParameter[4];
            AS400Text idText = new AS400Text(8);
            parmList[0] = new ProgramParameter(idText.toBytes(sysId));
            parmList[1] = new ProgramParameter(80);
            parmList[2] = new ProgramParameter(10);
            parmList[3] = new ProgramParameter(1);

            ProgramCall program = new ProgramCall(system);
            program.setProgram(programName, parmList);

            // Run and get the result
            if (program.run()!= true){

                // Program run fail
                AS400Message[] messagelist = program.getMessageList();
                for (int i = 0; i < messagelist.length; ++i){
                    System.out.println(messagelist[i]);
                }

            }else{

                // Program run success 
                sysName = new String(parmList[1].getOutputData(), "Cp937");//Get chinese name with cp937 encoding
                AS400Text statusText = new AS400Text(10);//Get english status with default encoding cp1047
                sysStatus = (String)statusText.toObject(parmList[2].getOutputData());
                AS400Text returnCodeText = new AS400Text(1);//Get return code with default encoding cp1047
                sysReturnCode=(String)returnCodeText.toObject(parmList[3].getOutputData());
    
            }

        }catch(Exception e){

            e.printStackTrace();

        }
      
        return new InquiryResult(counter.incrementAndGet(),
                sysId, sysName, sysStatus, sysReturnCode);

    }
    
    /**
     * Method date maps the /date url. When user call this API, it will
     * invoke AS400 program IMODULE/DICBSYMD and return the result to
     * user.
     *
     * @return A DateResult object that contains the date
     */
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

            // Run and get the result
            if (program.run()!= true){

                // Program run fail
                AS400Message[] messagelist = program.getMessageList();
                for (int i = 0; i < messagelist.length; ++i){
                    System.out.println(messagelist[i]);
                }

            }else{

                // Program run success
                result = toInt(parmList[0].getOutputData());

            }

        }catch(Exception e){

            e.printStackTrace();
            result = -1;

        }
        
        return new DateResult(counter.incrementAndGet(), result);

    }
    
    /**
     * Method toInt translates AS400 program return value to a java int
     * variable. 
     *
     * @param bytes a list of byte 
     * @return int 
     */
    private static int toInt(byte[] bytes) {

        int result = 0;

        for (int i=0; i<8; i++) {
            //Packed decimal consum ceiling((x+2)/2) bytes for a x digit number 
            //and it start with 4 bit 0 and end with 4 bit 1
            int index = (i+1)/2;
            int isHigh = i%2;
            int value = isHigh==1?((int)bytes[index]>>4 & 0x0F):((int)bytes[index]& 0x0F);
            result = result * 10 + value;
        }

        return result;

    }
    
}
