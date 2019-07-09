# Error Generator
A series of code utilities that generate random Java errors. The idea is to build different classes and then use class for name to randomly call them

## Using Error Generator
The main method is in com.overops.ErrorGenerator. It takes in two parameters (length of run and error types)
I tend to run the code from my IDE (add -agentlib:TakipiAgent to VM arguments in your IDE) or you can create a JAR file by navigating to the root of the directory and running mvn clean package (make sure to add -agentlib:TakipiAgent when you invoke java -jar)

## How to add new Error Utilities
In com.overops.errors, there is BaseError interface. Create a new Error class and implement the BaseError interface (see one of the other classes as an example). This class will be added to the list of classes in the main method class listed above (there is a error map in ErrorGenerator). You can classify your error type in order to generate only null pointers or invalid arguments or run all error types. Once you have the new class created, then create the actual code to create the errors in the com.overops.util package. If you look at an existing class, you will see the pattern. 
