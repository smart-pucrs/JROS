package jros.internal;


import java.util.ArrayList;
import java.util.HashMap;

//import java.util.HashMap;
//import java.util.Set;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.Node;

import jason.RevisionFailedException;
import jason.asSemantics.Agent;
import jason.asSyntax.ASSyntax;
import jason.asSyntax.parser.ParseException;

public class JasonListener extends AbstractNodeMain {
	
	//private HashMap<String, String> subTopics;
	private ArrayList<Object> subTopics;
	//private ArrayList<SubscriberObject> subList = new ArrayList<SubscriberObject>();
	private HashMap<String,SubscriberObject> subList = new HashMap<String,SubscriberObject>();
	private String nodeName;
	private ROSConnection rosconn;
	private Agent ag;
	
	//public JasonListener(String nodeName, HashMap<String, String> subTopics, ROSConnection rosconn){
	public JasonListener(Agent ag,String nodeName, ArrayList<Object> subTopics, ROSConnection rosconn){
		this.subTopics = subTopics;
		this.nodeName = nodeName;
		this.rosconn = rosconn;
		this.ag = ag;
	}
	
	public Object searchTopic(String topicName){
		/*for(SubscriberObject sub : subList){
			if(sub.getTopicName().equals(topicName))
				return sub.getLastStateData(topicName);
		}*/
		return subList.get(topicName).getLastStateData(topicName);
	}
	
	public boolean searchLT(String topicName, Object value){
		/*for(SubscriberObject sub : subList){
			if(sub.getTopicName().equals(topicName)){
				return sub.searchLessThanState(topicName, value);
			}
		}
		return false;*/
		return subList.get(topicName).searchLessThanState(topicName,value);
	}
	
	public void clearDataList(String topicName){
		/*for(SubscriberObject sub : subList){
			if(sub.getTopicName().equals(topicName)){
				sub.clearSubDataList();
				return;
			}
		}*/
		subList.get(topicName).clearSubDataList();
		return;
	}
	
	public boolean searchBT(String topicName, Object value){
		/*for(SubscriberObject sub : subList){
			if(sub.getTopicName().equals(topicName)){
				return sub.searchBiggerThanState(topicName, value);
			}
		}
		return false;*/
		return subList.get(topicName).searchBiggerThanState(topicName, value);
	}
	
	public boolean searchExact(String topicName, Object value){
		/*for(SubscriberObject sub : subList){
			if(sub.getTopicName().equals(topicName)){
				return sub.searchExactState(topicName, value);
			}
		}
		return false;*/
		
		return subList.get(topicName).searchExactState(topicName, value);
	}
	
	@Override
	public GraphName getDefaultNodeName() {
		return GraphName.of(nodeName);
	}

	@Override
	public void onShutdownComplete(Node node){
		System.exit(0);
	}
	
	@Override
	public void onStart(ConnectedNode connectedNode){
		//Set<String> subKeys = subTopics.keySet();
		for(Object d : subTopics){
			SubscriberObject sub = new SubscriberObject(connectedNode, nodeName, d , rosconn);
			//subList.add(sub);
			subList.put(((SDataClass)d).getTopicName(), sub);
		}
	}
}