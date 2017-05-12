package jros.internal;


import java.util.ArrayList;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;

import jason.RevisionFailedException;
import jason.asSemantics.Agent;
import jason.asSyntax.ASSyntax;
import jason.asSyntax.parser.ParseException;

public class JasonTalker extends AbstractNodeMain {
	
	private ArrayList<Object> pubTopics;
	private ArrayList<PublisherObject> pubList = new ArrayList<PublisherObject>();
	private String nodeName;
	private long pRate;
	private ROSConnection rosconn;
	private Agent ag;
	
	public JasonTalker(Agent ag,String nodeName, ArrayList<Object> pubTopics, long pRate, ROSConnection rosconn){
		this.nodeName = nodeName;
		this.pubTopics = pubTopics;
		this.rosconn = rosconn;
		this.pRate = pRate;
		this.ag = ag;
	}
	
	public String getNodeName(){
		return nodeName;
	}
	
	public ArrayList<Object> getPubList(){
		return pubTopics;
	}
	
	public boolean setTopicData(String topicName, Object data){
		for(PublisherObject po : pubList){
			if(po.getTopicName().equals(topicName)){
				po.setData(data);
				return true;
			}
		}
		return false;
	}
		
	@Override
	public GraphName getDefaultNodeName() {
		return GraphName.of(nodeName);
	}
	
	@Override
	public void onStart(final ConnectedNode connectedNode) {	
		for(Object d : pubTopics){
			try {
			pubList.add(new PublisherObject(connectedNode, nodeName, d, pRate, rosconn));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}