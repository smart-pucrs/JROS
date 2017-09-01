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
	<- 	
		jros.createPubNode("mynode","/cmd_vel_mux/input/teleop","geometry_msgs/Twist",500);
		jros.createSubNode("mysnode","/cmd_vel_mux/input/teleop","geometry_msgs/Twist");
		.wait(1000);
		!!turtle.
		
+!stop
	<- 	jros.setTopicData("mynode",0,0,0,0,0,0);
		jros.getTopicData("mysnode",L);
		.nth(0,L,0);
		.nth(5,L,0);
		+stopped.
-!stop
	<- !!stop.
	
//X = angulo (graus)  Y = vel. angular ( graus/sec )
+!rotate(X,Y)
	<- 	if(X < 0){
			jros.setTopicData("mynode",0,0,0,0,0,math.abs(Y*2*math.pi/360));//graus(vel_ang)
		}else{
			jros.setTopicData("mynode",0,0,0,0,0,-math.abs(Y*2*math.pi/360));	
		}
		.wait(math.abs(X/Y)*1000);//Y rad por seg, portanto em X/Y segs o robo rotaciona X radianos
		!!stop;
		.wait({+stopped});
		-stopped;
		+angleok.
-!rotate(X,Y)
	<- 	.wait(100);
		!!rotate(X,Y).

+!moveforward(X,Y)
	<- 	jros.setTopicData("mynode",math.abs(Y),0,0,0,0,0);
		.wait((X/Y)*1000);
		!!stop;
		.wait({+stopped});
		-stopped;
		+posok.
-!moveforward(X,Y)
	<-	.wait(100);
		!!moveforward(X,Y).

//move_set = {170,3.2,50,3,-90,1.6,-98,4.935,-91,4,-50,1}
+!turtle
	<-	M = [3.2,50,3,-90,1.6,-98,4.935,-91,4,-50,1];
		!!rotate(158,15);
		for(.member(X,M)){
			if(rotate){
				.wait({+posok});
				-posok;
				!!rotate(X,15);
				-rotate
			}else{
				.wait({+angleok});
				-angleok;
				!!moveforward(X,0.3);
				+rotate
			}
		}.