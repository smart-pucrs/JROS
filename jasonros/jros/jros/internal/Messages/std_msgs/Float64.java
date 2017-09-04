package jros.internal.Messages.std_msgs;

import java.util.ArrayList;

import jason.RevisionFailedException;
import jason.asSemantics.Unifier;
import jros.internal.SubscriberData;
import jros.internal.SubscriberObject;

public class Float64 {
	
	private SubscriberObject subObj;
	private ArrayList<SubscriberData> subData;
	
	public Float64(SubscriberObject subObj){
		this.subObj = subObj;
		this.subData = subObj.getSubData();
	}
	
	public void msgExec(std_msgs.Float64 message){
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

}
