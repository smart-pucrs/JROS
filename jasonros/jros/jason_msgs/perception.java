package jason_msgs;

import org.ros.internal.message.Message;

public interface perception extends Message{
	public static final String _TYPE = "jason_msgs/perception";
	public static final String _DEFINITION = "string agent\nstring perception\n";
	
	public String getAgent();
	public String getPerception();
	public void setAgent(String name);
	public void setPerception(String perception);
}