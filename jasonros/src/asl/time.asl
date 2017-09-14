// Agent turtlebot_cpplib in project jasonros

/* Initial beliefs and rules */

/* Initial goals */
!connectto("127.0.0.1","11311").

/* Plans */
+!connectto(IP, Port): .my_name(Me) & .term2string(Me,Mes)
	<-	.print(Mes);
		jros.config(IP,Port,Mes,"/home/iancalaca/jason/projects/JROS/jasonros/src/java/topics.jros");
		.wait(2000);
		!!turtle.
		//!p2.
-!connectto(IP, Port)
	<-	.print("Connection error. Trying again...");
		.wait(1000);
		!!connectto(IP, Port).
			
+!stop
	<- 	jros.sendAction("setVel",0,0,0,0,0,0);
		jros.recvData("getVel",L);
		.nth(0,L,0);
		.nth(5,L,0);
		+stopped.
-!stop
	<- //.wait(100);
		//.print("Fail Stop");
		!!stop.
	
+!rotate(X,Y)
	<- 	if(X < 0){
			jros.sendAction("setVel", 0,0,0,0,0,math.abs(Y*2*math.pi/360));
		}else{
			jros.sendAction("setVel", 0,0,0,0,0,-math.abs(Y*2*math.pi/360));
		}
		.wait(math.abs(X/Y)*1000);
		!!stop;
		.wait({+stopped});
		-stopped;
		+angleok.
-!rotate(X,Y)
	<- 	//.wait(100);
		!!rotate(X,Y).

+!moveforward(X,Y)
	<- 	
		jros.sendAction("setVel", math.abs(Y),0,0,0,0,0);
		.wait((X/Y)*1000);
		!!stop;
		.wait({+stopped});
		-stopped;
		+posok.
-!moveforward(X,Y)
	<-	//.wait(100);
		!!moveforward(X,Y).


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