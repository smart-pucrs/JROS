package jros.internal;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.plaf.synth.SynthSeparatorUI;

import jason.asSemantics.Agent;
import jason.asSemantics.Unifier;
import jason.asSyntax.Term;

public class JMethods {
private static ConcurrentHashMap<String,ROSConnection> agMap = new ConcurrentHashMap<String,ROSConnection>();
	
	public static boolean rosConfig(String agName, Agent ag, String rosIP, String rosPort,String remoteAgName) throws InterruptedException{
		System.out.println("criou");
		ROSConnection rc = agMap.get(ag);
		if(rc == null){
			rc = new ROSConnection(ag,remoteAgName);
			agMap.put(agName, rc);
		}
		return rc.rosConfig(rosIP, rosPort);
	}
	
	public static boolean rosConfig(String agName, Agent ag, String rosIP, String rosPort,String remoteAgName, String configFile) throws InterruptedException{
		List<String> fList = null;
		ROSConnection rc = agMap.get(ag);
		Path file = Paths.get(configFile);
		try {
			fList = Files.readAllLines(file);
		} catch (IOException e) {
			System.out.println("Read file error!!!");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(rc == null){
			rc = new ROSConnection(ag,remoteAgName);
			agMap.put(agName, rc);
		}
		//if(rc.rosConfig(rosIP, rosPort,aList)){
		if(rc.rosConfig(rosIP, rosPort)){
			for(String s : fList){
				Parser p = new Parser(s, ag);
				if(p.isValidLine()){
					if(p.isSubscriber()){
						JasonListener jl = rc.createSubNode(p.nodeName(), p.actionName(), p.topicName(), 
								p.msgType());
						rc.mapNode(p.actionName(), jl);
					}	
					else if(p.isPublisher()){
						JasonTalker jt = rc.createPubNode(p.nodeName(), p.topicName(), p.msgType(), 
								p.publishRate());
						rc.mapNode(p.actionName(), jt);
					}else{
						System.out.println("Error: Node type missing!");
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}
	
	public static boolean listenPerceptions(String ag){
		ROSConnection rc = agMap.get(ag);
		return rc.listenPerceptions();
	}
	
	public static boolean sendAction(String ag, String action, ArrayList<Object> params) throws InterruptedException{
		ROSConnection rc = agMap.get(ag);
		return rc.sendAction(ag.toString(), action, params);
	}
	
	public static boolean createSubNode(String ag, String nodeName, String topicName,
			String messageType) throws InterruptedException{
		ROSConnection rc = agMap.get(ag);
		return rc.createSubNode(nodeName, null, topicName, messageType) != null;
	}
	
	public static boolean createPubNode(String ag, String nodeName, String topicName,
			String messageType, long pRate) throws InterruptedException{
		ROSConnection rc = agMap.get(ag);
		return rc.createPubNode(nodeName, topicName, messageType, pRate) != null;
	}
	
	public static boolean searchLT(String ag, String nodeName, Object value){
		ROSConnection rc = agMap.get(ag);	
		JasonListener jl = rc.getListener(nodeName);
		if(jl != null)
			return jl.searchLT(value);
		return false;
	}
	
	public static boolean searchBT(String ag, String nodeName, Object value){
		ROSConnection rc = agMap.get(ag);
		JasonListener jl = rc.getListener(nodeName);
		if(jl != null)
			return jl.searchBT(value);
		return false;
	}
	
	public static boolean searchExact(String ag, String nodeName, Object value){
		ROSConnection rc = agMap.get(ag);
		JasonListener jl = rc.getListener(nodeName);
		if(jl != null)
			return jl.searchExact(value);
		return false;
	}
	
	public static boolean clearDataList(String ag, String nodeName){
		ROSConnection rc = agMap.get(ag);
		JasonListener jl = rc.getListener(nodeName);
		if(jl != null){
			jl.clearDataList();
			return true;
		}
		return false;
	}
	
	public static Object getTopicData(String ag, String nodeName) throws InterruptedException{
		ROSConnection rc = agMap.get(ag);
		JasonListener jl = rc.getListener(nodeName);
		if(jl != null)
			return jl.getTopicData();
		return false;
	}
	
	public static Object recvData(String ag, String action, Unifier un) throws InterruptedException{
		ROSConnection rc = agMap.get(ag);
		Object data = rc.getDataByAction(action);
		return data;
		
	}
	
	public static boolean setTopicData(String ag, String nodeName, ArrayList<Object> params) throws InterruptedException{
		ROSConnection rc = agMap.get(ag);
		JasonTalker jt = rc.getTalker(nodeName);
		if(jt != null)
			return jt.setTopicData(params);
		return false;
	}
	
	public void closeROSConn(String ag){
		ROSConnection rc = agMap.get(ag);
		rc.shutdown();
		agMap.remove(ag);
	}

}
