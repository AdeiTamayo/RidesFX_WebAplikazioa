# RidesFX: a JavaFX (modular+hibernate+h2+properties) desktop application project 

A JavaFX+Hibernate+H2+JUnit template for the Software Engineering I class of the Faculty of Computer Science of Donostia. 


* First, you need to create some Java POJO entities in the domain folder
* Then, you can map those entities to an H2 database: 
  * In the hibernate.cfg.xml file, edit this line:
  
           <property name="hibernate.hbm2ddl.auto">update</property>

    Change the value to "create" to create the database from scratch, or "update" to update the database with the new entities.
  * In the hibernate.cfg.xml file, edit this line:
  
           <property name="hibernate.connection.url">jdbc:h2:~/database</property>

    Change the value to the path where you want to store the database. In this case,
  the database will be stored in the home folder of the user.

* Rename the package (from eus.ehu.ridesfx to eus.ehu.XXXXX where XXXX is your package name)
* Rename the module (from template to XXXXX where XXXX is your module name)
* You may need to change the controller path in the hello-view.fxml file
* Remember that, by default, the open mode of the db is `initialize` (this property is set in the `config.properties` file. 
Change it to `update` if you want to keep the data between executions

------------
ITERATION 1
 Implemented requrements analysis: Domain Model + Use Case Diagram + Event Flow
 Changed project layout to BorderPane layout
 Implemented the register and login use cases

 -----------
 ITERATION 2
 -> Corrected errors from Iteration 1:
    -> Corrected Login and Register and User added correctly to database
    -> Tab order on menu
    -> Current user label updated after logging in
    -> Requirements Analysis artifacts
 -> Implemented new things:
    -> Implemented User/Driver/Traveler hierarchy
    -> Added 2 more use cases: Create Alert and Book Ride
    -> The application is opened as a not logged in user which can query rides but must log in in order to do anything else.
    -> Changed the available buttons depending on user type
    -> Created a window to display and delete alerts, and if a matching ride is found the option booking the ride
    -> Created sequence Diagram for "Query Rides" use case

 -> STRUGGLES: the sequence diagram was a reality check for the app implementation. Major changes had to be done
 
