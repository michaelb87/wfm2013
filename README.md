wfm2013
=======

WMPM

First steps after deploying the project in Jboss 7:

1. go to http://localhost:8080/explorer and login with kermit/kermit
2. click on Verwalten and then on Benutzer
3. click on Benutzer anlegen and fill in the data 
4. click on Gruppen and then on Gruppe anlegen
5. make sure to create 2 groups (Trainer & member)
6. assign groupmembers by clicking on the "+" symbol
--- varifying the h2 (optional)
7. login with url: jdbc:h2:./fox-h2-dbs/fox-engine    username: sa pw: sa
8. Run SELECT * FROM ACT_ID_USER 
9. Make sure the users you just created are there
10. Run SELECT * FROM ACT_ID_GROUP (Trainer & member should exist now)
11. Run SELECT * FROM ACT_ID_MEMBERSHIP 
13. make sure the user you created (or existing ones) have a group ID of either Trainer or member
14. Open http://localhost:8080/sportcenter and login with user/pw
