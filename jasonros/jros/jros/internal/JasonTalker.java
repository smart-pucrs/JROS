package jros.internal;


import java.util.ArrayList;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;

public class JasonTalker extends AbstractNodeMain {
	
	private ArrayList<Object> pubTopics;
	private String nodeName;
	private long pRate;
	private ROSConnection rosconn;
	
	public JasonTalker(String nodeName, ArrayList<Object> pubTopics, long pRate, ROSConnection rosconn){
		this.nodeName = nodeName;
		this.pubTopics = pubTopics;
		this.rosconn = rosconn;
		this.pRate = pRate;
	}
	
	public String getNodeName(){
		return nodeName;
	}
	
	public ArrayList<Object> getPubList(){
		return pubTopics;
	}
	
	public boolean setTopicData(String topicName, Object data){
		for(Object o : pubTopics){
			if(((DataClass)o).getTopicName().equals(topicName)){
				((DataClass)o).setData(data);
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
		System.out.println("OnStart JasonTalker");
		for(Object d : pubTopics){
			try {
				new PublisherObject(connectedNode, nodeName, d, pRate, rosconn);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}