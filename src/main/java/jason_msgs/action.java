package jason_msgs;

import java.util.List;

import org.ros.internal.message.Message;

public interface action extends Message{
	public static final String _TYPE = "jason_msgs/action";
	public static final String _DEFINITION = "int32 id\nstring agent\nstring action\nstring[] parameters\n";

	public String getAgent();
	public String getAction();
	public List<String> getParameters();
	public int getId();
	public void setAgent(String agent);
	public void setAction(String action);
	public void setParameters(List<String> parameters);
	public void setId(int id);
}
