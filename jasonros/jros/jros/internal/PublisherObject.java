package jros.internal;


import java.util.ArrayList;

import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;

import geometry_msgs.Point;
import geometry_msgs.Pose;
import geometry_msgs.PoseWithCovariance;
import geometry_msgs.Quaternion;
import geometry_msgs.Vector3;
import jason.NoValueException;
import jason.asSemantics.Unifier;
import jason.asSyntax.NumberTerm;
import jason.asSyntax.StringTerm;
import jason.asSyntax.Term;
import jason.stdlib.term2string;

public class PublisherObject extends AbstractNodeMain{
	private String nodeName;
	private ArrayList<Object> dataP;
	private String topicName;
	
	public void setData(ArrayList<Object> o){
		dataP = o;
	}
	
	public String getTopicName(){
		return topicName;
	}
	public PublisherObject(ConnectedNode connectedNode, String nodeName, JROSNodeInfo jn, long pRate, ROSConnection rosconn) throws InterruptedException{
		this.nodeName = nodeName;
		String msgType;
		ArrayList<Object> data;
		/*if(topic instanceof _GDataClass){
			topicName = ((_GDataClass)topic).getTopicName();
			msgType = ((_GDataClass)topic).getMsgType();
			data = null;
			dataP = data;
			Unifier un  = ((_GDataClass)topic).getUnifier();
			Term[] terms = ((_GDataClass)topic).getTerms();
			String className = ((_GDataClass)topic).getClassName();
			try {
				Object genClass = Class.forName(className).newInstance();
				((GenericPub)genClass).pubProc(connectedNode, topicName, msgType, un, terms);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}else{*/
			topicName = jn.getTopic();
			msgType = jn.getMsgType();
			data = jn.getParams();
			dataP = data;
			
			switch(msgType){
			case "std_msgs/String":{
				Publisher<std_msgs.String> pubNode = connectedNode.newPublisher(topicName, msgType);
				pubNode.setLatchMode(true);
				connectedNode.executeCancellableLoop(new CancellableLoop() {
					@Override
					protected void loop() throws InterruptedException {
						std_msgs.String msg = pubNode.newMessage();
						if(dataP != null){
							msg.setData((String)dataP.get(0));
						}
						pubNode.publish(msg);
						Thread.sleep(pRate);
					}
				});
			}
			break;
			case "geometry_msgs/Twist":{
				System.out.println("MessageType:"+msgType);
				Publisher<geometry_msgs.Twist> pubNode = connectedNode.newPublisher(topicName, msgType);
				pubNode.setLatchMode(true);
				connectedNode.executeCancellableLoop(new CancellableLoop() {
					@Override
					protected void loop() throws InterruptedException {
						geometry_msgs.Twist msg = pubNode.newMessage();
						Vector3 ang = msg.getAngular();
						Vector3 lin = msg.getLinear();
						if(dataP != null){
							//Term[] terms = ((Term[])dataP);
							//System.out.println("terms length:"+terms.length);
							lin.setX((double)dataP.get(0));
							lin.setY((double)dataP.get(1));
							lin.setZ((double)dataP.get(2));
							ang.setX((double)dataP.get(3));
							ang.setY((double)dataP.get(4));
							ang.setZ((double)dataP.get(5));
							msg.setAngular(ang);
							msg.setLinear(lin);
						}
						pubNode.publish(msg);
						Thread.sleep(pRate);
					}
				});
			}
			break;
			case "nav_msgs/Odometry":{
				Publisher<nav_msgs.Odometry> pubNode = connectedNode.newPublisher(topicName, msgType);
				pubNode.setLatchMode(true);
				connectedNode.executeCancellableLoop(new CancellableLoop() {
					@Override
					protected void loop() throws InterruptedException {
						nav_msgs.Odometry msg = pubNode.newMessage();
						if(dataP != null){
						//Term[] terms = ((Term[])dataP);
						//String str = ((StringTerm)terms[1]).getString();
						//String[] params = str.split("(,)|( ,)");
						PoseWithCovariance posec = msg.getPose();
						Pose pose = posec.getPose();
						Point pos = pose.getPosition();
						Quaternion ori = pose.getOrientation();
						pos.setX((double)dataP.get(0));
						pos.setY((double)dataP.get(1));
						pos.setZ((double)dataP.get(2));
						ori.setW((double)dataP.get(3));
						ori.setX((double)dataP.get(4));
						ori.setY((double)dataP.get(5));
						ori.setZ((double)dataP.get(6));
						pose.setPosition(pos);
						posec.setPose(pose);
						msg.setPose(posec);
						}
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
						if(dataP != null){
						double value = (double)dataP.get(0);
						msg.setData((byte)value);
						}
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
						if(dataP != null){
							double value = (double)dataP.get(0);
							msg.setData((short)value);
						}
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
						if(dataP != null){
							double value = (double)dataP.get(0);;
							msg.setData((int)value);
						}
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
						if(dataP != null){
						double value = (double)dataP.get(0);;
						msg.setData((long)value);
						}
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
						if(dataP != null){
						double value = (double)dataP.get(0);
						msg.setData((float)value);
						}
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
						if(dataP != null){
						double value = (double)dataP.get(0);
						msg.setData((double)value);
						}
						pubNode.publish(msg);
						Thread.sleep(pRate);
					}
				});
			}
			}
		//}
		//Thread.sleep(pRate);
		rosconn.addToCheckList(nodeName);
	}
	
	@Override
	public GraphName getDefaultNodeName() {
		return GraphName.of(nodeName);
	}
}
