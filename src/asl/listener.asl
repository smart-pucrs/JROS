/* Initial beliefs and rules */

/* Initial goals */
!connect_to("127.0.0.1","11311").


/* Plans */
+!connect_to(IP, Port): .my_name(Me) & .term2string(Me,Me_s)
	<-	jros.config(IP,Port,Me_s,"topics.jros");
		.wait(2000);
		!!listen.

-!connect_to(IP, Port)
	<-	.print("Connection error. Trying again...");
		.wait(1000);
		!!connect_to(IP, Port).

-!listen
	<-	!listen.

+!listen
	<-	.wait(1000);
		jros.recvData("listen", S);
		.print("Listener: ",S);
	    !listen.