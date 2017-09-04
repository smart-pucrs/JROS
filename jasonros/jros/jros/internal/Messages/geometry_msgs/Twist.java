package jros.internal.Messages.geometry_msgs;

import java.util.ArrayList;
import java.util.List;

import geometry_msgs.Vector3;
import jason.RevisionFailedException;
import jason.asSemantics.Unifier;
import jros.internal.SubscriberData;
import jros.internal.SubscriberObject;

public class Twist {
	
	private SubscriberObject subObj;
	private ArrayList<SubscriberData> subData;
	
	public Twist(SubscriberObject subObj){
		this.subObj = subObj;
		this.subData = subObj.getSubData();
	}
	
	public void msgExec(geometry_msgs.Twist message){
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
			dc = new SubscriberData(subObj.getTopicName(), l);
			subData.add(dc);
		}else{
			Object lastObj = (Object)subData.get(subData.size()-1).getData();
			if(!lastObj.equals(l)){
				dc = new SubscriberData(subObj.getTopicName(), l);
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
