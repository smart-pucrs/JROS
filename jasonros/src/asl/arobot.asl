// Agent turtlebot in project jasonros

/* Initial beliefs and rules */

/* Initial goals */
!connectto("127.0.0.1","11311").

/* Plans */

+!connectto(IP, Port)
	<-	//jros.config(IP,Port,"arobot");
		jros.config(IP,Port,"arobot","/home/iancalaca/jason/projects/JROS/jasonros/src/java/topics.jros");
		//!createNodes.
		!testActions.
-!connectto(IP, Port)
	<-	.print("Connection error. Trying again...");
		.wait(1000);
		!!connectto(IP, Port).

+!testActions
	<-	jros.sendAction("testpub",12340).
+!createNodes
	<- 	jros.addPubTopic("/cmd_vel_mux/input/teleop","geometry_msgs/Twist",null);//linear = (0,0,0) / angular = (1,2,3)
		jros.addSubTopic("/cmd_vel_mux/input/teleop","geometry_msgs/Twist");
		jros.createPubNode("mynode",500);
		jros.createSubNode("mysnode");
		.wait(1000);
		!!turtle.
		
+!stop
	<- 	jros.setTopicData("mynode","/cmd_vel_mux/input/teleop",0,0,0,0,0,0);
		jros.getTopicData("/cmd_vel_mux/input/teleop",L);
		.nth(0,L,0);
		.nth(5,L,0);
		+stopped.
-!stop
	<- !!stop.
	
//X = angulo (graus)  Y = vel. angular ( graus/sec )
+!rotate(X,Y)
	<- 	if(X < 0){
			jros.setTopicData("mynode","/cmd_vel_mux/input/teleop",0,0,0,0,0,math.abs(Y*2*math.pi/360));//graus(vel_ang)
		}else{
			jros.setTopicData("mynode","/cmd_vel_mux/input/teleop",0,0,0,0,0,-math.abs(Y*2*math.pi/360));	
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
	<- 	jros.setTopicData("mynode","/cmd_vel_mux/input/teleop",math.abs(Y),0,0,0,0,0);
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
		!!rotate(170,15);
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