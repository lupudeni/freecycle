# FreeCycle

#### Final project for "The Informal School of IT - JAVA Development"

FreeCycle is an app that is used for donating objects. The purpose of the app is to centralise and organise the transactions between donor and requester and serves a platform for establishing connection between people.

#### Application main flow
The user can act both as a donor and as a receiver depending on the options they choose.

Users log in and post objects from different categories that they would like to donate to someone else. 
Other users can search for objects based on category, title (keyword) and area of availability. If they would like to get the donation they request it. When a request is made, the user who requests the donation is placed in a list with max. 5 users. The donor can then pick from that list the user who will receive the donation.
After a receiver is picked by the donor, the app sends an e-mail to the receiver with the phone number of the donor, so that contact can be established.
The item is then classified as "donated" and will not appear in searches anymore.

Users can edit their own donations, see a list of past donated objects, see a list of active donations (that have not been donated yet) and see a list of donations that they requested.
When posting a donation, the user is required to specify a category, title, description, area of availability and to load photos.

#### Application requirements

##### Data Source
Provide the following environment variables:
* `dataSourceUserName` 
* `dataSourcePassword`

##### DB Schema
In order for Flyway to be able to create the DB, make sure you have a `freecycle` schema created.
Note that this app uses MySql 8.

##### Email
In order to use the email sending functionality, provide the following environment variables:
* `mailUserName` - the gmail SMTP username 
* `mailPassword` - the app password for the gmail SMTP user (How to obtain an app password: https://support.google.com/mail/answer/185833?hl=en-GB)


#### Open API 3 Documentation

The application exposes a Swagger UI at http://localhost:8080/doc/swagger-ui.html