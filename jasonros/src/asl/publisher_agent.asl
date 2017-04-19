/* Initial goals */
!connectto("127.0.0.1","11311").

/* Plans */
+!connectto(IP, Port)
	<-	jros.config(IP,Port);
		!addPubTopicT1;
		!addPubTopicT2;
		!addPubTopicT3;
		!addPubTopicT4;
		!addPubGeneric;
		!pubOnTopics.
-!connectto(IP, Port)
	<-	.print("Connection error. Trying again...");
		.wait(1000);
		!!connectto(IP, Port).		
+!addPubTopicT1
	<- jros.addPubTopic("/topics/t1","std_msgs/String","Topic 1 :D").
+!addPubTopicT2
	<- jros.addPubTopic("/topics/t2","std_msgs/Int32",124).
+!addPubTopicT3
	<- jros.addPubTopic("/topics/t3","std_msgs/String","Topic 3 :D").
+!addPubTopicT4
	<- jros.addPubTopic("/topics/t4","std_msgs/Float32",123.45).
+!addPubGeneric
	<- jros.addPubGenericTopic("/topics/t5", "sensor_msgs/Temperature", "TestGenericPub").
+!pubOnTopics
	<- jros.createPubNode("jason/pubnode",500).