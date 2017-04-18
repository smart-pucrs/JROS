package jros.internal;


import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Set;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.Node;

public class JasonListener extends AbstractNodeMain {
	
	//private HashMap<String, String> subTopics;
	private ArrayList<DataClass> subTopics;
	private ArrayList<SubscriberObject> subList = new ArrayList<SubscriberObject>();
	private String nodeName;
	private ROSConnection rosconn;
	
	//public JasonListener(String nodeName, HashMap<String, String> subTopics, ROSConnection rosconn){
	public JasonListener(String nodeName, ArrayList<DataClass> subTopics, ROSConnection rosconn){
		this.subTopics = subTopics;
		this.nodeName = nodeName;
		this.rosconn = rosconn;
	}
	
	public Object searchTopic(String topicName){
		for(SubscriberObject sub : subList)
			if(sub.getTopicName().equals(topicName))
				return sub.getLastStateData(topicName);
		return null;
	}
	
	public boolean searchLT(String topicName, Object value){
		for(SubscriberObject sub : subList){
			if(sub.getTopicName().equals(topicName)){
				return sub.searchLessThanState(topicName, value);
			}
		}
		return false;
	}
	
	public void clearDataList(String topicName){
		for(SubscriberObject sub : subList){
			if(sub.getTopicName().equals(topicName)){
				sub.clearSubDataList();
				return;
			}
		}
	}
	
	public boolean searchBT(String topicName, Object value){
		for(SubscriberObject sub : subList){
			if(sub.getTopicName().equals(topicName)){
				return sub.searchBiggerThanState(topicName, value);
			}
		}
		return false;
	}
	
	public boolean searchExact(String topicName, Object value){
		for(SubscriberObject sub : subList){
			if(sub.getTopicName().equals(topicName)){
				return sub.searchExactState(topicName, value);
			}
		}
		return false;
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
		for(DataClass d : subTopics){
			SubscriberObject sub = new SubscriberObject(connectedNode, nodeName, d.getTopicName(), d.getMsgType(), rosconn);
			subList.add(sub);
		}
	}
}