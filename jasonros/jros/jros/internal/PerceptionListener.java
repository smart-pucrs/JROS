package jros.internal;

import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Subscriber;

import jason.RevisionFailedException;
import jason.asSemantics.Agent;
import jason.asSyntax.Literal;
import jason_msgs.perception;

public class PerceptionListener extends AbstractNodeMain{
	private Agent ag;
	@Override
	public GraphName getDefaultNodeName() {
		return GraphName.of("Jason/subNode");
	}
	
	public PerceptionListener(Agent ag){
		this.ag = ag;
	}
	
	@Override
	public void onStart(ConnectedNode connectedNode){
		Subscriber<jason_msgs.perception> perceptionSub = connectedNode.newSubscriber("/jtopics/perception", jason_msgs.perception._TYPE);
		perceptionSub.addMessageListener(new MessageListener<jason_msgs.perception>() {
			@Override
			public void onNewMessage(jason_msgs.perception message) {
				Literal l = Literal.parseLiteral(message.getPerception());
				try {
					ag.addBel(l);
				} catch (RevisionFailedException e) {
					e.printStackTrace();
				}
			} 
		});
	}

}
