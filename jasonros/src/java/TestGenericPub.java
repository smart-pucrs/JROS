

import org.ros.concurrent.CancellableLoop;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;
import jros.internal.GenericPub;


public class TestGenericPub extends GenericPub{
	@Override
	public void pubProc(ConnectedNode connectedNode,  String topicName,  String msgType){
		System.out.println("pubProc!!!!!");
		Publisher<sensor_msgs.Temperature> pubNode = connectedNode.newPublisher(topicName, msgType);
		connectedNode.executeCancellableLoop(new CancellableLoop() {
			@Override
			protected void loop() throws InterruptedException {
				sensor_msgs.Temperature msg = pubNode.newMessage();
				msg.setTemperature(6.6);
				pubNode.publish(msg);
				Thread.sleep(1000);
			}
		});
	}


}
