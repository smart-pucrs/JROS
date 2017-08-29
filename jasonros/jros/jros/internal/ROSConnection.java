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
import java.util.HashMap;
import java.util.List;

public class ROSConnection{
	
	private String remoteAgName;
	//private JasonTalker publisherNode;
	private ActionTalker actionNode;
	//private JasonListener subscriberNode;
	private PerceptionListener perceptNode;
	private ConfirmationListener confirmNode;
	private Agent ag;
	private List<String[]> aList;
	private ArrayList<String> nCheckList = new ArrayList<String>();
	private HashMap<String, Object> nodeMap = new HashMap<String, Object>();
	//private ArrayList<Object> subTopics = new ArrayList<Object>();
	//private ArrayList<Object> pubTopics = new ArrayList<Object>();
	private NodeMainExecutor nodeMainExecutor = DefaultNodeMainExecutor.newDefault();
	private NodeConfiguration nodeConfiguration;
	private ArrayList<JasonTalker> talkerList = new ArrayList<JasonTalker>();
	private ArrayList<JasonListener> listenerList = new ArrayList<JasonListener>();
	URI masteruri;
	
	public ROSConnection(Agent ag, String remoteAgName){
		this.remoteAgName = remoteAgName;
		this.ag = ag;
	}
	
	public boolean rosConfig(String rosIP, String rosPort, List<String[]> aList) throws InterruptedException{
		System.out.println("rosconfig <<<<<<<<<<<<<<<<<");
		this.aList = aList;
		//return false;
		return rosConfig(rosIP, rosPort);
	}
	
	/*public boolean createConfigNodes(){
		//System.out.println("aList size:"+aList.size());
		for(String[] sv : this.aList){
			try {
				if(sv.length == 6)
					createPubNode(sv[0]+"NodePub",Integer.parseInt(sv[5]));
				createSubNode(sv[0]+"NodeSub");
			} catch (NumberFormatException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}*/
	
	public boolean rosConfig(String rosIP, String rosPort) throws InterruptedException{
		masteruri = URI.create("http://"+rosIP+":"+rosPort);
		nodeConfiguration = NodeConfiguration.newPublic(rosIP, masteruri);
		boolean res = checkConn();
		if(res ){
			confirmNode = new ConfirmationListener(remoteAgName);
			confirmNode.setAg(ag);
			perceptNode = new PerceptionListener(remoteAgName);
			actionNode = new ActionTalker(remoteAgName,500,ag);
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
	
	
	public boolean createSubNode(String nodeName, String action, String topicName, String messageType) throws InterruptedException{
		try{
			//subscriberNode = new JasonListener(ag,nodeName, subTopics, this);
			JROSNodeInfo jn = new JROSNodeInfo(ag, nodeName, "sub", topicName, messageType, null, null, 0);
			JasonListener jl = new JasonListener(jn, action, this);
			nodeMainExecutor.execute(jl, nodeConfiguration);
			while(!nCheckList.contains(nodeName)) Thread.sleep(10);
			nCheckList.remove(nodeName);
			listenerList.add(jl);
			System.out.println("ROSConnection: subNode created.");
			return true;
		}catch(RuntimeException e){
			System.out.println("Connection error!");
		}
		return false;
	}
	
	public JasonTalker createPubNode(String nodeName, String topicName, String messageType,
			long pRate) throws InterruptedException{
		//publisherNode = new JasonTalker(ag,nodeName, pubTopics, pRate, this);
		JROSNodeInfo jn = new JROSNodeInfo(ag, nodeName, "pub", topicName, messageType, null, null, pRate);
		JasonTalker jt = new JasonTalker(jn, this);
		jn.setJNode(jt);
		try{
			nodeMainExecutor.execute(jt, nodeConfiguration);
			while(!nCheckList.contains(nodeName)) Thread.sleep(10);
			nCheckList.remove(nodeName);
			talkerList.add(jt);
			return jt;
		}catch(RuntimeException e){
			System.out.println("Connection error!");
		}
		return null;
	}
	
	public boolean sendAction(String agName, String action, ArrayList<Object> params) throws InterruptedException{
		JasonTalker jt = (JasonTalker)nodeMap.get(action);
		if(jt != null){
			jt.setTopicData(params);
			return true;
		}
		actionNode.setNewAction(agName, action, params);
		return true;
	}
	
	public Object getDataByAction(String action){
		String topic = "";
		for(String[] p : aList)
			if(p[0].equals(action)){
				topic = p[2];
				break;
			}
		for(JasonListener jl : listenerList){
			Object o = jl.searchTopic(topic);
			if(o != null)
				return o;
		}
		return null;
	}
	
	public void mapNode(String action, Object node){
		nodeMap.put(action, node);
	}
	
	
	public boolean listenPerceptions(){
		perceptNode.setAg(ag);
		return true;
	}
	

	
	
	/*
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
	*/
	
	public boolean subExists(){
		return !listenerList.isEmpty();
	}
	
	private boolean pubExists(){
		return !talkerList.isEmpty();
	}
	
	
	public Agent getAg(){
		return ag;
	}
	
	public List<String[]> getActionList(){
		return aList;
	}
	
	//public JasonListener getListenerInstance(){
	//	return subscriberNode;
	//}
	
	public ArrayList<JasonTalker> getTalkerList(){
		return talkerList;
	}
	
	public void shutdown(){
		nodeMainExecutor.shutdown();
	}
}