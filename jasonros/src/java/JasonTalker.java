
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

//import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
//import org.ros.node.topic.Publisher;
//import hanse_msgs.sollSpeed;

public class JasonTalker extends AbstractNodeMain {
	
	private ArrayList<DataClass> pubTopics;
	private String nodeName;
	private long pRate;
	private ROSConnection rosconn;
	
	public JasonTalker(String nodeName, ArrayList<DataClass> pubTopics, long pRate, ROSConnection rosconn){
		this.nodeName = nodeName;
		this.pubTopics = pubTopics;
		this.rosconn = rosconn;
		this.pRate = pRate;
	}
	
	public String getNodeName(){
		return nodeName;
	}
	
	public ArrayList<DataClass> getPubList(){
		return pubTopics;
	}
	
	public boolean setTopicData(String topicName, Object data){
		for(DataClass d : pubTopics){
			if(d.getTopicName().equals(topicName)){
				d.setData(data);
				return true;
			}
		}
		return false;
	}
		
	@Override
	public GraphName getDefaultNodeName() {
		return GraphName.of(nodeName);
	}
	
	@Override
	public void onStart(final ConnectedNode connectedNode) {		
		for(DataClass d : pubTopics){
			String topicName = d.getTopicName();
			String msgType = d.getMsgType();
			Object data = d.getData();
			try {
				new PublisherObject(connectedNode, nodeName, topicName, msgType, data, pRate, rosconn);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}