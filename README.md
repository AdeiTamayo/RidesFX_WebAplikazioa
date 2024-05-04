# Abstract 
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
 # ITERATION 3
 ### Corrected errors from Iteration 2:
 - Minor fix on Query Rides sequence diagram
 - Fixed the create ride GUI to implement combo boxes for the city selection and Spinners.

### Implemented new things:
This iteration is a bit different from the previous ones. We made the following changes:
* The following changes were mandatory:
  * Implement (at least) a new feature
    * We chose the new feature to be: the accept reservation feature for the driver.
  * Prepare a presentation for the final version of the project
 
* Alongside the mandatory implementations we added the following which were optional:
  * New use case: Query reservations
    * Both traveler and driver users can access a list of their reservations, check the state of the reservations and       delete them.
  * New user case: Create a ride from the Alert ui.
    * The same ui as in the normal AlertsView is displayed but the driver has the option of creating a ride based on 
      an existing alert.
  * Improve query alerts feature: Add the option of displaying all different prices for an alert with a matching ride 
    and book the ride with the selected price.
  * Query data using API: Retrieve city info from the internet.
  * Added functionality to remove the success messages after certain time.

### Iteration 3 struggles
The main struggle was to implement the API to retrieve city information.
* The API was not working as expected, and we had to change the API to a different one.

We also had problems with the Location domain, we had to change the way we were storing the location information and retrieving it.
## How to test the application

There are two users already created for practical purposes:

- **Driver:**
  - **Email:** driver@
  - **Password:** 1

- **Traveler:**
  - **Email:** traveler@
  - **Password:** 1

### Notes on City Info API Testing

When testing the City Info API, please keep the following points in mind:

- The API primarily selects the biggest cities. Therefore, not all cities may appear in the results. For example, Tolosa might not be listed.
- The API is capable of detecting the name of the city in various languages. However, for some cities, this feature may not work as expected. For instance, Iruña might not be recognized correctly.
- There's a limit for the amount of queries possible, due to the API's own limit.
