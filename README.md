wfm2013
=======

WMPM

First steps after deploying the project in Jboss 7:

0. Download Fox platform here: https://app.camunda.com/confluence/display/foxUserGuide/Getting+Started (see jboss config: standalone.xml)
1. go to http://localhost:8080/explorer and login with kermit/kermit
2. click on 'Verwalten' and then on 'Benutzer'
3. click on 'Benutzer anlegen' and fill in the data 
4. click on 'Gruppen' and then on 'Gruppe anlegen'
5. create 2 groups (Trainer & Member), make sure to use capital letters at the beginning 
6. create 3 groups for the sports center membership types (Bronze, Silver, Gold)
7. assign group members by clicking on the "+" symbol 
(either Trainer or Member and if Member add Bronze, Silver and/or Gold; Gold doesn't automatically include Bronze and Silver, 
furthermore assign a user to the admin group, he will get an error notification via email if Twitter posting doesn't work)

--- verifying the h2 (optional)
8.  login with url: jdbc:h2:./fox-h2-dbs/fox-engine    username: sa pw: sa
9.  Run SELECT * FROM ACT_ID_USER 
10. Make sure the users you just created are there
11. Run SELECT * FROM ACT_ID_GROUP (Trainer, Member, etc. should exist now)
12. Run SELECT * FROM ACT_ID_MEMBERSHIP 
13. make sure the user you created (or existing ones) have a group ID of either Trainer or Member and if Member either Bronze, Silver or Gold
14. Open http://localhost:8080/sportcenter and login with user/pw

--- Twitter Account
https://twitter.com/sccms


--- Weather Service
adding libraries to build path with maven is just a mess so please install the library locally by 
executing the command from basedir of the project

mvn install:install-file -DgroupId=org.fedy2 -DartifactId=YahooWeatherJavaAPI -Dpackaging=jar -Dversion=0.0.1-SNAPSHOT -Dfile=lib/YahooWeatherJavaAPI-0.0.1-SNAPSHOT.jar -DgeneratePom=true
