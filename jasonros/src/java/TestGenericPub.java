

import java.util.ArrayList;
import java.util.List;

import org.ros.concurrent.CancellableLoop;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;

import jason.asSemantics.Unifier;
import jason.asSyntax.Term;
import jros.internal.GenericPub;


public class TestGenericPub extends GenericPub{
	@Override
	public void pubProc(ConnectedNode connectedNode,  String topicName,  String msgType, Unifier un, Term[] terms){
		Publisher<jason_msgs.perception> pubNode = connectedNode.newPublisher(topicName, msgType);
		connectedNode.executeCancellableLoop(new CancellableLoop() {
			@Override
			protected void loop() throws InterruptedException {
				jason_msgs.perception msg = pubNode.newMessage();
				msg.setPerception("testP");
				pubNode.publish(msg);
				cancel();
			}
		});
	}


}
