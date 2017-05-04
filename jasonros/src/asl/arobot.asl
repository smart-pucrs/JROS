// Agent arobot in project jasonros

/* Initial beliefs and rules */

/* Initial goals */
!connectto("127.0.0.1","11311").

/* Plans */
+!connectto(IP, Port)
	<-	jros.config(IP,Port,"arobot");
		!receivePerceptions.
	
-!connectto(IP, Port)
	<-	.print("Connection error. Trying again...");
		.wait(1000);
		!!connectto(IP, Port).
		
+!receivePerceptions
	<-	jros.listenPerceptions.
		//.wait({+lala}).
