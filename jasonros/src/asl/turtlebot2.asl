// Agent turtlebot2 in project jasonros
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
		!!s0.

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
		.wait(100);
		!!angle(X,math.abs(A)).
		
+!angle2(X,Y) : checkAngle(X,Y)
	<-	!stop;
		+angleok;
		.print("X = ",X," Y = ",Y).
		
-!angle2(X,Y)
	<- jros.getTopicData("/odom",L);
		.nth(3,L,A);
		.wait(100);
		!!angle(X,math.abs(A)).
		
		
+!pos(X,Y) : checkPosX(X,Y)
	<-	!stop;
		.print("parou.");
		+posok;
		.print("X = ",X," Y = ",Y).
		
-!pos(X,Y)
	<- jros.getTopicData("/odom",L);
		.nth(0,L,A);
		.wait(100);
		!!pos(X,math.abs(A)).
		
+!s0
	<- 	jros.getTopicData("/odom",L);
		!stop.
		/*.nth(6,L,A);
		!rotateright(0.2);
		!!angle(0.96,math.abs(A));
		.wait({+angleok});
		-angleok;
		!s1.*/
		
-!s0 
	<-	.wait(100);
		!!s0.
		
+!s1
	<-	jros.getTopicData("/odom",L);
		.nth(0,L,A);
		!moveforward(0.2);
		!!pos(2.70,math.abs(A));
		.wait({+posok});
		-posok;
		!s2.
		
-!s1
	<-	.wait(100);
		!!s1.
		
+!s2
	<-	jros.getTopicData("/odom",L);
		.nth(6,L,A);
		!rotateright(0.2);
		!!angle(0.97,math.abs(A));
		.wait({+angleok});
		-angleok;
		!s3.
		
-!s2
	<-	.wait(100);
		!!s2.
	
+!s3
	<-	jros.getTopicData("/odom",L);
		.nth(0,L,A);
		!moveforward(0.2);
		!!pos(5.2,math.abs(A));
		.wait({+posok});
		-posok;
		!s4.
		
-!s3
	<-	.wait(100);
		!!s3.
		
+!s4
	<-	jros.getTopicData("/odom",L);
		.nth(6,L,A);
		!rotateleft(0.2);
		!!angle(0.92,math.abs(A));
		.wait({+angleok});
		-angleok;
		!s5.
		
-!s4
	<-	.wait(100);
		!!s4.
		
+!s5
	<-	jros.getTopicData("/odom",L);
		.nth(0,L,A);
		!moveforward(0.2);
		!!pos(6.1,math.abs(A));
		.wait({+posok});
		-posok;
		!s6.
		
-!s5
	<-	.wait(100);
		!!s5.
		
+!s6
	<-	jros.getTopicData("/odom",L);
		.nth(5,L,A);
		!rotateleft(0.2);
		!!angle2(0.96,math.abs(A));
		.wait({+angleok});
		-angleok.
		//!s7.
		
-!s6
	<-	.wait(100);
		!!s6.