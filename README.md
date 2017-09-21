# Lunar Note Web Application Demo

This is a trial project that demostrates how to build an restful webapp that requests AS400 API resource. 

## Project structure introduction

The source includes a static html page(`${project-root}/src/main/resources`) and a java restful webservice(`${project-root}/src/main/java`). 

In the html part, it simply uses JQuery to launch a restful webservice consumption. 

In the java package, spring-boot is used and the configuration is defined in class `com.bcm.app.App`. The request url mapping and AS400 API resource requesting logic are defined in class `com.bcm.app.CentralController`. 3 kinds of restful api result are defined as `com.bcm.app.DateResult`, `com.bcm.app.LoginResult` and `com.bcm.app.InquiryResult`.  

## How to build 

This is a __maven__ project so build tool *mvn* is required for build. Change directory to project folder and run below command:

    ```
    mvn clean package
    ```

Now that a war package is built in the target folder, __deploy__ it to your tomcat/jboss server. When the restful webapp is ready, visit the webapp's index page and a simple inquiry function would be available.

## Trouble shooting

* If your can visit the index page but no result is returned after clicking the inquiry button
    
    If there is a json response but the fields are empty, its probably because that your enquiry id does match any records on AS400, simply input another id to try again; If there is no json responses, shoot the trouble following steps:
    
    1. Open the developer's tool provided by the browser and reload the page, make sure if `Jquery.js` is loaded successfully.
    1. Verify the AS400 user token is valid (the token can be found in class `${project-root}/src/main/java/com/bcm/app/CentralController.java`)
    1. Verify the AS400 program exists and the library list is well arranged. 
    1. Verify the AS400 user id is authorized to run the program.
    1. Verify the AS400 program's integrity

* If you don't know where to access the index page 

    Its usually the address of the web server, plus a slash before the war package name. But please do refer to your web server's setting. 
