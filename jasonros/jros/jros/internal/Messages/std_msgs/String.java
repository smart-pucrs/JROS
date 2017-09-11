package jros.internal.Messages.std_msgs;

import java.util.ArrayList;

import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;
import org.ros.node.topic.Subscriber;

import jason.RevisionFailedException;
import jason.asSemantics.Unifier;
import jros.internal.JROSNodeInfo;
import jros.internal.PublisherObject;
import jros.internal.ROSConnection;
import jros.internal.SubscriberData;
import jros.internal.SubscriberObject;

public class String {
	private SubscriberObject subObj;
	private PublisherObject pubObj;
	private ArrayList<SubscriberData> subData;
	private ArrayList<Object> dataP;
	
	public String(Object dataObj){
		if(dataObj instanceof SubscriberObject){
			this.subObj = (SubscriberObject)dataObj;
			this.subData = this.subObj.getSubData();
		}
		
		if(dataObj instanceof PublisherObject){
			this.pubObj = (PublisherObject)dataObj;
			this.dataP = this.pubObj.getDataP();
		}
	}
	
	public void msgExecSub(std_msgs.String message){
		//System.out.println("Novo!!!!");
		SubscriberData sd;
		if(subData.isEmpty()){
			sd = new SubscriberData(subObj.getTopicName(), message.getData());
			subData.add(sd);
		}else{
			Object lastObj = (Object)subData.get(subData.size()-1).getData();
			if(!lastObj.equals(message.getData())){
				sd = new SubscriberData(subObj.getTopicName(), message.getData());
				subData.add(sd);
			}
		}
		if(subObj.getBelief() != null && !subObj.getAgent().believes(subObj.getBelief(), new Unifier())){
			System.out.println("Jason Recv:"+subObj.getBelief().toString());
			try {
				subObj.getAgent().addBel(subObj.getBelief());
			} catch (RevisionFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void msgExecPub(std_msgs.String message, Publisher<std_msgs.String> pubNode) throws InterruptedException{
		dataP = pubObj.getDataP();
		if(dataP != null){
			message.setData((java.lang.String)dataP.get(0));
		}
		pubNode.publish(message);
	}

}
