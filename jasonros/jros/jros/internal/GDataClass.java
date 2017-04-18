package jros.internal;

public class GDataClass extends DataClass{
	private String className;
	
	public GDataClass(String topicName, String msgType, String className) {
		super(topicName, msgType, null);
		this.className = className;
	}
	
	public String getClassName(){
		return className;
	}

}
