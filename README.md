Educational Project Epam University Program. Java 2018

Task: 

Conference Management System. There are roles:
Administrator, Moderator, Speaker and normal User.
The moderator can fix, offer, change the theme of the report for
Speaker, as well as adjust the time and place of the event.
It is necessary to take into account the possibility of viewing past / future rallies.
Each speaker has its own rating, depending on the rating
more bonuses are accrued. The Speaker may propose his Report.
There should be statistics of registered people and how much physically
total came to the Report. Implement notification of participants about
upcoming events.

Recomendations to app running:

To run this application You need install jdk 8, apache maven, apache tomcat, mysql 5.6.

Create schema 'fp_db' in Yours database. Run scripts from 
resources/scripts : fp_db_create.sql, then starting_filling.sql. Then You have 
2 ways : 1 - open Intellij IDEA or else IDE, open project and run it with green button. 
2 way : pack war with maven, deploy it to tomcat server. run tomcat, use application.
