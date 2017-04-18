package jros.internal;


import org.ros.node.NodeMainExecutor;
import org.ros.node.NodeConfiguration;
import org.ros.internal.node.client.SlaveClient;
import org.ros.namespace.GraphName;
import org.ros.node.DefaultNodeMainExecutor;
import java.net.URI;
import java.util.ArrayList;

public class ROSConnection{
	
	private JasonTalker publisherNode;
	private JasonListener subscriberNode;
	private ArrayList<String> nCheckList = new ArrayList<String>();
	//private HashMap<String, String> subTopics = new HashMap<String, String>();
	private ArrayList<DataClass> subTopics = new ArrayList<DataClass>();
	private ArrayList<Object> pubTopics = new ArrayList<Object>();
	private NodeMainExecutor nodeMainExecutor = DefaultNodeMainExecutor.newDefault();
	private NodeConfiguration nodeConfiguration;
	private ArrayList<JasonTalker> talkerList = new ArrayList<JasonTalker>();
	URI masteruri;
	
	public boolean rosConfig(String rosIP, String rosPort){
		masteruri = URI.create("http://"+rosIP+":"+rosPort);
		nodeConfiguration = NodeConfiguration.newPublic(rosIP, masteruri);
		return checkConn();
	}
	
	private boolean checkConn(){
		try{
			SlaveClient slaveClient = new SlaveClient(GraphName.of("JRos/check"), masteruri);
			slaveClient.getPid();
		}catch(RuntimeException e){
			return false;
		}
		return true;
	}
	
	public void addToCheckList(String nodeName){
		nCheckList.add(nodeName);
	}
	
	public ArrayList<String> getList(){
		return nCheckList;
	}
	
	public boolean createSubNode(String nodeName) throws InterruptedException{
		if(subscriberNode == null){
			try{
				subscriberNode = new JasonListener(nodeName, subTopics, this);
				nodeMainExecutor.execute(subscriberNode, nodeConfiguration);
				while(!nCheckList.contains(nodeName)) Thread.sleep(10);
				nCheckList.remove(nodeName);
				System.out.println("ROSConnection: subNode created.");
				return true;
			}catch(RuntimeException e){
				System.out.println("Connection error!");
			}
		}else
			System.out.println("An subscriber node already exists!");
		return false;
	}
	
	public boolean createPubNode(String nodeName, long pRate) throws InterruptedException{
		publisherNode = new JasonTalker(nodeName, pubTopics, pRate, this);
		if(publisherNode != null){
			try{
				nodeMainExecutor.execute(publisherNode, nodeConfiguration);
				while(!nCheckList.contains(nodeName)) Thread.sleep(10);
				nCheckList.remove(nodeName);
				talkerList.add(publisherNode);
				pubTopics = new ArrayList<Object>();
				return true;
			}catch(RuntimeException e){
				System.out.println("Connection error!");
			}
		}
		return false;
	}
	
	
	
	public boolean addSubTopic(String topicName, String msgType){
		//subTopics.put(topicName, msgType);
		subTopics.add(new DataClass(topicName, msgType,null));
		return true;
	}
	
	public boolean addPubTopic(String topicName, String msgType, Object data){
		return pubTopics.add(new DataClass(topicName,msgType,data));
	}
	
	public boolean addPubGenericTopic(String topicName, String msgType, String className){
		return pubTopics.add(new GDataClass(topicName,msgType,className));
	}
	
	public boolean subExists(){
		return !(subscriberNode == null);
	}
	
	private boolean pubExists(){
		return !talkerList.isEmpty();
	}
	
	public boolean pubExists(String topicName){
		if(pubExists())
			for(JasonTalker t : talkerList){
				ArrayList<Object> l = t.getPubList();
				for(Object o : l)
					if(((DataClass)o).getTopicName().equals(topicName))
						return true;
			}
		return false;
	}
	
	public JasonListener getListenerInstance(){
		return subscriberNode;
	}
	
	public ArrayList<JasonTalker> getTalkerList(){
		return talkerList;
	}
	
	public void shutdown(){
		nodeMainExecutor.shutdown();
	}
}