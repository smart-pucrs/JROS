package jros.internal;


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
import jason.asSyntax.Term;
import jason.stdlib.term2string;

public class PublisherObject extends AbstractNodeMain{
	private String nodeName;
	private Object dataP;
	private String topicName;
	
	public void setData(Object o){
		dataP = o;
	}
	
	public String getTopicName(){
		return topicName;
	}
	public PublisherObject(ConnectedNode connectedNode, String nodeName, Object topic, long pRate, ROSConnection rosconn) throws InterruptedException{
		this.nodeName = nodeName;
		String msgType;
		Object data;
		if(topic instanceof GDataClass){
			topicName = ((GDataClass)topic).getTopicName();
			msgType = ((GDataClass)topic).getMsgType();
			data = null;
			dataP = data;
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
			dataP = data;
			switch(msgType){
			case "std_msgs/String":{
				Publisher<std_msgs.String> pubNode = connectedNode.newPublisher(topicName, msgType);
				pubNode.setLatchMode(true);
				connectedNode.executeCancellableLoop(new CancellableLoop() {
					@Override
					protected void loop() throws InterruptedException {
						std_msgs.String msg = pubNode.newMessage();
						msg.setData((String)dataP);
						pubNode.publish(msg);
						Thread.sleep(pRate);
					}
				});
			}
			case "geometry_msgs/Twist":{
				Publisher<geometry_msgs.Twist> pubNode = connectedNode.newPublisher(topicName, msgType);
				pubNode.setLatchMode(true);
				connectedNode.executeCancellableLoop(new CancellableLoop() {
					@Override
					protected void loop() throws InterruptedException {
						geometry_msgs.Twist msg = pubNode.newMessage();
						Vector3 ang = msg.getAngular();
						Vector3 lin = msg.getLinear();
						try {
							if(dataP != null){
						Term[] terms = ((Term[])dataP);
						lin.setX(((NumberTerm)terms[2]).solve());
						lin.setY(((NumberTerm)terms[3]).solve());
						lin.setZ(((NumberTerm)terms[4]).solve());
						ang.setX(((NumberTerm)terms[5]).solve());
						ang.setY(((NumberTerm)terms[6]).solve());
						ang.setZ(((NumberTerm)terms[7]).solve());
						msg.setAngular(ang);
						msg.setLinear(lin);
						}
						pubNode.publish(msg);
						Thread.sleep(pRate);
						} catch (NoValueException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
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
						String[] params = ((String)dataP).split("(,)|( ,)");
						nav_msgs.Odometry msg = pubNode.newMessage();
						PoseWithCovariance posec = msg.getPose();
						Pose pose = posec.getPose();
						Point pos = pose.getPosition();
						Quaternion ori = pose.getOrientation();
						pos.setX(Double.valueOf(params[0]));
						pos.setY(Double.valueOf(params[1]));
						pos.setZ(Double.valueOf(params[2]));
						ori.setW(Double.valueOf(params[3]));
						ori.setX(Double.valueOf(params[4]));
						ori.setY(Double.valueOf(params[5]));
						ori.setZ(Double.valueOf(params[6]));
						pose.setPosition(pos);
						posec.setPose(pose);
						msg.setPose(posec);
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
						msg.setData(((Double)dataP).byteValue());
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
						msg.setData(((Byte)dataP).byteValue());
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
						msg.setData(((Double)dataP).shortValue());
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
						msg.setData(((Double)dataP).intValue());
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
						msg.setData(((Double)dataP).longValue());
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
						msg.setData(((Double)dataP).floatValue());
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
						msg.setData(((Double)dataP).doubleValue());
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
