// Agent turtlebot in project jasonros

/* Initial beliefs and rules */

/* Initial goals */
!connectto("127.0.0.1","11311").

/* Plans */

+!connectto(IP, Port)
	<-	jros.config(IP,Port,"arobot");
		!createNodes.
		
-!connectto(IP, Port)
	<-	.print("Connection error. Trying again...");
		.wait(1000);
		!!connectto(IP, Port).
		
+!createNodes
	<-	//jros.createSubNode("mysnode","/testestr","std_msgs/String");
		jros.createPubNode("mynode", "/testeint", "std_msgs/Int32", 500);
		.wait(1000);
		!!turtle.

+!turtle
	<-	//jros.getTopicData("mysnode",S);
		jros.setTopicData("mynode", 123);
		.print("String:",S).
		
-!turtle
	<- !!turtle.
		