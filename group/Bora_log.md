# CS102 ~ Personal Log page ~
****
## Bora Altınok
****

On this page I will keep a weekly record of what I have done for the CS102 group project. This page will be submitted together with the rest of the repository, in partial fulfillment of the CS102 course requirements.

### ~ 28 April 2020 ~
We began to design our project. I started look at android studio. After i collected some information. I began to write my first android project. I wrote pretty easy app. Just a button which changes the current activity.

### ~ 4 May 2020 ~
Ege and I started to design the register and log in part of our app. We begin to learn firebase too. After some research, i did the first register which uses Authentication from Database. We succesfully created new users and stored them in database with their e mail addresses and passwords. But we wanted to add new users with their other custom field such as user's phone, id, rezervation count, money. But the challenging part was connecting authantication with firebase database. I studied it.
### ~ 5 May 2020 ~
I succesfully registered the user with custom fields for the first time. Authantication was connected to firebase realtime database from now on. I created a new project to see if i can design a menu with add and delete options. This mini project helped us a lot to create our group project's menu. Ege and I  started to study for editing accounts after they are created.

### ~ 6 May 2020 ~
We spotted a bug when trying to add more than one food to menu. We were not storing the food as an object. I assumed that not storing the foods as objects created this problem. I again created a mini project to see if this was the problem. I created another menu but this time it stores the food as an object. I quickly find out that this was the problem. I told Ege about it. And we changed the code. So now, the code was storing the foods as an objects in the real time database. This time the GitHub started to create some problems. The codes that we pulled from github started to crush. We started to work on it.

### ~ 8 May 2020 ~
As we store the restaurant in our database. Problems started to occur. The registers became inconsistent(Sometimes it authenticated the user, sometimes it did not) I  realized that as we did the same thing when storing food. We did not created restaurant as an object. So we were storing all of restaurant's custom fields individually. This was a lot of unnecessary code and also caused the firebase to crush. So Ege and I wrote a basic restaurant class with properties and set get methods. And it started to work properly.
### ~ 13 May 2020 ~
I can not run the app because my phones sdkVersion can not match with the new sdk version( a new method and libraries that we added forced it to update the sdk version). 
### ~ 17 May 2020 ~
I have been working on to fix the sdkVersion problems for few days but unfortunately i have gotten to a point that the only point to fix this problem is changing my phone. I added search view that helps user to find the restaurant that he/she wants. It searches through chars and lists the restaurants that have the chars that user typed. Also user still can press on it to view the restaurant's homepage. I searched to find how to add icons to app. I came up with one logo and added it to our project.

****
