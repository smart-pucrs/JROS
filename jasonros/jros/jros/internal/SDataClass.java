package jros.internal;
public class SDataClass{
	String topicName;
	String msgType;
	Object data;
	public SDataClass(String topicName, Object data){
		this.topicName = topicName;
		this.data = data;
	}
	public String getTopicName(){
		return topicName;
	}
	public Object getData(){
		return data;
	}
	public void setData(Object data){
		this.data = data;
	}
}
