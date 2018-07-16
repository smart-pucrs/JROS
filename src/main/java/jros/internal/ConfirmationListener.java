package jros.internal;

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
import jason.asSyntax.parser.ParseException;
import jason_msgs.perception;

public class ConfirmationListener extends AbstractNodeMain{
	private String lastBel = "";
	private String remoteAgName;
	private Agent ag;
	@Override
	public GraphName getDefaultNodeName() {
		return GraphName.of(remoteAgName+"/confirmationNode");
	}
	
	public ConfirmationListener(String remoteAgName){
		this.remoteAgName = remoteAgName;
	}
	
	
	public void setAg(Agent ag){
		this.ag = ag;
	}
	
	@Override
	public void onStart(ConnectedNode connectedNode){
		Subscriber<std_msgs.String> confirmationSub = connectedNode.newSubscriber(remoteAgName+"/jconfirmation", std_msgs.String._TYPE);
		confirmationSub.addMessageListener(new MessageListener<std_msgs.String>() {
			@Override
			public void onNewMessage(std_msgs.String message) {
				String msg = message.getData();
				//System.out.println("jason recv:"+msg);
				try {
					if(!lastBel.equals(msg)){
						lastBel = msg;
						Literal bel = ASSyntax.parseLiteral("lastJROSAction("+msg+")");
						if(!ag.believes(bel, new Unifier()))
							ag.addBel(bel);
					}
				} catch (RevisionFailedException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
		});
	}

}
