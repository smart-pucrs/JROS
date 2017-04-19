
import org.ros.message.MessageListener;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Subscriber;

import jros.internal.GenericSub;

public class TestGenericSub extends GenericSub{
	@Override
	public void subProc(ConnectedNode connectedNode, String topicName, String msgType) {
		System.out.println("subProc!!!!!");
		Subscriber<sensor_msgs.Temperature> subNode = connectedNode.newSubscriber(topicName, msgType);
		subNode.addMessageListener(new MessageListener<sensor_msgs.Temperature>() {     
			@Override
			public void onNewMessage(sensor_msgs.Temperature message) {
				double temp = message.getTemperature();
				System.out.println("Temperature: " + temp);
				subNode.shutdown();
			}
		});
	}
}
