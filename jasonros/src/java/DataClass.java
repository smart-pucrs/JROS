public class DataClass{
	String topicName;
	String msgType;
	Object data;
	public DataClass(String topicName, String msgType, Object data){
		this.topicName = topicName;
		this.msgType = msgType;
		this.data = data;
	}
	
	public String getTopicName(){
		return topicName;
	}
	public String getMsgType(){
		return msgType;
	}
	public Object getData(){
		return data;
	}
	public void setData(Object data){
		this.data = data;
	}
}
