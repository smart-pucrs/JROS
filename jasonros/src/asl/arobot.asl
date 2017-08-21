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
	<-	
		//jros.sendAction("testactionpub", 12345);
		.wait({+lastJROSAction(testactionsub)});
		-lastJROSAction(testactionsub);
		while(true){
			.wait(500);
			.print("Recebido!!!!!");
			jros.recvData("testactionsub",S);
			.print("String: ",S);
		}.