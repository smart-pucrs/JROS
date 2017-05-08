// Agent turtlebot in project jasonros

/* Initial beliefs and rules */

/* Initial goals */
!connectto("127.0.0.1","11311").

/* Plans */

+!connectto(IP, Port)
	<-	jros.config(IP,Port,"arobot");
		//!createNodes.
		!subTest.
	
-!connectto(IP, Port)
	<-	.print("Connection error. Trying again...");
		.wait(1000);
		!!connectto(IP, Port).

+!createNodes
	<- jros.addPubTopic("/cmd_vel_mux/input/teleop","geometry_msgs/Twist","0,0,0,1,2,3");//linear = (0,0,0) / angular = (1,2,3)
		jros.createPubNode("mynode",500).
		
+!subTest
	<- jros.addSubTopic("testtopic","geometry_msgs/Twist");
		jros.createSubNode("mynode");
		!getData.
+!getData
	<- 	jros.getTopicData("testtopic",L);
		for(.member(X,L)){
			.print(X);
		}.
-!getData
	<- !!getData.