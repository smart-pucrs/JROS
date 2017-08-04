package jros.internal;


import org.ros.node.NodeMainExecutor;

import jason.asSemantics.Agent;
import jason.asSemantics.Unifier;
import jason.asSemantics.DefaultInternalAction;
import jason.asSyntax.Term;
import jason.stdlib.*;
import org.ros.node.NodeConfiguration;
import org.ros.internal.node.client.SlaveClient;
import org.ros.namespace.GraphName;
import org.ros.node.DefaultNodeMainExecutor;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class ROSConnection{
	private String remoteAgName;
	private JasonTalker publisherNode;
	private ActionTalker actionNode;
	private JasonListener subscriberNode;
	private PerceptionListener perceptNode;
	private ConfirmationListener confirmNode;
	private Agent ag;
	private List<String> fList;
	private ArrayList<String> nCheckList = new ArrayList<String>();
	//private HashMap<String, String> subTopics = new HashMap<String, String>();
	private ArrayList<Object> subTopics = new ArrayList<Object>();
	private ArrayList<Object> pubTopics = new ArrayList<Object>();
	private NodeMainExecutor nodeMainExecutor = DefaultNodeMainExecutor.newDefault();
	private NodeConfiguration nodeConfiguration;
	private ArrayList<JasonTalker> talkerList = new ArrayList<JasonTalker>();
	URI masteruri;
	
	public ROSConnection(Agent ag, String remoteAgName){
		this.remoteAgName = remoteAgName;
		this.ag = ag;
	}
	
	public boolean rosConfig(String rosIP, String rosPort, List<String> fList) throws InterruptedException{
		this.fList = fList;
		return rosConfig(rosIP, rosPort);
	}
	
	public boolean rosConfig(String rosIP, String rosPort) throws InterruptedException{
		masteruri = URI.create("http://"+rosIP+":"+rosPort);
		nodeConfiguration = NodeConfiguration.newPublic(rosIP, masteruri);
		boolean res = checkConn();
		if(res ){
			confirmNode = new ConfirmationListener(remoteAgName);
			confirmNode.setAg(ag);
			perceptNode = new PerceptionListener(remoteAgName);
			actionNode = new ActionTalker(remoteAgName,500);
			nodeMainExecutor.execute(confirmNode, nodeConfiguration);
			nodeMainExecutor.execute(perceptNode, nodeConfiguration);
			nodeMainExecutor.execute(actionNode, nodeConfiguration);
		}
		return res;
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
	
	private boolean createSubNodeP(String nodeName) throws InterruptedException{
		try{
			subscriberNode = new JasonListener(ag,nodeName, subTopics, this);
			nodeMainExecutor.execute(subscriberNode, nodeConfiguration);
			while(!nCheckList.contains(nodeName)) Thread.sleep(10);
			nCheckList.remove(nodeName);
			System.out.println("ROSConnection: subNode created.");
			return true;
		}catch(RuntimeException e){
			System.out.println("Connection error!");
		}
		return false;
	}
	
	public boolean createSubNode(String nodeName) throws InterruptedException{
		if(subscriberNode == null){
			return createSubNodeP(nodeName);
		}else
			System.out.println("An subscriber node already exists!");
		return false;
	}
	
	private boolean createPubNodeP(String nodeName, long pRate) throws InterruptedException{
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
		return false;
	}
	
	public boolean sendAction(String agName, String action, List<String> parameters) throws InterruptedException{
		actionNode.setNewAction(agName, action, parameters);
		return true;
	}
	
	public boolean listenPerceptions(){
		perceptNode.setAg(ag);
		return true;
	}
	
	public boolean createPubNode(String nodeName, long pRate) throws InterruptedException{
		publisherNode = new JasonTalker(ag,nodeName, pubTopics, pRate, this);
		if(publisherNode != null){
			return createPubNodeP(nodeName, pRate);
		}
		return false;
	}
	
	
	
	public boolean addSubTopic(String topicName, String msgType){
		//subTopics.put(topicName, msgType);
		subTopics.add(new DataClass(topicName, msgType,null));
		return true;
	}
	
	public boolean addSubGenericTopic(String topicName, String msgType, String className, Unifier un, Term[] terms){
		//subTopics.put(topicName, msgType);
		subTopics.add(new GDataClass(topicName, msgType,className,un,terms));
		return true;
	}
	
	public boolean addPubTopic(String topicName, String msgType, Object data){
		return pubTopics.add(new DataClass(topicName,msgType,data));
	}
	
	public boolean addPubGenericTopic(String topicName, String msgType, String className, Unifier un, Term[] terms){
		return pubTopics.add(new GDataClass(topicName,msgType,className,un,terms));
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