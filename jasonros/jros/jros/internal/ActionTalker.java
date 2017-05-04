package jros.internal;

import java.util.List;

import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;

public class ActionTalker extends AbstractNodeMain{
	
	private String action = "";
	private String agName;
	private String remoteAgName;
	private List<String> parameters;
	private long pRate;
	
	public ActionTalker(String remoteAgName,long pRate){
		this.remoteAgName = remoteAgName;
		this.pRate = pRate;
	}
	
	public void setNewAction(String agName, String action, List<String> parameters){
		this.agName = agName;
		this.action = action;
		this.parameters = parameters;
	}
	
	@Override
	public GraphName getDefaultNodeName() {
		return GraphName.of(remoteAgName+"/actionNode");
	}
	
	@Override
	public void onStart(final ConnectedNode connectedNode){
		System.out.println("onStart!!!");
		Publisher<jason_msgs.action> actionPub = connectedNode.newPublisher(remoteAgName+"/jaction",jason_msgs.action._TYPE);
		connectedNode.executeCancellableLoop(new CancellableLoop(){
			@Override
			protected void loop() throws InterruptedException{
				if(!action.isEmpty()){
					jason_msgs.action actionMsg = actionPub.newMessage();
					actionMsg.setAgent(agName);
					actionMsg.setAction(action);
					actionMsg.setParameters(parameters);
					actionPub.publish(actionMsg);
					action = "";
				}
				Thread.sleep(pRate);
			}
		});
	}
}
