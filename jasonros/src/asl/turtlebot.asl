// Agent turtlebot in project jasonros
/* Initial beliefs and rules */
checkAngle(X,W) :- W > X & W < (X + 0.01).
checkPosX(X,W) :- W > X & W < (X + 0.1).
/* Initial goals */
!connectto("127.0.0.1","11311").

/* Plans */

+!connectto(IP, Port)
	<-	jros.config(IP,Port,"arobot");
		//!createNodes.
		!createNodes.
	
-!connectto(IP, Port)
	<-	.print("Connection error. Trying again...");
		.wait(1000);
		!!connectto(IP, Port).

+!createNodes
	<- 	jros.addPubTopic("/cmd_vel_mux/input/teleop","geometry_msgs/Twist",null);//linear = (0,0,0) / angular = (1,2,3)
		jros.addSubTopic("/odom","nav_msgs/Odometry");
		jros.addSubTopic("/cmd_vel_mux/input/teleop","geometry_msgs/Twist");
		jros.createPubNode("mynode",500);
		jros.createSubNode("mysnode");
		!!plan1.

+!stop
	<- jros.setTopicData("mynode","/cmd_vel_mux/input/teleop",0,0,0,0,0,0).
-!stop
	<- !!stop.
			
+!rotateright(X)
	<- 	jros.setTopicData("mynode","/cmd_vel_mux/input/teleop",0,0,0,0,0,-X).

-!rotateright(X)
	<- 	!!rotateright(X).
	
+!rotateleft(X)
	<- 	jros.setTopicData("mynode","/cmd_vel_mux/input/teleop",0,0,0,0,0,X).
		
-!rotateleft(X)
	<- 	!!rotateleft(X).
	
+!moveforward(X)
	<-	jros.setTopicData("mynode","/cmd_vel_mux/input/teleop",X,0,0,0,0,0).
		
-!moveforward(X)
	<- 	!!moveforward(X).
	
+!movebackward(X)
	<- 	jros.setTopicData("mynode","/cmd_vel_mux/input/teleop",-X,0,0,0,0,0).
		
-!movebackward(X)
	<- 	!!movebackward(X).

+!angle(X,Y) : checkAngle(X,Y)
	<-	!stop;
		+angleok;
		.print("X = ",X," Y = ",Y).
		
-!angle(X,Y)
	<- jros.getTopicData("/odom",L);
		.nth(6,L,A);
		.wait(200);
		!!angle(X,math.abs(A)).
		
+!pos(X,Y) : checkPosX(X,Y)
	<-	!stop;
		.print("parou.");
		+posok;
		.print("X = ",X," Y = ",Y).
		
-!pos(X,Y)
	<- jros.getTopicData("/odom",L);
		.nth(0,L,A);
		.wait(200);
		!!pos(X,math.abs(A)).
		
+!plan1
	<- 	jros.getTopicData("/odom",L);
		.nth(6,L,A);
		!rotateright(0.3);
		!!angle(0.96,math.abs(A));
		.wait({+angleok});
		-angleok;
		!plan2.
		
-!plan1 
	<-	.wait(100);
		!!plan1.
		
+!plan2
	<-	jros.getTopicData("/odom",L);
		.nth(0,L,A);
		!moveforward(0.3);
		!!pos(2.75,math.abs(A));
		.wait({+posok});
		-posok;
		!plan3.
		
-!plan2
	<-	.wait(100);
		!!plan2.
		
+!plan3
	<-	jros.getTopicData("/odom",L);
		.nth(6,L,A);
		!rotateright(0.3);
		!!angle(0.98,math.abs(A));
		.wait({+angleok});
		-angleok;
		!plan4.
		
-!plan3
	<-	.wait(100);
		!!plan3.
	
+!plan4
	<-	jros.getTopicData("/odom",L);
		.nth(0,L,A);
		!moveforward(0.3);
		!!pos(5.2,math.abs(A));
		.wait({+posok});
		-posok;
		!plan5.
		
-!plan4
	<-	.wait(100);
		!!plan4.
		
+!plan5
	<-	jros.getTopicData("/odom",L);
		.nth(6,L,A);
		!rotateleft(0.3);
		!!angle(0.92,math.abs(A));
		.wait({+angleok});
		-angleok;
		!plan6.
		
-!plan5
	<-	.wait(100);
		!!plan5.
		
+!plan6
	<-	jros.getTopicData("/odom",L);
		.nth(0,L,A);
		!moveforward(0.3);
		!!pos(6,math.abs(A));
		.wait({+posok});
		-posok.
		//!plan6.
		
-!plan6
	<-	.wait(100);
		!!plan6.