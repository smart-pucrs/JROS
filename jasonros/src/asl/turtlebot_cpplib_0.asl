// Agent turtlebot_cpplib in project jasonros

/* Initial beliefs and rules */

/* Initial goals */
!connectto("127.0.0.1","11311").

/* Plans */

+!connectto(IP, Port)
	<-	jros.config(IP,Port,"jason_turtlebot_0");
		!p1.
		//!p2.
-!connectto(IP, Port)
	<-	.print("Connection error. Trying again...");
		.wait(1000);
		!!connectto(IP, Port).

//adicionar crencas recebidas(confirmacao e percepcoes) a uma lista do proprio jros
//para nao sobrecarregar a base de crencas
+!p1//adicionar confirmacoes em forma de crenca e usar wait ou implementar o wait no codigo?
	<-	jros.sendAction("move",2);
		.wait({+lastJROSAction(move)}); -lastJROSAction(move);
		.print("move confirmed!");
		jros.sendAction("rotate",90);
		.wait({+lastJROSAction(rotate)}); -lastJROSAction(rotate);
		.print("rotate confirmed!");
		jros.sendAction("move",1);
		.wait({+lastJROSAction(move)}); -lastJROSAction(move).
