package jros.internal.Messages.std_msgs;

import java.util.ArrayList;

import org.ros.node.topic.Publisher;

import jason.RevisionFailedException;
import jason.asSemantics.Unifier;
import jros.internal.PublisherObject;
import jros.internal.SubscriberData;
import jros.internal.SubscriberObject;

public class Int64 {
	private SubscriberObject subObj;
	private PublisherObject pubObj;
	private ArrayList<SubscriberData> subData;
	private ArrayList<Object> dataP;
	
	public Int64(Object dataObj){
		if(dataObj instanceof SubscriberObject){
			this.subObj = (SubscriberObject)dataObj;
			this.subData = this.subObj.getSubData();
		}
		
		if(dataObj instanceof PublisherObject){
			this.pubObj = (PublisherObject)dataObj;
			this.dataP = this.pubObj.getDataP();
		}
	}
	
	
	public void msgExecSub(std_msgs.Int64 message){
		SubscriberData dc;
		if(subData.isEmpty()){
			dc = new SubscriberData(subObj.getTopicName(), message.getData());
			subData.add(dc);
		}else{
			Object lastObj = (Object)subData.get(subData.size()-1).getData();
			if(!lastObj.equals(message.getData())){
				dc = new SubscriberData(subObj.getTopicName(), message.getData());
				subData.add(dc);
			}
		}
		if(subObj.getBelief() != null && !subObj.getAgent().believes(subObj.getBelief(), new Unifier()))
			try {
				subObj.getAgent().addBel(subObj.getBelief());
			} catch (RevisionFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void msgExecPub(std_msgs.Int64 message, Publisher<std_msgs.Int64> pubNode) throws InterruptedException{
		std_msgs.Int64 msg = pubNode.newMessage();
		if(dataP != null){
		double value = (double)dataP.get(0);;
		msg.setData((long)value);
		}
		pubNode.publish(msg);
		Thread.sleep(pubObj.getpRate());
	}

}
