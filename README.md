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

* assigned port : 8686
         
      (application.properties -------> server.port=8686)

# integrator

com.lahiru.integrator.wsdl.* classes will report compile-time errors in your IDE unless you have run the task to generate the domain classes based on the WSDL.
(no issue for launch jar file)

* to remove those compile errors run

       mvn compile

       press mvn complie button in IntellijIDEA

* build jar

       mvn clean install

* assigned port : 8787

      (application.properties -------> server.port=8787)



** mysql configurations

       application.properties
       
            spring.datasource.url = jdbc:mysql://localhost:43370/trabeya_integration
            spring.datasource.username = root
            spring.datasource.password = root@@

create trabeya_integration schema manually on your own mysql database and change above configurations to match your local mysql db.

            or you can use in memmory h2 database by commenting mysql properties in application.properties file, mysql dependencies in pom.xml file
            and uncommenting h2 properties in application.properties file, h2 dependencies in pom.xml file
