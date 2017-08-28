package jros.internal;


import java.util.ArrayList;
import java.util.HashMap;
//import java.util.HashMap;
import java.util.List;

import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
//import org.ros.node.Node;
import org.ros.node.topic.Subscriber;

import geometry_msgs.Point;
import geometry_msgs.Quaternion;
import geometry_msgs.Vector3;
import jason.RevisionFailedException;
import jason.asSemantics.Agent;
import jason.asSemantics.Unifier;
import jason.asSyntax.ASSyntax;
import jason.asSyntax.Literal;
import jason.asSyntax.Term;
import jason.asSyntax.parser.ParseException;


public class SubscriberObject extends AbstractNodeMain{
	private String nodeName;
	private String topicName;
	private String action = null;
	private Agent ag;
	private Literal bel = null;
	private ArrayList<SubscriberData> subData = new ArrayList<SubscriberData>();
	//private HashMap<SDataClass> subData = new HashMap<SDataClass>();
	//private long timeStamp = 0;
	
	public String getTopicName(){
		return topicName;
	}
	
	public SubscriberData getLastStateObj(String topicName){
		//for(int i = subData.size()-1; i >= 0; i--){
		if(!subData.isEmpty()){
			SubscriberData sd = subData.get(subData.size()-1);
			//if(dc.getTopicName().equals(topicName))
				return sd;
		}
		return null;
		//}
		//return null;
	}
	
	public Object getLastStateData(String topicName){
		//for(int i = subData.size()-1; i >= 0; i--){
		if(!subData.isEmpty()){	
		SubscriberData sd = subData.get(subData.size()-1);
			//if(sd.getTopicName().equals(topicName))
				return sd.getData();
		}
		//}
		return null;
	}
	
	public void clearSubDataList(){
		subData.clear();
	}
	
	public SubscriberData getFirstStateObj(String topicName){
		//for(int i = 0; i < subData.size(); i++){
		if(!subData.isEmpty()){
			SubscriberData dc = subData.get(0);
			//if(dc.getTopicName().equals(topicName))
				return dc;
		}
		//}
		return null;
	}
	
	public Object getFirstStateData(String topicName){
		//for(int i = 0; i < subData.size(); i++){
		if(!subData.isEmpty()){
		SubscriberData dc = subData.get(0);
			
			//if(dc.getTopicName().equals(topicName))
				return dc.getData();
		}
		//}
		return null;
	}
	
	public boolean searchExactState(String topicName, Object state){//<---------TODO:converter data
		for(SubscriberData dc : subData){
			if(dc.getTopicName().equals(topicName)){
				Object data = dc.getData();
				if(data instanceof Integer){
					int iData = ((Double)state).intValue(); 
					if(dc.getData().equals(iData))
						return true;
				}else if(data instanceof Float){
					float fData = ((Double)state).floatValue();
					if(dc.getData().equals(fData))
						return true;
				}else if(data instanceof Double){
					if(dc.getData().equals(state))
						return true;
				}
				else{
					if(dc.getData().equals(state))
						return true;
				}
			}
		}
		return false;
	}
	
	public boolean searchLessThanState(String topicName, Object state){
		for(SubscriberData dc : subData)
			if(dc.getTopicName().equals(topicName)){
				Object data = dc.getData();
				if(data instanceof Integer)
					if(((Integer)dc.getData()).intValue() < ((Double)state).intValue())
						return true;
				if(data instanceof Float)
					if(((Float)dc.getData()).floatValue() < ((Double)state).floatValue())
						return true;
				if(data instanceof Double)
					if(((Double)dc.getData()).doubleValue() < ((Double)state).doubleValue())
						return true;
			}
		return false;
	}
	
	public boolean searchBiggerThanState(String topicName, Object state){
		for(SubscriberData dc : subData)
			if(dc.getTopicName().equals(topicName)){
				Object data = dc.getData();
				if(data instanceof Integer)
					if(((Integer)dc.getData()).intValue() > ((Double)state).intValue())
						return true;
				if(data instanceof Float)
					if(((Float)dc.getData()).floatValue() > ((Double)state).floatValue())
						return true;
				if(data instanceof Double)
					if(((Double)dc.getData()).doubleValue() > ((Double)state).doubleValue())
						return true;
			}
		return false;
	}
	
