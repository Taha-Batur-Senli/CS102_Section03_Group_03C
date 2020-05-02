# CS102 ~ Personal Log page ~
****
## Berkay İnceişçi
****

On this page I will keep a weekly record of what I have done for the CS102 group project. This page will be submitted together with the rest of the repository, in partial fulfillment of the CS102 course requirements.

### ~ 28.04.2020 ~
Today, I thought a bit on outline of our project and made some additions-substractions to the scheme prepared by Ege. Then, I created user-costumer-restaurant owner classes and put some methods into them. In our app, customers and restaurants need to be ordered depending on their points or ratings for the reward purposes. At first, sorting all customers and restaurants whenever getBestCustomers or getBestRestaurants is called seemed appropriate and therefore I wrote these methods using quick sort. Then, I thought it would be better practice to update the place of customers or restaurants whenever their points or ratings are incremented. So I updated increasePoint method in the Customer class so that it updates the leaderboard whenever a customer makes a reservation and has an increase in their points (May seem simple, but it took approximately 3 hours to think and implement it). I will implement same approach to the restaurant rating system. Other than that, I'm still on the process of learning the basics of android studio and firebase.

### ~ 01.05.2020 ~
Today, I worked on reservation system. When a customer reserves a particular time slot from a particular restaurant, another customer should not be able to do so. For now, I represented times as Strings and stored all timeslots as an arraylist in the calendar object (which is a property of seat class- each seat should have their timetables), letting them to be selected by customers. I managed to prevent another customer to reserve a reserved seat, but there is another concern. For instance, let a customer reserve a specific table at 8:00 PM. By default, this table becomes reserved for him for 1.5 hours and nobody else can access to it. After 1.5 hours the table should become accessible (unreserved) again which requires to update system regularly in order to compare the end time of reservation to the actual local time. For that updating purpose, threads seem rational to utilize; I will try to implement threads to main method so that tables become accessible automatically. Additionally, instead of representing dates and times as Strings, representing them as Date objects and formatting them by DateTimeFormatter might be more reliable. In the next days, I will focus on Date and DateTimeFormatter classes and threads, trying to complete the reservation process.

### ~ 02.05.2020 ~
Today, changed the time representations from String to LocalTime objects and instead of having timeslots inside an arraylist, timeslots are now stored in Hashmap collection. Each specific time of the day is accessible by its minute representation. For example 15:10 - 16:40 timeslot's key is 15.60 + 10. Doing so apparently will not cause any problem with times. However, same approach cannot be implemented to the dates because each year or each month contains different amount of days. Therefore, In my opinion- I do not know what is the better way of doing it-, dates might be compared after they are represented in Strings. For now, customers can choose the time they want to reserve. Moreover, I learned how to use threads in order to compare real time to reservation end time. Tomorrow, I will hopefully include choosing date part to the reservation process. 


****
