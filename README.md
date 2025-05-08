# TEAM-36
A website for car services, car insurance and car resale.
This is a Local Based Project.




code structure :-
car-services/
├── pom.xml
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── team36/
│       │           ├── model/
│       │           │   ├── Booking.java
│       │           │   ├── Car.java
│       │           │   ├── CarResale.java
│       │           │   ├── InsurancePlan.java
│       │           │   ├── Main.java
│       │           │   └── ServicePackage.java
│       │           ├── servlet/
│       │           │   ├── InsurancePurchaseServlet.java
│       │           │   ├── InsuranceServlet.java
│       │           │   ├── ResaleServlet.java
│       │           │   ├── ServiceBookingServlet.java
│       │           │   └── ServiceServlet.java
│       │           └── util/
│       │               └── DatabaseUtil.java
│       ├── resources/
│       │   └── META-INF/
│       └── webapp/
│           ├── index.html
│           ├── insurance_purchase.html
│           ├── insurance.html
│           ├── resale_list.html
│           ├── resale.html
│           ├── service_booking.html
│           ├── service.html
│           ├── css/
│           │   ├── insurance_purchase.css
│           │   └── style.css
│           ├── js/
│           │   └── script.js
│           └── WEB-INF/
│               └── web.xml



To run this project you need apache maven, tomcat, Mysql and VsCode.

1)First place all the files in above order.
2) run sevices such as Mysql80 and tomcat server
3) open command prompt where pom.xml files exists.
4) use command mvn clean install
5) a WAR file is created which is saved in folder named Target in the same place where pom.xml and src folder exist.
6) Copy war file and paste in tomcat webapps folder.
7) http://localhost:8080/car-services-1.0-SNAPSHOT to access the website.
8) Note:- make sure the SQL DB Url and User name, Password match to your own Local MySQL credentials.

Video can be seen on how to build and deploy the project.
Video link :- https://youtu.be/iKeXOCPC_fw



Now, 3 Minute video link represent presentaion.
Video Link :- https://youtu.be/VxKgbSl4BPI.