	//msgtype ex:"std_msgs/String"
	//Usar TreeMap ao inves de HashMap
	public SubscriberObject(ConnectedNode connectedNode, String action, JROSNodeInfo jn, ROSConnection rosconn){
		this.nodeName = jn.getNodeName();
		this.ag = rosconn.getAg();
		//String topicName;
		String msgType = "";
		/*if(topic instanceof _GDataClass){
			this.topicName = ((_GDataClass)topic).getTopicName();
			msgType = ((_GDataClass)topic).getMsgType();
			String className = ((_GDataClass)topic).getClassName();
			Unifier un = ((_GDataClass)topic).getUnifier();
			Term[] terms = ((_GDataClass)topic).getTerms();
			try {
				Object genClass = Class.forName(className).newInstance();
				((GenericSub)genClass).subProc(connectedNode, topicName, msgType, un, terms);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}else{*/
			this.topicName = jn.getTopic();
			msgType = jn.getMsgType();
			//System.out.println("Sub str:"+nodeName.substring(0, nodeName.length()-7));
			/*for(String[] p : rosconn.getActionList()){
				if(p[2].equals(this.topicName) && p[0].equals(nodeName.substring(0, nodeName.length()-7))){ //<<<------- Problem here :/
					this.action = p[0];
					break;
				}
			}*/
			this.action = action;
			//if(!rosconn.nodeExists(nodeName))
			if(this.action != null)
				try {
					bel = ASSyntax.parseLiteral("lastJROSAction("+this.action+")");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			switch(msgType){
			case "std_msgs/String":{
				Subscriber<std_msgs.String> subNode = connectedNode.newSubscriber(topicName, msgType);
				subNode.addMessageListener(new MessageListener<std_msgs.String>() {     
					@Override
					public void onNewMessage(std_msgs.String message) {
						//System.out.println("Novo!!!!");
						SubscriberData sd;
						if(subData.isEmpty()){
							sd = new SubscriberData(topicName, message.getData());
							subData.add(sd);
						}else{
							Object lastObj = (Object)subData.get(subData.size()-1).getData();
							if(!lastObj.equals(message.getData())){
								sd = new SubscriberData(topicName, message.getData());
								subData.add(sd);
							}
						}
						if(bel != null && !ag.believes(bel, new Unifier())){
							System.out.println("Jason Recv:"+bel.toString());
							try {
								ag.addBel(bel);
							} catch (RevisionFailedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				});
			}
			break;
			case "geometry_msgs/Twist":{
				Subscriber<geometry_msgs.Twist> subNode = connectedNode.newSubscriber(topicName, msgType);
				subNode.addMessageListener(new MessageListener<geometry_msgs.Twist>() {     
					@Override
					public void onNewMessage(geometry_msgs.Twist message) {
						SubscriberData dc;
						List<Double> l = new ArrayList<Double>();
						Vector3 ang = message.getAngular();
						Vector3 lin = message.getLinear();
						l.add(lin.getX());
						l.add(lin.getY());
						l.add(lin.getZ());
						l.add(ang.getX());
						l.add(ang.getY());
						l.add(ang.getZ());
						if(subData.isEmpty()){
							dc = new SubscriberData(topicName, l);
							subData.add(dc);
						}else{
							Object lastObj = (Object)subData.get(subData.size()-1).getData();
							if(!lastObj.equals(l)){
								dc = new SubscriberData(topicName, l);
								subData.add(dc);
							}
						}
						if(bel != null && !ag.believes(bel, new Unifier()))
							try {
								ag.addBel(bel);
							} catch (RevisionFailedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}
				});
			}
			break;
			case "nav_msgs/Odometry":{
				Subscriber<nav_msgs.Odometry> subNode = connectedNode.newSubscriber(topicName, msgType);
				subNode.addMessageListener(new MessageListener<nav_msgs.Odometry>() {     
					@Override
					public void onNewMessage(nav_msgs.Odometry message) {
						SubscriberData dc;
						List<Double> l = new ArrayList<Double>();
						Point pos = message.getPose().getPose().getPosition();
						Quaternion ori = message.getPose().getPose().getOrientation();
						l.add(pos.getX());
						l.add(pos.getY());
						l.add(pos.getZ());
						l.add(ori.getW());
						l.add(ori.getX());
						l.add(ori.getY());
						l.add(ori.getZ());
						if(subData.isEmpty()){
							dc = new SubscriberData(topicName, l);
							subData.add(dc);
						}else{
							Object lastObj = (Object)subData.get(subData.size()-1).getData();
							if(!lastObj.equals(l)){
								dc = new SubscriberData(topicName, l);
								subData.add(dc);
							}
						}
						if(bel != null && !ag.believes(bel, new Unifier()))
							try {
								ag.addBel(bel);
							} catch (RevisionFailedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}
				});
			}
			break;
			case "std_msgs/Int8":{
				Subscriber<std_msgs.Int8> subNode = connectedNode.newSubscriber(topicName, msgType);
				subNode.addMessageListener(new MessageListener<std_msgs.Int8>() {     
					@Override
					public void onNewMessage(std_msgs.Int8 message) {
						//subData.put(topicName, message.getData());
					}
				});
			}
			break;
			case "std_msgs/Int16":{
				Subscriber<std_msgs.Int16> subNode = connectedNode.newSubscriber(topicName, msgType);
				subNode.addMessageListener(new MessageListener<std_msgs.Int16>() {     
					@Override
					public void onNewMessage(std_msgs.Int16 message) {
						//subData.put(topicName, message.getData());
					}
				});
			}
			break;
			case "std_msgs/Int32":{
				Subscriber<std_msgs.Int32> subNode = connectedNode.newSubscriber(topicName, msgType);
				subNode.addMessageListener(new MessageListener<std_msgs.Int32>() {     
					@Override
					public void onNewMessage(std_msgs.Int32 message) {
						//timeStamp++;
						SubscriberData dc;
						if(subData.isEmpty()){
							dc = new SubscriberData(topicName, message.getData());
							subData.add(dc);
						}else{
							Object lastObj = (Object)subData.get(subData.size()-1).getData();
							if(!lastObj.equals(message.getData())){
								dc = new SubscriberData(topicName, message.getData());
								subData.add(dc);
							}
						}
						if(bel != null && !ag.believes(bel, new Unifier()))
							try {
								ag.addBel(bel);
							} catch (RevisionFailedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}
				});
				//rosconn.addToCheckList(nodeName);
			}
			break;
			case "std_msgs/Int64":{
				Subscriber<std_msgs.Int64> subNode = connectedNode.newSubscriber(topicName, msgType);
				subNode.addMessageListener(new MessageListener<std_msgs.Int64>() {     
					@Override
					public void onNewMessage(std_msgs.Int64 message) {
						//subData.put(topicName, message.getData());
					}
				});
			}
			break;
			case "std_msgs/Float32":{
				Subscriber<std_msgs.Float32> subNode = connectedNode.newSubscriber(topicName, msgType);
				subNode.addMessageListener(new MessageListener<std_msgs.Float32>() {     
					@Override
					public void onNewMessage(std_msgs.Float32 message) {
						//timeStamp++;
						SubscriberData dc;
						if(subData.isEmpty()){
							dc = new SubscriberData(topicName, message.getData());
							subData.add(dc);
						}else{
							Object lastObj = (Object)subData.get(subData.size()-1).getData();
							if(!lastObj.equals(message.getData())){
								dc = new SubscriberData(topicName, message.getData());
								subData.add(dc);
							}
						}
						if(bel != null && !ag.believes(bel, new Unifier()))
							try {
								ag.addBel(bel);
							} catch (RevisionFailedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}
				});
			}
			break;
			case "std_msgs/Float64":{
				Subscriber<std_msgs.Float64> subNode = connectedNode.newSubscriber(topicName, msgType);
				subNode.addMessageListener(new MessageListener<std_msgs.Float64>() {     
					@Override
					public void onNewMessage(std_msgs.Float64 message) {
						//subData.put(topicName, message.getData());
					}
				});
			}
			}
			rosconn.addToCheckList(nodeName);
	}
	

	@Override
	public GraphName getDefaultNodeName() {
		return GraphName.of(nodeName);
	}
}
