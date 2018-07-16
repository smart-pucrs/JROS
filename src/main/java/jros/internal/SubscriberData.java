package jros.internal;

public class SubscriberData {
	private String topicName;
	private Object data;
	public SubscriberData(String topicName, Object data){
		this.topicName = topicName;
		this.data = data;
	}
	public String getTopicName(){
		return topicName;
	}
	
	public Object getData(){
		return data;
	}
}
