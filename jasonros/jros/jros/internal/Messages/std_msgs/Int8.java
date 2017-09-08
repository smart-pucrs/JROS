package jros.internal.Messages.std_msgs;

import java.util.ArrayList;

import org.ros.node.topic.Publisher;

import jason.RevisionFailedException;
import jason.asSemantics.Unifier;
import jros.internal.PublisherObject;
import jros.internal.SubscriberData;
import jros.internal.SubscriberObject;

public class Int8 {
	private SubscriberObject subObj;
	private PublisherObject pubObj;
	private ArrayList<SubscriberData> subData;
	private ArrayList<Object> dataP;
	
	public Int8(Object dataObj){
		if(dataObj instanceof SubscriberObject){
			this.subObj = (SubscriberObject)dataObj;
			this.subData = this.subObj.getSubData();
		}
		
		if(dataObj instanceof PublisherObject){
			this.pubObj = (PublisherObject)dataObj;
			this.dataP = this.pubObj.getDataP();
		}
	}
	
	
	public void msgExecSub(std_msgs.Int16 message){
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
	
	public void msgExecPub(std_msgs.Int8 message, Publisher<std_msgs.Int8> pubNode) throws InterruptedException{
		if(dataP != null){
			double value = (double)dataP.get(0);;
			message.setData((byte)value);
		}
			pubNode.publish(message);
		Thread.sleep(pubObj.getpRate());
	}

}
