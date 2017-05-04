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

public class ConfirmationListener extends AbstractNodeMain{
	private String lastAction;
	private String remoteAgName;
	@Override
	public GraphName getDefaultNodeName() {
		return GraphName.of(remoteAgName+"/confirmationNode");
	}
	
	public ConfirmationListener(String remoteAgName){
		this.remoteAgName = remoteAgName;
	}
	
	public String getLastAction(){
		return lastAction;
	}
	
	public void setLastAction(String action){
		lastAction = action;
	}
	
	@Override
	public void onStart(ConnectedNode connectedNode){
		Subscriber<std_msgs.String> confirmationSub = connectedNode.newSubscriber(remoteAgName+"/jconfirmation", std_msgs.String._TYPE);
		confirmationSub.addMessageListener(new MessageListener<std_msgs.String>() {
			@Override
			public void onNewMessage(std_msgs.String message) {
				lastAction = message.getData();
			} 
		});
	}

}
