// Agent turtlebot_cpplib in project jasonros

/* Initial beliefs and rules */

/* Initial goals */
!connectto("127.0.0.1","11311").

/* Plans */

+!connectto(IP, Port): .my_name(Me) & .term2string(Me,Mes)
	<-	.print(Mes);
		jros.config(IP,Port,Mes);
		!p1.
		//!p2.
-!connectto(IP, Port)
	<-	.print("Connection error. Trying again...");
		.wait(1000);
		!!connectto(IP, Port).
		
+!move(X) <-
		jros.sendAction("move",X);
		.wait({+lastJROSAction(move)});
		-lastJROSAction(move);
		.print("move confirmed!").
		
+!rotate(X) <-
		jros.sendAction("rotate",X);
		.wait({+lastJROSAction(rotate)}); 
		-lastJROSAction(rotate);
		.print("rotate confirmed!").

//adicionar crencas recebidas(confirmacao e percepcoes) a uma lista do proprio jros
//para nao sobrecarregar a base de crencas
+!p1//adicionar confirmacoes em forma de crenca e usar wait ou implementar o wait no codigo?
	<-	!move(1);
		!rotate(90);
		!move(1);
		!rotate(90);
		!move(1);
		!rotate(90);
		!move(1);
		!rotate(90).
