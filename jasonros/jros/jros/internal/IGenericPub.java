package jros.internal;

import org.ros.node.ConnectedNode;

public interface IGenericPub {
	void pubProc(ConnectedNode connectedNode, String topicName, String msgType);
}
