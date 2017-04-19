package jros.internal;

import org.ros.node.ConnectedNode;

public interface IGenericSub {
	void subProc(ConnectedNode connectedNode, String topicName, String msgType);
}
