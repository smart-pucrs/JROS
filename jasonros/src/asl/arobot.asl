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
	<-	jros.createSubNode("mysnode","/testestr","std_msgs/String");
		jros.createPubNode("mynode", "/testestr", "std_msgs/String", 500);
		!!setdata.

+!setdata
	<-	jros.setTopicData("mynode", "lalalala");
		!!getData.
	
+!getData
	<-	jros.getTopicData("mysnode",S);
		.print("String:",S);
		.wait(100);
		!getData.
		
-!getData
	<- !!getData.
		