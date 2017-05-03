package jason_msgs;

import java.util.List;

import org.ros.internal.message.Message;

public interface perception extends Message{
	public static final String _TYPE = "jason_msgs/perception";
	public static final String _DEFINITION = "string agent\nstring[] perceptions\n";
	
	public String getAgent();
	public List<String> getPerceptions();
	public void setAgent(String name);
	public void setPerceptions(List<String> perceptions);
}