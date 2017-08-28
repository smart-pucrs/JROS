package jros.internal;


import java.util.ArrayList;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;

import jason.RevisionFailedException;
import jason.asSemantics.Agent;
import jason.asSyntax.ASSyntax;
import jason.asSyntax.parser.ParseException;

public class JasonTalker extends AbstractNodeMain {
	
	private JROSNodeInfo nodeInfo;
	private PublisherObject po;
	private String nodeName;
	private long pRate;
	private ROSConnection rosconn;
	private Agent ag;
	private String action;
	
	public JasonTalker(JROSNodeInfo nodeInfo, ROSConnection rosconn){
		this.nodeInfo = nodeInfo;
		this.nodeName = nodeInfo.getNodeName();
		this.rosconn = rosconn;
		this.ag = nodeInfo.getAgent();
		this.pRate = nodeInfo.getPRate();
		this.action = null;
	}
	
	public String getNodeName(){
		return nodeName;
	}
	
	public String getAction(){
		return action;
	}
	
	public void setAction(String act){
		action = act;
	}

	
	public boolean setTopicData(ArrayList<Object> data){
		po.setData(data);
		return true;
	}
		
	@Override
	public GraphName getDefaultNodeName() {
		return GraphName.of(nodeName);
	}
	
	@Override
	public void onStart(final ConnectedNode connectedNode) {	
		try {
		po = new PublisherObject(connectedNode, nodeName, nodeInfo, pRate, rosconn);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}