

import java.util.ArrayList;
import java.util.List;

import org.ros.concurrent.CancellableLoop;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;

import geometry_msgs.Vector3;
import jason.asSemantics.Unifier;
import jason.asSyntax.Term;
import jros.internal.GenericPub;


public class TestGenericPub extends GenericPub{
	private double acum = 0;
	@Override
	public void pubProc(ConnectedNode connectedNode,  String topicName,  String msgType, Unifier un, Term[] terms){
		Publisher<geometry_msgs.Twist> pubNode = connectedNode.newPublisher(topicName, msgType);
		connectedNode.executeCancellableLoop(new CancellableLoop() {
			@Override
			protected void loop() throws InterruptedException {
				geometry_msgs.Twist msg = pubNode.newMessage();
				Vector3 va = msg.getAngular();
				Vector3 vl = msg.getLinear();
				vl.setX(acum+=0.1);
				msg.setAngular(va);
				msg.setLinear(vl);				
				pubNode.publish(msg);
				Thread.sleep(500);
			}
		});
	}


}
