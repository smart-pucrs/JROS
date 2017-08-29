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
		System.out.println("criou config file");
		List<String> fList = null;
	    List<String[]> aList = new ArrayList<String[]>();
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
		if(rc.rosConfig(rosIP, rosPort,aList)){
			for(String s : fList){
				//System.out.println(s.charAt(0));
				if(s.charAt(0) != '#' && !s.isEmpty()){
					String[] params = s.split(" ");
					aList.add(params);
					switch(params[4]){
					case "sub":
					{
						System.out.println("Criou sub!!!");
						//rc.addSubTopic(params[2], params[1]);
						rc.createSubNode(params[0]+"SubNode", params[0], params[2], params[1]);
					}
					break;
					case "pub":
					{
						JasonTalker jt = rc.createPubNode(params[0]+"PubNode", params[2], params[1], Long.valueOf(params[5]));
						rc.mapNode(params[0], jt);
						//rc.addSubTopic(params[2], params[1]);
					}
					break;
					default:
					{
						System.out.println("Error: Node type missing!");
						return false;
					}
					}
				}
			}
			//System.out.println("JMethods aList size:"+aList.size());
			//rc.createConfigNodes();
			return true;
		}
		return false;
	}
	
	/*public static boolean addPubGenericTopic(String ag, String topicName, String msgType, String className, Unifier un, Term[] terms) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		ROSConnection rc = agMap.get(ag);
		return rc.addPubGenericTopic(topicName, msgType, className, un, terms);
	}
	
	public static boolean addSubGenericTopic(String ag, String topicName, String msgType, String className, Unifier un, Term[] terms) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		ROSConnection rc = agMap.get(ag);
		return rc.addSubGenericTopic(topicName, msgType, className, un, terms);
	}*/
	
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
		return rc.createSubNode(nodeName, null, topicName, messageType);
	}
	
	public static boolean createPubNode(String ag, String nodeName, String topicName,
			String messageType, long pRate) throws InterruptedException{
		ROSConnection rc = agMap.get(ag);
		return rc.createPubNode(nodeName, topicName, messageType, pRate) != null;
	}
	
	/*public static boolean addSubTopic(String ag, String topicName, String msgType){
		ROSConnection rc = agMap.get(ag);
		return rc.addSubTopic(topicName, msgType);
	}
	
	public static boolean addPubTopic(String ag, String topicName, String msgType, Object data){
		ROSConnection rc = agMap.get(ag);
		return rc.addPubTopic(topicName, msgType, data);
	}*/
	
	public static boolean searchLT(String ag, String topicName, Object value){
		ROSConnection rc = agMap.get(ag);
		if(rc.subExists()){
			return rc.getListenerInstance().searchLT(topicName, value);
		}else{
			System.out.println("Subscriber node doesn't exists!");
			return false;
		}
	}
	
	public static boolean searchBT(String ag, String topicName, Object value){
		ROSConnection rc = agMap.get(ag);
		if(rc.subExists()){
			return rc.getListenerInstance().searchBT(topicName, value);
		}else{
			System.out.println("Subscriber node doesn't exists!");
			return false;
		}
	}
	
	public static boolean searchExact(String ag, String topicName, Object value){
		ROSConnection rc = agMap.get(ag);
		if(rc.subExists())
			return rc.getListenerInstance().searchExact(topicName, value);
		else{
			System.out.println("Subscriber node doesn't exists!");
			return false;
		}
	}
	
	public static boolean clearDataList(String ag, String topicName){
		ROSConnection rc = agMap.get(ag);
		if(rc.subExists()){
			rc.getListenerInstance().clearDataList(topicName);
			return true;
		}
		return false;
	}
	
	public static Object getTopicData(String ag, String topicName) throws InterruptedException{
		ROSConnection rc = agMap.get(ag);
		if(rc.subExists()){
			//Object data = null;
			Object data = rc.getListenerInstance().searchTopic(topicName);
			//timeoutTimer(getTopicTimeout);
//			while(data == null)
//				data = rosconn.getListenerInstance().searchTopic(topicName);
			/*if(timeout){
				System.out.println("getTopicData: timeout!");
				timeout = false;
			}else timer.cancel();*/
			if(data != null)
				return data;
		}else
			System.out.println("Subscriber node doesn't exists!");
			//return null;
		return null;
	}
	
	public static Object recvData(String ag, String action, Unifier un) throws InterruptedException{
		ROSConnection rc = agMap.get(ag);
		Object data = rc.getDataByAction(action);
		return data;
		
	}
	
	public static boolean setTopicData(String ag, String nodeName, String topicName, Object data) throws InterruptedException{
		ROSConnection rc = agMap.get(ag);
		if(rc.pubExists(topicName)){
		//rosconn.getTalkerInstance().setTopicData(topicName, msgType, data);
		ArrayList<JasonTalker> l = rc.getTalkerList();
		for(JasonTalker jt : l){//nodeName -> nodo que ira publicar o dado
			if(jt.getNodeName().equals(nodeName)){
				return jt.setTopicData(topicName, data);
			
			}
		}
		}
		return false;//topico nao existe ou nodo nao existe
	}
	
	public void closeROSConn(String ag){
		ROSConnection rc = agMap.get(ag);
		rc.shutdown();
		agMap.remove(ag);
	}

}
