# trabeya task
Name   : Lahiru Wishwa Gunarathne

Email  : lahiruwishwa10@gmail.com

Mobile : 0718888148
 
This repository contains the source code solution for the Trabeya SE task.

# Notes: 

# core_bank

com.lahiru.corebank.gen.* classes will report compile-time errors in your IDE unless you have run the task to generate the domain classes based on the WSDL.
(no issue for launch jar file)

* to remove those compile errors run

      mvn compile

      or press mvn complie button in IntellijIDEA

* build jar

      mvn clean install

* default port : 8686
         
      (application.properties -------> server.port=8686)

# integrator

com.lahiru.integrator.wsdl.* classes will report compile-time errors in your IDE unless you have run the task to generate the domain classes based on the WSDL.
(no issue for launch jar file)

* to remove those compile errors run

       mvn compile

       press mvn complie button in IntellijIDEA

* build jar

       mvn clean install

* default port : 8787

      (application.properties -------> server.port=8686)



mysql configurations
