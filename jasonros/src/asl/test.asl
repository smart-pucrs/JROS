/*!test(0).
!setspeed(10,0).
!teststring.
!addpubinttopic.
!addpubstringtopic.
!createpubnode.
*/
/*+!test(X) : jia.jcall("gettopicdata","/hanse/motors/right",D) & X == D
	<- .print("\nX = ",X,"\nD = ",D).
	
+!test(X) : 1 < 2
	<- 	.print(X);
		!test(X+1).
*/	 

 /*+!setspeed(X,V) : X == -1 & V == 11
	<- 	jia.jcall("gettopicdata","/hanse/motors/right",D);
		jia.jcall("gettopicdata","/hanse/motors/left",E);
		.print("Velocidade alcancada! \nEsquerda = ", E,"\nDireita = ",D).

+!setspeed(X,V) : X <= 10
	<-	jia.jcall("settopicdata","/hanse/motors/right","std_msgs/Int32",V);
		jia.jcall("settopicdata","/hanse/motors/left","std_msgs/Int32",V);
		.wait(1000);
		!!setspeed(X-1,V+1).
*/
/*+!teststring : true
	<- jia.jcall("gettopicdata","/topic/string", S);
		.print("String = ",S).
*/
/* +!addpubinttopic : true 
	<- jia.jcall("addpubtopic", "/topic/int", "std_msgs/Int32", 102030).
+!addpubstringtopic : true 
	<- jia.jcall("addpubtopic", "/topic/string", "std_msgs/String", ":D :D :D").
+!createpubnode : true
	<- jia.jcall("createpubnode", "testnode/pubnode", 1000).*/