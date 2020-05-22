# CS102 ~ Personal Log page ~
****
## Ali Eren Günaltılı
****

On this page I will keep a weekly record of what I have done for the CS102 group project. This page will be submitted together with the rest of the repository, in partial fulfillment of the CS102 course requirements.

### ~ 02.05.2020 ~
This week I learned to implement firebase to my android project by using realtime database. I also designed GUİ's of our projects with our friends on android studio and how to write it on xml's to manipulate the GUI. I also helped my friends to designed basic login and changeable then check the data via firebase.

### ~ 04.05.2020 ~
 These days I and Arda worked on the GUI and we almost finished the basic design except 5 6 page. Also I started study on SQL to better understand the database mechanism. About GUI I learned create recylclerview and create scrollable page. In SQL I succeed to save my first data by creating table and updated the data that I stored. Other than that I realized that I made mistakes when I am making the constrain layout. I have to constrain images and textview related to each other instead of constrain them respect to the parent. 

### ~ 06.05.2020 ~
 After I comprehend the logic of database and SQL I started work on firebase real time database. As I volunteered to do the drag dropping mechanism of our seating plan, I started to think how should I store the drag dropped images into firebase. I succeed to drag and drop a image to another image however it directly fill the whole size of the target image. Therefore, I wasted lots of time to keep the size of the dragged objects and couldn’t do it. After I realize our drag drop mechanism not very convenient and hard to interpret by code, I started to think a more convenient and feasible way. Eventually I found a way to do it. This way is very similar to choosing and uploading a picture to any social media account. This will be useful because restaurants can arrange their seating plan as they want. It also gives them a chance to upload the pictures of restaurants or meals. Simultenaously, I also helped Arda to creation of some GUIs of our project and started to working on some RecyclerViews by creating a class Adapter which represent a every row of the RecyclerView. After few days passed by watching tutorials about firebase database and create the logic in my mind. I have to store the url of the picture to show it on my screen so I have search the method and libraries for it. One library basically call “picasso” is very useful for this request. Picasso has a method to called “Picasso.with(Context).load(Image Url which is going to be loaded).into(target Place)” and this is what I need to show to image on my screen. I take notes about the needed methods and libraries to be used in uploadImage activity and give a break on that.  After that I spend my time on databases and started to working on SQL and firebase. But while I am learning more things about firebase, I realized that we don’t really need to write anything about SQL to store and retrieve data since firebase automatically do that for me. At this point as we are strugling with using gitHub properly I don’t have an direct access to my friends and still don’t know many things how they use the firebase on login sign up mechanism. Therefore, I also searched for the tutorial of building login sign up to decide what should do next to relate the datas each other.
 
 ### ~ 10.05.2020 ~
 In these days we managed to use git properly. Therefore, I have an access to my friends code and by looking Ege and Bora loging mechanism I learned to implementation of FirebaseAuth and FirebaseUser to database. Basically I learned to build a bridge between datas and reach a specific data by referencing the right childs. I also start to design uploadImage activity. The intent that I used in chooser method passed it via “startActivityForResult” as I have to take the picture from B and receive the A. In a overrided OnActivityResult method I take the url of the chosen photo and displays it the choose/upload page. Then when the upload button is pressed save the data to firebase by creating upload object which has two properties url and name. However, I still couldn't manage the retrieve data yet. Other than that I worked on basic things like creating few help classes and dealing with some GUI problems.

 ### ~ 13.05.2020 ~
 
 
 

****
