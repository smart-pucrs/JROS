/* Initial goals */
//!connectto("186.34.5.1","11311").
!connectto("127.0.0.1","11311").
//!main.
/* Plans */
+!connectto(IP, Port)
	<-  jros.config(IP,Port);
		!addTopicT1;
		!addTopicT2;
		!addTopicT3;
		!addTopicT4;
		!addTopicT5;
		!subToTopics.
-!connectto(IP, Port)
	<-	.print("Connection error. Trying again...");
		.wait(1000);
		!!connectto(IP, Port).
+!addTopicT1
	<-	jros.addSubTopic("/topics/t1","std_msgs/String"). 
+!addTopicT2
	<- 	jros.addSubTopic("/topics/t2","std_msgs/Int32").
+!addTopicT3
	<- 	jros.addSubTopic("/topics/t3","std_msgs/String").
+!addTopicT4
	<- 	jros.addSubTopic("/topics/t4","std_msgs/Float32").
+!addTopicT5
	<- 	jros.addSubGenericTopic("/jtopics/action","jason_msgs/action","TestGenericSub").
+!subToTopics
	<- 	jros.createSubNode("jason/subnode");
		!getSubData.
+!getSubData
	<- 	jros.getTopicData("/topics/t1", A);
		jros.getTopicData("/topics/t2", B);
		jros.getTopicData("/topics/t3", C);
		jros.getTopicData("/topics/t4", D);
		//jros.listenPerceptions;
		//.wait({+testP});
		.print("Topic 1:", A);
		.print("Topic 2:", B);
		.print("Topic 3:", C);
		.print("Topic 4:", D);
		!btRec.
		
-!getSubData
	<- .wait(400);
	!!getSubData.
+!btRec
	<- jros.searchBTState("/topics/t4",123.44);
		.print("Valor encontrado.").
-!btRec
	<- !!btRec.