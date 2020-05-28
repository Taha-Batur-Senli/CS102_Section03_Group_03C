g3C -Reserved-
-----------------------------------------------------------------------------------------------------------------------------------
DESCRIPTION: This app designed to help both customers and restaurants by making fast, efficient and reliable reservations anytime.
-----------------------------------------------------------------------------------------------------------------------------------
PROJECT'S CURRENT STATUS: Completed!
-----------------------------------------------------------------------------------------------------------------------------------
WHAT WORKS:
	-Authentication works succesfully.
        -Reservation making process works.
        -Customer's features such as adding money, rating past reservations, editing account... works.
        -Restaurant owner's features such as changing menu, adding promotion... works.
-------------------------------------------------------------------------------------------------------------------------------------
WHAT DOES NOT WORK: 
	-Little bugs occur rarely, yet they do not effect the bulk of the project.
------------------------------------------------------------------------------------------------------------------------------------
WHAT REMAINS TO BE DONE: 
	-Delete option for deleting the added images by restaurant owner's, customer's reservation points/ranking
-------------------------------------------------------------------------------------------------------------------------------------
	-Berkay İnceişçi: I worked on reservation process and I was responsible for making our app time sensitive
	-Ege Ergül: I worked on manuplating the data on Firebase, connecting it to our app, and I also worked on reservation objects. 
	-Bora Altınok:I worked on connecting database to Android Studio ,and creating the basic skeleton of our project.
	-Ali Eren Günaltılı: I worked on image uploading mechanism and built a connection between Firestorage and Firebase.
	-Arda Tavusbay: I created our design and Adobe XD, and then implemented it to our Android Studio project and worked on           	  integrating xml files to java classes.
	-Taha Batur Şenli: I worked on our app's GUI and apperance, mostly spending time on XML classes and event listeners.
-------------------------------------------------------------------------------------------------------------------------------------
Softwares: Android Studio 3.6, java8.0, SDK version 26, Firebase, GitHub
Implementations: minSdkVersion 26, targetSdkVersion 29, compileSdkVersion 29.
Libraries used: Firebase's libraries for connecting our project to relevelant pieces of Firebase such as 
implementation 'com.google.firebase:firebase-storage:19.1.1'
implementation 'com.google.firebase:firebase-analytics:17.4.1'
implementation 'com.google.firebase:firebase-database:19.3.0'
implementation 'com.google.firebase:firebase-auth:19.3.1'
Also we used one additional library for picture adding machinism: 
implementation 'com.squareup.picasso:picasso:2.5.2'
------------------------------------------------------------------------------------------------------------------
Instructions for setup:
Prerequisite: Make sure that you installed AndroidStudio and Emulator (Pixel 2 is recommended) .
	1- Unzip our project.
	2- Open the project in Android Studio.
	3- Click green triangle(run button) to run the project with your emulator.

PS: If you recieve "Gradle Build failed" error. 
To fix it:
	1- Open Gradle Scripts > settings.gradle
	2- Comment out "include':app'"(like this //include':app'). 
	3- Sync the project.
	4- Uncomment it.
	5- Sync the project again.
	6- Run the project.
---------------------------------------------------------------------------------------------------------------------------

