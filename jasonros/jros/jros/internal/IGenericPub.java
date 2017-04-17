package jros.internal;

import org.ros.node.ConnectedNode;

public interface IGenericPub {
	boolean pubProc(ConnectedNode connectedNode, String topicName, String msgType);
}
