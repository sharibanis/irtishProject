# irtishProject
irtishProject

Displays the weather results in a table. 

Stores the data to MongoDB as forecast of the day, and populates the record to UI using thymeleaf (https://www.thymeleaf.org/
). 

Uses Bootstrap for layout. 

All proceses are logged using log4j. 

Subsequent calls to display the weather results will check whether the today forecast record exists in database before triggering 
another REST API call to Darksky. 

Housekeeps forecast records that already 3 days old.

Run the program using ``mvn clean; mvn compile; mvn tomcat7:run``
