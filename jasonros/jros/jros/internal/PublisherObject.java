package jros.internal;


import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;

import geometry_msgs.Vector3;
import jason.asSemantics.Unifier;
import jason.asSyntax.Term;

public class PublisherObject extends AbstractNodeMain{
	private String nodeName;
	public PublisherObject(ConnectedNode connectedNode, String nodeName, Object topic, long pRate, ROSConnection rosconn) throws InterruptedException{
		this.nodeName = nodeName;
		String topicName;
		String msgType;
		Object data;
		if(topic instanceof GDataClass){
			topicName = ((GDataClass)topic).getTopicName();
			msgType = ((GDataClass)topic).getMsgType();
			data = null;
			Unifier un  = ((GDataClass)topic).getUnifier();
			Term[] terms = ((GDataClass)topic).getTerms();
			String className = ((GDataClass)topic).getClassName();
			try {
				Object genClass = Class.forName(className).newInstance();
				((GenericPub)genClass).pubProc(connectedNode, topicName, msgType, un, terms);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}else{
			topicName = ((DataClass)topic).getTopicName();
			msgType = ((DataClass)topic).getMsgType();
			data = ((DataClass)topic).getData();
			switch(msgType){
			case "std_msgs/String":{
				Publisher<std_msgs.String> pubNode = connectedNode.newPublisher(topicName, msgType);
				connectedNode.executeCancellableLoop(new CancellableLoop() {
					@Override
					protected void loop() throws InterruptedException {
						std_msgs.String msg = pubNode.newMessage();
						msg.setData((String)data);
						pubNode.publish(msg);
						Thread.sleep(pRate);
					}
				});
			}
			case "geometry_msgs/Twist":{
				Publisher<geometry_msgs.Twist> pubNode = connectedNode.newPublisher(topicName, msgType);
				connectedNode.executeCancellableLoop(new CancellableLoop() {
					@Override
					protected void loop() throws InterruptedException {
						String[] params = ((String)data).split("(,)|( ,)");
						
						geometry_msgs.Twist msg = pubNode.newMessage();
						Vector3 ang = msg.getAngular();
						Vector3 lin = msg.getLinear();
						lin.setX(Double.valueOf(params[0]));
						lin.setY(Double.valueOf(params[1]));
						lin.setZ(Double.valueOf(params[2]));
						ang.setX(Double.valueOf(params[3]));
						ang.setY(Double.valueOf(params[4]));
						ang.setZ(Double.valueOf(params[5]));
						msg.setAngular(ang);
						msg.setLinear(lin);
						pubNode.publish(msg);
						Thread.sleep(pRate);
					}
				});
			}
			break;
			case "hanse_msgs/sollSpeed":{
				Publisher<hanse_msgs.sollSpeed> pubNode = connectedNode.newPublisher(topicName, msgType);
				connectedNode.executeCancellableLoop(new CancellableLoop() {
					@Override
					protected void loop() throws InterruptedException {
						hanse_msgs.sollSpeed msg = pubNode.newMessage();
						msg.setData(((Double)data).byteValue());
						pubNode.publish(msg);
						Thread.sleep(pRate);
					}
				});
			}
			break;
			case "std_msgs/Int8":{
				Publisher<std_msgs.Int8> pubNode = connectedNode.newPublisher(topicName, msgType);
				connectedNode.executeCancellableLoop(new CancellableLoop() {
					@Override
					protected void loop() throws InterruptedException {
						std_msgs.Int8 msg = pubNode.newMessage();
						msg.setData(((Byte)data).byteValue());
						pubNode.publish(msg);
						Thread.sleep(pRate);
					}
				});
			}
			break;
			case "std_msgs/Int16":{
				Publisher<std_msgs.Int16> pubNode = connectedNode.newPublisher(topicName, msgType);
				connectedNode.executeCancellableLoop(new CancellableLoop() {
					@Override
					protected void loop() throws InterruptedException {
						std_msgs.Int16 msg = pubNode.newMessage();
						msg.setData(((Double)data).shortValue());
						pubNode.publish(msg);
						Thread.sleep(pRate);
					}
				});
			}
			break;
			case "std_msgs/Int32":{
				Publisher<std_msgs.Int32> pubNode = connectedNode.newPublisher(topicName, msgType);
				connectedNode.executeCancellableLoop(new CancellableLoop() {
					@Override
					protected void loop() throws InterruptedException {
						std_msgs.Int32 msg = pubNode.newMessage();
						msg.setData(((Double)data).intValue());
						pubNode.publish(msg);
						Thread.sleep(pRate);
					}
				});
			}
			break;
			case "std_msgs/Int64":{
				Publisher<std_msgs.Int64> pubNode = connectedNode.newPublisher(topicName, msgType);
				connectedNode.executeCancellableLoop(new CancellableLoop() {
					@Override
					protected void loop() throws InterruptedException {
						std_msgs.Int64 msg = pubNode.newMessage();
						msg.setData(((Double)data).longValue());
						pubNode.publish(msg);
						Thread.sleep(pRate);
					}
				});
			}
			break;
			case "std_msgs/Float32":{
				Publisher<std_msgs.Float32> pubNode = connectedNode.newPublisher(topicName, msgType);
				connectedNode.executeCancellableLoop(new CancellableLoop() {
					@Override
					protected void loop() throws InterruptedException {
						std_msgs.Float32 msg = pubNode.newMessage();
						msg.setData(((Double)data).floatValue());
						pubNode.publish(msg);
						Thread.sleep(pRate);
					}
				});
			}
			break;
			case "std_msgs/Float64":{
				Publisher<std_msgs.Float64> pubNode = connectedNode.newPublisher(topicName, msgType);
				connectedNode.executeCancellableLoop(new CancellableLoop() {
					@Override
					protected void loop() throws InterruptedException {
						std_msgs.Float64 msg = pubNode.newMessage();
						msg.setData(((Double)data).doubleValue());
						pubNode.publish(msg);
						Thread.sleep(pRate);
					}
				});
			}
			}
		}
		//Thread.sleep(pRate);
		rosconn.addToCheckList(nodeName);
	}
	
	@Override
	public GraphName getDefaultNodeName() {
		return GraphName.of(nodeName);
	}
}
