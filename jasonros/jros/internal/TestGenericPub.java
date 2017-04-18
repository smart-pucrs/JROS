package jros.internal;

import org.ros.concurrent.CancellableLoop;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;

public class TestGenericPub extends GenericPub{

	@Override
	public boolean pubProc(ConnectedNode connectedNode, String topicName, String msgType){
		Publisher<std_msgs.String> pubNode = connectedNode.newPublisher(topicName, msgType);
		connectedNode.executeCancellableLoop(new CancellableLoop() {
			@Override
			protected void loop() throws InterruptedException {
				std_msgs.String msg = pubNode.newMessage();
				msg.setData("Testing...");
				pubNode.publish(msg);
				Thread.sleep(1000);
			}
		});
		return true;
	}

}
