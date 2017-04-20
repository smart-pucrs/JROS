

import java.util.ArrayList;
import java.util.List;

import org.ros.concurrent.CancellableLoop;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;
import jros.internal.GenericPub;


public class TestGenericPub extends GenericPub{
	@Override
	public void pubProc(ConnectedNode connectedNode,  String topicName,  String msgType){
		Publisher<jason_msgs.action> pubNode = connectedNode.newPublisher(topicName, msgType);
		connectedNode.executeCancellableLoop(new CancellableLoop() {
			@Override
			protected void loop() throws InterruptedException {
				jason_msgs.action msg = pubNode.newMessage();
				List<String> p = new ArrayList<String>();
				p.add("x");
				p.add("y");
				p.add("z");
				msg.setAction("move");
				msg.setAgent("pub agent");
				msg.setParameters(p);
				pubNode.publish(msg);
				Thread.sleep(1000);
			}
		});
	}


}
