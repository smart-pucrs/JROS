package jros.internal;

import java.util.List;

import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;

public class ActionTalker extends AbstractNodeMain{
	
	private String action;
	private String agName;
	private List<String> parameters;
	private long pRate;
	
	public ActionTalker(String agName, String action, List<String> parameters, long pRate){
		this.agName = agName;
		this.action = action;
		this.parameters = parameters;
		this.pRate = pRate;
	}
	
	public void setNewAction(String agName, String action, List<String> parameters){
		this.agName = agName;
		this.action = action;
		this.parameters = parameters;
	}
	
	@Override
	public GraphName getDefaultNodeName() {
		return GraphName.of("Jason/pubNode");
	}
	
	@Override
	public void onStart(final ConnectedNode connectedNode){
		System.out.println("onStart!!!");
		Publisher<jason_msgs.action> actionPub = connectedNode.newPublisher("/jtopics/action",jason_msgs.action._TYPE);
		connectedNode.executeCancellableLoop(new CancellableLoop(){
			@Override
			protected void loop() throws InterruptedException{
				jason_msgs.action actionMsg = actionPub.newMessage();
				actionMsg.setAgent(agName);
				actionMsg.setAction(action);
				actionMsg.setParameters(parameters);
				actionPub.publish(actionMsg);
				Thread.sleep(pRate);
			}
		});
	}
}
