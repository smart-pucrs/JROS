package jason_msgs;

import java.util.List;

import org.ros.internal.message.Message;

public interface perception extends Message{
	public static final String _TYPE = "jason_msgs/perception";
	public static final String _DEFINITION = "int8 id\nstring agent\nstring[] perceptions\n";
	
	public byte getId();
	public String getAgent();
	public List<String> getPerceptions();
	public void setId(byte id);
	public void setAgent(String agent);
	public void setPerceptions(List<String> perceptions);
}