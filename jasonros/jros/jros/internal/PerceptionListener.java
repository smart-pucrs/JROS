package jros.internal;

import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;

public class PerceptionListener extends AbstractNodeMain{

	@Override
	public GraphName getDefaultNodeName() {
		return GraphName.of("Jason/subNode");
	}
	
	@Override
	public void onStart(ConnectedNode connectedNode){
		
	}

}
