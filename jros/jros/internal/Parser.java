package jros.internal;

import jason.asSemantics.Agent;

public class Parser {
	private String[] parameters;
	private String s;
	//private Agent ag;
	private String agName;
	
	public Parser(String s, Agent ag){
		this.s = s;
		this.agName = ag.getTS().getUserAgArch().getAgName();
		if(isValidLine())
			parameters = s.split(" ");
	}
	
	public boolean isValidLine(){
		if(s.charAt(0) != '#' && !s.isEmpty())
			return true;
		return false;
	}
	
	public boolean isPublisher(){
		return parameters[4].equals("pub");
	}
	
	public boolean isSubscriber(){
		return parameters[4].equals("sub");
	}
	
	public String actionName(){
		return parameters[0];
	}
	
	public String msgType(){
		return parameters[1];
	}
	
	public String topicName(){
		String topicName = parameters[2];
		if(topicName.contains("{$AG_NAME}"))
			topicName = topicName.replace("{$AG_NAME}", agName);
		return topicName;
	}
	
	public String type(){
		return parameters[3];
	}
	
	public String nodeType(){
		return parameters[4];
	}
	
	public String nodeName(){
		if(isPublisher())
			return actionName()+agName;
		if(isSubscriber())
			return actionName()+agName;
		return null;
	}
	
	public long publishRate(){
		if(parameters.length == 6)
			return Long.valueOf(parameters[5]);
		return 0;
	}

}
