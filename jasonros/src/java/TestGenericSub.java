
import org.ros.message.MessageListener;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Subscriber;
import jros.internal.GenericSub;

public class TestGenericSub extends GenericSub{
	@Override
	public void subProc(ConnectedNode connectedNode, String topicName, String msgType) {
		System.out.println("----subProc----");
		Subscriber<jason_msgs.action> subNode = connectedNode.newSubscriber(topicName, msgType);
		subNode.addMessageListener(new MessageListener<jason_msgs.action>() {     
			@Override
			public void onNewMessage(jason_msgs.action message) {
				String agent = message.getAgent();
				String action = message.getAction();
				System.out.print(agent+": "+action+"( ");
				for(String s : message.getParameters()){
					System.out.print(s+" ");
				}
				System.out.println(")");
				subNode.shutdown();
			}
		});
	}
}
