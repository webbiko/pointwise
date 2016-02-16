# Pointwise

This application simulates a producer/consumer pattern and it was developped to PointWise.
In its archtecture it was used alarm manager to run services periodically in order to push new user data to priority queue and pull them as well.

Between the services and implementation of Handler was used to exchange queue data between producer/consumer. The running time of each service is statically located at Constants.java but it should be done in a portal web for real applications.

A simulation of unreliable network was done and at every 10 times it is expected that 2 fails and the data wich was polled from queue is sent back to queue. The consumer is using alarm manager to periodically run the synchronization with UI but in real application SyncAdapter would fit better since it take care of important system resource(like battery life). Starting from android 5.0 a JobScheduler is recommended for this kind of synchronization but unfortunatelly it is not available in support library.

In this POC I did not focus on storing queue information into database since I believe it was not the main goal of this test but in case not losing any user data It would be stored in sqlite instead of keeping data into memory. This would avoid application force stop.

If the user force stop this POC the alarm manager will be reestarted and the queue will be populated again since alarm manager does not depend the app to be running in order to work and the return of service riquires that last intent be redelivered. An event of boot complete could be listened to in order to starts the alarm manager as well but it was not worried in this case.

For priority queue a class named UserData.java was created with following attributes: data, weight, date. Seens that priority queue should order its data by weight I decided to use date/time as tiebreaker. So, if the queue receive repeated 10 weight as input the order of data that will be picked will be based on date/time of each one has reached the queue.

When running application you will notice that queue size may be zero since the inteval of time for each service are very close but after a minute the queue size will be increated and the consumer will not be able to accompany the producer. In a real application the user data would be pagined and sent to server as json format then deleted from local datastore if it returned 200K.

For data displayed on screen you will noticed that a weight is also displayed> eg.: [8] Pointwise.
** An overview of how application is architecture may be found at application root directory (Pointwise_Archictecture.png)
