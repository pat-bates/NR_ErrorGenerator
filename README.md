# Error Generator Sping Boot Applictions
A series of code utilities that generate random Java errors.

This code is a variation/offshoot of the OverOps Error Generator:  https://github.com/takipi-field/ErrorGenerator

The purpose of this modification is to support automated testing through a READ API.  A number of ErrorGenerator calls have been exposed through SpringBoot to allow simple automated testing through scripting or test tools such as jmeter.

The application deploys as a .war file and runs under tomcat.

The test utility can be used to drive a particular mix of arguments:
 - Number of concurrent threads/users
 - Error Rate as a percentage
 - Test Duration in seconds
 - Transactions Per Minute (TPM)

The tests can be run individually or by using a provided Jmeter job and accompanying script.

## Deploying Error Generator Sping Boot App using Tomcat

You can use the .war in the target folder:  ErrorSpringApp.war, or you build it from scratch using maven.

### To Build it from scratch

mvn clean package 

### To Deploy the .war file under tomcat

Copy the .war file:

cp ./target/ErrorServlet.war /opt/tomcat/webapps/.

Restart tomcat, e.g., 

<tomcat-dir>/bin/shutdown.sh
<tomcat-dir>/bin/startup.sh

## To Run the Error Generator Sping Boot App

### In a browswer

Enter the following URL in the broswer to generate errors one at a time:

<tomcat host>:8080/ErrorSpringApp

Select the type of error and click "Submit".

### REST API

Use the following URL to execute a single error:

<tomcat cat>:8080/ErrorSpringApp/InvokeError?errorType=<error>

Where <error> is one of
  NoError
  NullPointerException
  NumberFormatException
  ParseException
  UncaughtException

Scripts can be created using curl, e.g., 

curl ${test_host}:8080/ErrorSpringApp/InvokeError?errorType=NullPointerException

## Testing with JMeter

Make sure the JMeter executable is in the PATH:

export PATH=$PATH:/opt/jmeter/bin

A JMeter job is included in the scripts folder that executes the Error Generator Spring Boot App
with the following characteristics driven by variables:

HOST       :  The tomcat server host name
URLPATH    :  The application and command (InvokeError)
DURATION   :  Length of the test run in seconds.
              This value must have a single decimal place, e.g., 60.0
NUM_THREADS:  The number of concurrent test threads, simulating concurrent users
ERR_RATE   :  The percentage of error transactions (0 - 100)
TPM        :  The number of transactions per minute.  JMeter will throttle accordingly.

The command line to execute the JMeter job will look something like this:

jmeter.sh -n -t OverOps_Spring_Boot_Test.jmx \
                                -Jnum_threads=$nt \
                                -Jno_err_rate=$no_er \
                                -Jerr_rate=$er \
                                -Jtpm=$tpm \
                                -Jhost=${HOST} \
                                -Jpath=${URLPATH} \
                                -Jduration=${DURATION}


### Test with the JMeter script

A script file run_jmeter.sh is provided in the scripts folder as well.

This script will run a sequence of tests with variable settings for 
NUM_THREADS, ERR_RATE and TPM.

Edit the file to set the arguments and arrays as desired for a particular test.


