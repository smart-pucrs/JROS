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
	//private List<String[]> aList;
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
	
	
	public JasonListener createSubNode(String nodeName, String action, String topicName, String messageType) throws InterruptedException{
		try{
			//subscriberNode = new JasonListener(ag,nodeName, subTopics, this);
			JROSNodeInfo jn = new JROSNodeInfo(ag, nodeName, "sub", topicName, messageType, null, null, 0);
			JasonListener jl = new JasonListener(jn, action, this);
			nodeMainExecutor.execute(jl, nodeConfiguration);
			while(!nCheckList.contains(nodeName)) Thread.sleep(10);
			nCheckList.remove(nodeName);
			listenerList.add(jl);
			System.out.println("ROSConnection: subNode created.");
			return jl;
		}catch(RuntimeException e){
			System.out.println("Connection error!");
		}
		
		System.out.println("Connection error!");
		return null;
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
		JasonListener jl = (JasonListener)nodeMap.get(action);
		if(jl != null){
			System.out.println("getDataByAction: Found!!");
			Object o = jl.getTopicData();
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
	
	public boolean subExists(){
		return !listenerList.isEmpty();
	}
	
	private boolean pubExists(){
		return !talkerList.isEmpty();
	}
	
	
	public Agent getAg(){
		return ag;
	}
	

	public JasonListener getListener(String nodeName){
		for(JasonListener jl : listenerList){
			if(jl.getNodeName().equals(nodeName))
				return jl;
		}
		return null;
	}
	
	public JasonTalker getTalker(String nodeName){
		for(JasonTalker jt : talkerList){
			if(jt.getNodeName().equals(nodeName))
				return jt;
		}
		return null;
	}
	
	/*public ArrayList<JasonTalker> getTalkerList(){
		return talkerList;
	}*/
	
	public void shutdown(){
		nodeMainExecutor.shutdown();
	}
}