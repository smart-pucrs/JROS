package jros.internal;

import java.util.Iterator;
import java.util.List;

import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Subscriber;

import jason.RevisionFailedException;
import jason.asSemantics.Agent;
import jason.asSemantics.Unifier;
import jason.asSyntax.ASSyntax;
import jason.asSyntax.Literal;
import jason.asSyntax.PredicateIndicator;
import jason.asSyntax.parser.ParseException;
import jason_msgs.perception;

public class PerceptionListener extends AbstractNodeMain{
	private Agent ag;
	private String remoteAgName;
	private byte lastid,id;
	@Override
	public GraphName getDefaultNodeName() {
		return GraphName.of(remoteAgName+"/perceptionNode");
	}
	
	public PerceptionListener(String remoteAgName){
		this.remoteAgName = remoteAgName;
	}
	
	public void setAg(Agent ag){
		this.ag = ag;
	}
	
	private void parsePerception(String s){
		String sToParse;
		PredicateIndicator pi;
		Literal l = null;
		try{
			if(s.charAt(0) == '@'){
				sToParse = s.substring(1, s.length());
				l = ASSyntax.parseLiteral(sToParse);
				pi = new PredicateIndicator(l.getFunctor(),l.getArity());
				ag.getBB().abolish(pi);
				ag.addBel(l);
			}
			else{
				sToParse = s;
				l = ASSyntax.parseLiteral(sToParse);
				if(!ag.believes(l, new Unifier()))
					ag.addBel(l);
			}
		}catch(RevisionFailedException|ParseException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void onStart(ConnectedNode connectedNode){
		Subscriber<jason_msgs.perception> perceptionSub = connectedNode.newSubscriber(remoteAgName+"/jperceptions", jason_msgs.perception._TYPE);
		perceptionSub.addMessageListener(new MessageListener<jason_msgs.perception>() {
			@Override
			public void onNewMessage(jason_msgs.perception message) {
				id = message.getId();
				if(id != lastid){				
					lastid = id;
					List<String> pList = message.getPerceptions();
					for(String s : pList)
						parsePerception(s);
				}
			} 
		});
	}

}
