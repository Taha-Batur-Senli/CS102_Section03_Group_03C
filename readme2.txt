g3C -RESERVED-
-----------------------------------------------------------------------------------------------------------------------------------
DESCRIPTION: This app designed to help both customers and restaurant owners by making fast, efficient and reliable reservations anytime.
-----------------------------------------------------------------------------------------------------------------------------------
PROJECT'S CURRENT STATUS: Completed!
-----------------------------------------------------------------------------------------------------------------------------------
WHAT WORKS:
	-Authentication works successfully.
        -Reservation making process works.
        -Customer's features such as adding money, rating past reservations, editing account e.g works.
        -Restaurant owner's features such as changing menu, adding promotion e.g works.
-------------------------------------------------------------------------------------------------------------------------------------
WHAT DOES NOT WORK: 
	-Little bugs occur rarely, yet they do not effect the bulk of the project.
------------------------------------------------------------------------------------------------------------------------------------
WHAT REMAINS TO BE DONE: 
	-Delete option for deleting the added images by restaurant owner's, customer's reservation points/ranking
	-The view of our app's GUI differs from phone to phone that creates an aesthatic problem (doesn't affect the
	functions), therefore, enhancing the app's GUI so that it stays the same on all phones.
-------------------------------------------------------------------------------------------------------------------------------------
	-Berkay İnceişçi: I worked on reservation process and I was responsible for making our app time sensitive
	-Ege Ergül: I worked on manuplating the data on Firebase, connecting it to our app, and I also worked on reservation objects. 
	-Bora Altınok:I worked on connecting database to Android Studio ,and creating the basic skeleton of our project.
	-Ali Eren Günaltılı: I worked on image uploading mechanism and built a connection between Firestorage and Firebase.
	-Arda Tavusbay: I created our design and Adobe XD, and then implemented it to our Android Studio project 
	and worked on integrating xml files to java classes.
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
Prerequisite: Make sure that you installed AndroidStudio and its Emulator (Pixel 2 is recommended).
	1- Unzip our project.
	2- Open the project in Android Studio by selecting our project folder.
	3- After it opens, click the green triangle(run button) to run the project with your emulator.
	
	----------------------------------------------------------------------------------------------------------
	How to add emulators:
	When Android Studio is first installed it should be asking you for preferred emulator. However, you can also add emulators manually. To add an emulator,
	1- Go to the Tools -> AVD Manager
	2- Press "Create virtual device"
	3- Select Pixel 2 and click next
	4- Select R (should already be selected) and click next
	5- Click finish and wait for its setup.

PS: Hopefully there shouldn't be any errors when you try to open the project, but if you encounter any problems, these might
be due to the path differencies on our different computers. Therefore we will also explain how to solve these problems if encountered.

        If you recieve "SDK location not found!" error; 
To fix it:
	1- Open Gradle Scripts > settings.gradle,
	2- Comment out "include':app'"(like this //include':app'), 
	3- Sync the project (A blue row pops-up on top),
	4- Uncomment the line in step 2,
	5- Sync the project again,
	6- Run the project.
	
	If you recieve "JSON files collided!" error; 
To fix it:
	1- Go to Build > Clean Project,
	2- Re-run the project.
---------------------------------------------------------------------------------------------------------------------------

