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
import geometry_msgs.Twist;
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
	private long pRate;
	private PublisherObject thisInstance = this;
	
	public long getpRate(){
		return pRate;
	}
	
	public void setData(ArrayList<Object> o){
		dataP = o;
	}
	
	public ArrayList<Object> getDataP(){
		return dataP;
	}
	
	public String getTopicName(){
		return topicName;
	}
	public PublisherObject(ConnectedNode connectedNode, String nodeName, JROSNodeInfo jn, ROSConnection rosconn) throws InterruptedException{
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
			this.pRate = jn.getPRate();
			
			switch(msgType){
			case "std_msgs/String":{
				Publisher<std_msgs.String> pubNode = connectedNode.newPublisher(topicName, msgType);
				jros.internal.Messages.std_msgs.String jrosMsg = new jros.internal.Messages.std_msgs.String(thisInstance);
				pubNode.setLatchMode(true);
				connectedNode.executeCancellableLoop(new CancellableLoop() {
					@Override
					protected void loop() throws InterruptedException {
						std_msgs.String msg = pubNode.newMessage();
						jrosMsg.msgExecPub(msg, pubNode);
					}
				});
			}
			break;
			case "geometry_msgs/Twist":{
				System.out.println("MessageType:"+msgType);
				Publisher<geometry_msgs.Twist> pubNode = connectedNode.newPublisher(topicName, msgType);
				jros.internal.Messages.geometry_msgs.Twist jrosMsg = new jros.internal.Messages.geometry_msgs.Twist(thisInstance);
				pubNode.setLatchMode(true);
				connectedNode.executeCancellableLoop(new CancellableLoop() {
					@Override
					protected void loop() throws InterruptedException {
						geometry_msgs.Twist msg = pubNode.newMessage();
						jrosMsg.msgExecPub(msg, pubNode);
					}
				});
			}
			break;
			case "std_msgs/Int8":{
				Publisher<std_msgs.Int8> pubNode = connectedNode.newPublisher(topicName, msgType);
				jros.internal.Messages.std_msgs.Int8 jrosMsg = new jros.internal.Messages.std_msgs.Int8(thisInstance);
				connectedNode.executeCancellableLoop(new CancellableLoop() {
					@Override
					protected void loop() throws InterruptedException {
						std_msgs.Int8 message = pubNode.newMessage();
						jrosMsg.msgExecPub(message, pubNode);
					}
				});
			}
			break;
			case "std_msgs/Int16":{
				Publisher<std_msgs.Int16> pubNode = connectedNode.newPublisher(topicName, msgType);
				jros.internal.Messages.std_msgs.Int16 jrosMsg = new jros.internal.Messages.std_msgs.Int16(thisInstance);
				connectedNode.executeCancellableLoop(new CancellableLoop() {
					@Override
					protected void loop() throws InterruptedException {
						std_msgs.Int16 message = pubNode.newMessage();
						jrosMsg.msgExecPub(message, pubNode);
					}
				});
			}
			break;
			case "std_msgs/Int32":{
				Publisher<std_msgs.Int32> pubNode = connectedNode.newPublisher(topicName, msgType);
				jros.internal.Messages.std_msgs.Int32 jrosMsg = new jros.internal.Messages.std_msgs.Int32(thisInstance);
				connectedNode.executeCancellableLoop(new CancellableLoop() {
					@Override
					protected void loop() throws InterruptedException {
						std_msgs.Int32 message = pubNode.newMessage();
						jrosMsg.msgExecPub(message, pubNode);
					}
				});
			}
			break;
			case "std_msgs/Int64":{
				Publisher<std_msgs.Int64> pubNode = connectedNode.newPublisher(topicName, msgType);
				jros.internal.Messages.std_msgs.Int64 jrosMsg = new jros.internal.Messages.std_msgs.Int64(thisInstance);
				connectedNode.executeCancellableLoop(new CancellableLoop() {
					@Override
					protected void loop() throws InterruptedException {
						std_msgs.Int64 message = pubNode.newMessage();
						jrosMsg.msgExecPub(message, pubNode);
					}
				});
			}
			break;
			case "std_msgs/Float32":{
				Publisher<std_msgs.Float32> pubNode = connectedNode.newPublisher(topicName, msgType);
				jros.internal.Messages.std_msgs.Float32 jrosMsg = new jros.internal.Messages.std_msgs.Float32(thisInstance);
				connectedNode.executeCancellableLoop(new CancellableLoop() {
					@Override
					protected void loop() throws InterruptedException {
						std_msgs.Float32 msg = pubNode.newMessage();
						jrosMsg.msgExecPub(msg, pubNode);
					}
				});
			}
			break;
			case "std_msgs/Float64":{
				Publisher<std_msgs.Float64> pubNode = connectedNode.newPublisher(topicName, msgType);
				jros.internal.Messages.std_msgs.Float64 jrosMsg = new jros.internal.Messages.std_msgs.Float64(thisInstance);
				connectedNode.executeCancellableLoop(new CancellableLoop() {
					@Override
					protected void loop() throws InterruptedException {
						std_msgs.Float64 message = pubNode.newMessage();
						jrosMsg.msgExecPub(message, pubNode);
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
