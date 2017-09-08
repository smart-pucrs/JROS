package jros.internal.Messages.geometry_msgs;

import java.util.ArrayList;
import java.util.List;

import org.ros.node.topic.Publisher;

import geometry_msgs.Point;
import geometry_msgs.Pose;
import geometry_msgs.PoseWithCovariance;
import geometry_msgs.Quaternion;
import geometry_msgs.Vector3;
import jason.RevisionFailedException;
import jason.asSemantics.Unifier;
import jros.internal.PublisherObject;
import jros.internal.SubscriberData;
import jros.internal.SubscriberObject;

public class Twist {
	private SubscriberObject subObj;
	private PublisherObject pubObj;
	private ArrayList<SubscriberData> subData;
	private ArrayList<Object> dataP;
	
	public Twist(Object dataObj){
		if(dataObj instanceof SubscriberObject){
			this.subObj = (SubscriberObject)dataObj;
			this.subData = this.subObj.getSubData();
		}
		
		if(dataObj instanceof PublisherObject){
			this.pubObj = (PublisherObject)dataObj;
			this.dataP = this.pubObj.getDataP();
		}
	}
	
	public void msgExecSub(geometry_msgs.Twist message){
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
	
	public void msgExecPub(geometry_msgs.Twist message, Publisher<geometry_msgs.Twist> pubNode) throws InterruptedException{
		Vector3 ang = message.getAngular();
		Vector3 lin = message.getLinear();
		if(dataP != null){
			//Term[] terms = ((Term[])dataP);
			//System.out.println("terms length:"+terms.length);
			lin.setX((double)dataP.get(0));
			lin.setY((double)dataP.get(1));
			lin.setZ((double)dataP.get(2));
			ang.setX((double)dataP.get(3));
			ang.setY((double)dataP.get(4));
			ang.setZ((double)dataP.get(5));
			message.setAngular(ang);
			message.setLinear(lin);
		}
		pubNode.publish(message);
		Thread.sleep(pubObj.getpRate());
	}

}
