package jros.internal;

import java.util.ArrayList;

import jason.asSemantics.Agent;
import jason.asSyntax.Term;

public class JROSNodeInfo{
	String nodeName;
	String nodeType;
	String msgType;
	String topic;
	Object jNode;
	ArrayList<Object> params;
	Agent agent;
	long pRate;
	JROSNodeInfo(Agent agent, String nodeName, String nodeType, String topic, String msgType, Object jNode,
			ArrayList<Object> params, long pRate){
		this.nodeName = nodeName;
		this.nodeType = nodeType;
		this.topic = topic;
		this.msgType = msgType;
		this.jNode = jNode;
		this.agent = agent;
		this.params = params;
	}
	public String getNodeName(){
		return nodeName;
	}
	public String getNodeType(){
		return nodeType;
	}
	public String getTopic(){
		return topic;
	}
	public String getMsgType(){
		return msgType;
	}
	public Object getJNode(){
		return jNode;
	}
	public long getPRate(){
		return pRate;
	}
	public ArrayList<Object> getParams(){
		return params;
	}
	public Agent getAgent(){
		return agent;
	}
	public void setJNode(Object jNode){
		this.jNode = jNode;
	}
}