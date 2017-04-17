
import java.util.ArrayList;
import java.util.HashMap;

import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;

public class PublisherObject extends AbstractNodeMain{
	private String nodeName;
	//TODO:tratar dados numericos do jason
	public PublisherObject(ConnectedNode connectedNode, String nodeName, String topicName, String msgType, Object data, long pRate, ROSConnection rosconn) throws InterruptedException{
		this.nodeName = nodeName;
		switch(msgType){
		case "std_msgs/String":{
			Publisher<std_msgs.String> pubNode = connectedNode.newPublisher(topicName, msgType);
			connectedNode.executeCancellableLoop(new CancellableLoop() {
				@Override
				protected void loop() throws InterruptedException {
					std_msgs.String msg = pubNode.newMessage();
					msg.setData((String)data);
					//msg.setData((String)pubList.get(topicName).get(msgType));
					pubNode.publish(msg);
					Thread.sleep(pRate);
				}
			});
		}
		break;
		case "hanse_msgs/sollSpeed":{
			Publisher<hanse_msgs.sollSpeed> pubNode = connectedNode.newPublisher(topicName, msgType);
			connectedNode.executeCancellableLoop(new CancellableLoop() {
				@Override
				protected void loop() throws InterruptedException {
					hanse_msgs.sollSpeed msg = pubNode.newMessage();
					msg.setData(((Double)data).byteValue());
					pubNode.publish(msg);
					Thread.sleep(pRate);
				}
			});
		}
		break;
		case "std_msgs/Int8":{
			Publisher<std_msgs.Int8> pubNode = connectedNode.newPublisher(topicName, msgType);
			connectedNode.executeCancellableLoop(new CancellableLoop() {
				@Override
				protected void loop() throws InterruptedException {
					std_msgs.Int8 msg = pubNode.newMessage();
					msg.setData(((Byte)data).byteValue());
					pubNode.publish(msg);
					Thread.sleep(pRate);
				}
			});
		}
		break;
		case "std_msgs/Int16":{
			Publisher<std_msgs.Int16> pubNode = connectedNode.newPublisher(topicName, msgType);
			connectedNode.executeCancellableLoop(new CancellableLoop() {
				@Override
				protected void loop() throws InterruptedException {
					std_msgs.Int16 msg = pubNode.newMessage();
					msg.setData(((Double)data).shortValue());
					pubNode.publish(msg);
					Thread.sleep(pRate);
				}
			});
		}
		break;
		case "std_msgs/Int32":{
			Publisher<std_msgs.Int32> pubNode = connectedNode.newPublisher(topicName, msgType);
			connectedNode.executeCancellableLoop(new CancellableLoop() {
				@Override
				protected void loop() throws InterruptedException {
					std_msgs.Int32 msg = pubNode.newMessage();
					msg.setData(((Double)data).intValue());
					pubNode.publish(msg);
					Thread.sleep(pRate);
				}
			});
		}
		break;
		case "std_msgs/Int64":{
			Publisher<std_msgs.Int64> pubNode = connectedNode.newPublisher(topicName, msgType);
			connectedNode.executeCancellableLoop(new CancellableLoop() {
				@Override
				protected void loop() throws InterruptedException {
					std_msgs.Int64 msg = pubNode.newMessage();
					msg.setData(((Double)data).longValue());
					pubNode.publish(msg);
					Thread.sleep(pRate);
				}
			});
		}
		break;
		case "std_msgs/Float32":{
			Publisher<std_msgs.Float32> pubNode = connectedNode.newPublisher(topicName, msgType);
			connectedNode.executeCancellableLoop(new CancellableLoop() {
				@Override
				protected void loop() throws InterruptedException {
					std_msgs.Float32 msg = pubNode.newMessage();
					msg.setData(((Double)data).floatValue());
					pubNode.publish(msg);
					Thread.sleep(pRate);
				}
			});
		}
		break;
		case "std_msgs/Float64":{
			Publisher<std_msgs.Float64> pubNode = connectedNode.newPublisher(topicName, msgType);
			connectedNode.executeCancellableLoop(new CancellableLoop() {
				@Override
				protected void loop() throws InterruptedException {
					std_msgs.Float64 msg = pubNode.newMessage();
					msg.setData(((Double)data).doubleValue());
					pubNode.publish(msg);
					Thread.sleep(pRate);
				}
			});
		}
		}
		//Thread.sleep(pRate);
		rosconn.addToCheckList(nodeName);
	}
	
	@Override
	public GraphName getDefaultNodeName() {
		return GraphName.of(nodeName);
	}
}
