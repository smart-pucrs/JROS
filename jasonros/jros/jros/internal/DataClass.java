package jros.internal;
public class DataClass extends SDataClass{
	private String msgType;
	public DataClass(String topicName, String msgType, Object data) {
		super(topicName, data);
		this.msgType = msgType;
	}
	public String getMsgType(){
		return msgType;
	}
}