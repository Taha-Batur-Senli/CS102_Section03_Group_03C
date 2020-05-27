
PROJET GROUP NUMBER:03C
-----------------------------------------------------------------------------------------------------------------------------------------
TITLE:Reserved
-----------------------------------------------------------------------------------------------------------------------------------------
DESCRIPTION:This app designed to help both customers and restaurants by making fast, efficient reservations anytime.
-----------------------------------------------------------------------------------------------------------------------------------------
PROJECT'S CURRENT STATUS: Completed
-----------------------------------------------------------------------------------------------------------------------------------------
WHAT WORKS:
	-Authentication works succesfully.
        -Reservation works.
        -Customer's features such as adding money, rating, editing account... works
        -Restaurant owner's features such as changing menu, adding promotion... works.
-----------------------------------------------------------------------------------------------------------------------------------------
WHAT DOES NOT WORK: 
	-Little bugs that occur rarely and do not effect the bulk of project.
-----------------------------------------------------------------------------------------------------------------------------------------
WHAT REMAINS TO BE DONE: 
	-Delete option for deleting the added images by restaurant owner's, customer's rate/rank
-----------------------------------------------------------------------------------------------------------------------------------------
	-Berkay Ýnceiþçi:I worked on reservation process and I was responsible for making our app time sensitive
	-Ege Ergül: I worked on manuplating the data on firebase, connecting it to our app, and i also worked on reservation objects. 
	-Bora Altýnok:I worked on connecting database to android studio ,and creating the basic skeleton of our project.
	-Taha Batur Þenli: I worked on our app's GUI and apperance, mostly spending time on xml classes and event listeners.
	-Ali Eren Günaltýlý:I worked on image uploading mechanism and built a connection between firestorage and firebase.
	-Arda Tavusbay: I created our design and Adobe XD, and then implemented it to our android studio project and worked on integrating xml files to java classes.
-----------------------------------------------------------------------------------------------------------------------------------------
Softwares: Android Studio, java8.0, SDK version 26, firebase, github
Implementations:minSdkVersion 26
, targetSdkVersion 29, compileSdkVersion 29, 
implementation 'com.google.firebase:firebase-storage:19.1.1'
implementation 'com.google.firebase:firebase-analytics:17.4.1'
    implementation 'com.google.firebase:firebase-database:19.3.0'

    implementation 'com.google.firebase:firebase-auth:19.3.1'
implementation 'com.google.firebase:firebase-storage:19.1.1'

-----------------------------------------------------------------------------------------------------------------------------------------
Instructions for setup:
0:(pre-request)Make sure that you installed AndroidStudio and EmulatorXL2.
	1-Unzip our project.
	2-Open the project in android studio.
	3-Click green triangle(run button) to run the project with your emulator.

NOT: If you recieve "Gradle Built failed" error. 
To fix it:
	1-Open your settings.
	2-Gradle comment include':app'(like this //include':app'). 
	3-Sync the project.
	4-Uncomment it.
	5-Sync the project again.
	6-run the project again.
----------------------------------------------------------------------------------------------------------------------------------------- 

  
