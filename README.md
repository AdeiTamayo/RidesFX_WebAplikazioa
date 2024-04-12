# Resume
This Readme file is a resume of the SE1 ShareTrip project. 
It contains the following sections:



# ITERATION 1
### We implemented the following things:
- Implemented requrements analysis: Domain Model + Use Case Diagram + Event Flow
- Changed project layout to BorderPane layout
- Implemented the register and login use cases

---
# ITERATION 2
### Corrected errors from Iteration 1:
- Corrected Login and Register and User added correctly to database
- Corrected text field's order aftter hitting TAB 
- Current user label updated after logging in
- Requirements Analysis artifacts
  
### Implemented new things:
* The following things were mandatory:
  * Implemented User/Driver/Traveler hierarchy
    * Added 2 more use cases: Create Alert and Book Ride 
    * The application is opened as a not logged in user which can query rides but must log in in order to do anything else. 
    * Changed the available buttons depending on user type. 
    * Created sequence Diagram for "Query Rides" use case.
    * Added three new event flows: Create Alert, Query Alert and Remove Alert.
    
* The following things were optional:
  * Created 'Clear' button: clears the text fields.
  * Implemented 'Return' button: returns to the main GUI. 
  * Created a window to display and delete alerts, and if a matching ride is found the option booking the ride.
  * Ride date appears on the top corner when date is selected.
  * Created home GUI.


### Iteration 2 struggles
The sequence diagram was a reality check for the app implementation. 
* Major changes had to be done on the application flow, such as new intercommunications between differents UIs.

 ---
 
