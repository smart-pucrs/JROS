package jros.internal.Messages.std_msgs;

import java.util.ArrayList;

import org.ros.node.topic.Publisher;

import jason.RevisionFailedException;
import jason.asSemantics.Unifier;
import jros.internal.PublisherObject;
import jros.internal.SubscriberData;
import jros.internal.SubscriberObject;

public class Int32 {
	private SubscriberObject subObj;
	private PublisherObject pubObj;
	private ArrayList<SubscriberData> subData;
	private ArrayList<Object> dataP;
	
	public Int32(Object dataObj){
		if(dataObj instanceof SubscriberObject){
			this.subObj = (SubscriberObject)dataObj;
			this.subData = this.subObj.getSubData();
		}
		
		if(dataObj instanceof PublisherObject){
			this.pubObj = (PublisherObject)dataObj;
		}
	}
	
	
	public void msgExecSub(std_msgs.Int32 message){
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
	
	public void msgExecPub(std_msgs.Int32 message, Publisher<std_msgs.Int32> pubNode) throws InterruptedException{
		dataP = pubObj.getDataP();
		if(dataP != null){
			double value = (double)dataP.get(0);
			message.setData((int)value);
		}
			pubNode.publish(message);
	}

}
