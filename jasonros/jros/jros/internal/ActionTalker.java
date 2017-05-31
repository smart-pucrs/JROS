package jros.internal;

import java.util.List;
import java.util.Random;

import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;

public class ActionTalker extends AbstractNodeMain{
	
	private String action;
	private String agName;
	private String remoteAgName;
	private List<String> parameters;
	private long pRate;
	private int id,lastId;
	private Random rand = new Random();
	
	public ActionTalker(String remoteAgName,long pRate){
		this.remoteAgName = remoteAgName;
		this.pRate = pRate;
		this.action = "";
		this.id = 0;
		this.lastId = -1;
	}
	
	public void setNewAction(String agName, String action, List<String> parameters){
		this.id = rand.nextInt(Integer.MAX_VALUE);
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
		Publisher<jason_msgs.action> actionPub = connectedNode.newPublisher(remoteAgName+"/jaction",jason_msgs.action._TYPE);
		connectedNode.executeCancellableLoop(new CancellableLoop(){
			@Override
			protected void loop() throws InterruptedException{
				if(id != lastId){
					id = rand.nextInt(Integer.MAX_VALUE);
					lastId = id;
				}
				jason_msgs.action actionMsg = actionPub.newMessage();
				actionMsg.setId(id);
				actionMsg.setAgent(agName);
				actionMsg.setAction(action);
				actionMsg.setParameters(parameters);
				actionPub.publish(actionMsg);
				Thread.sleep(pRate);
			}
		});
	}
}
