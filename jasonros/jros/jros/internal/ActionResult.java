import java.lang.reflect.Method;
import org.ros.internal.message.Message;
import org.ros.message.Time;
import std_msgs.Header;
import actionlib_msgs.GoalID;
import actionlib_msgs.GoalStatus;

/**
 * Class to encapsulate the action feedback object.
 * @author Ernesto Corbellini ecorbellini@ekumenlabs.com
 */
public class ActionResult<T_ACTION_RESULT extends Message> {
  private T_ACTION_RESULT actionResultMessage = null;

  public ActionResult(T_ACTION_RESULT msg) {
    actionResultMessage = msg;
  }

  public Header getHeaderMessage() {
    Header h = null;
    if (actionResultMessage != null) {
      try {
        Method m = actionResultMessage.getClass().getMethod("getHeader");
        m.setAccessible(true); // workaround for known bug http://bugs.java.com/bugdatabase/view_bug.do?bug_id=6924232
        h = (Header)m.invoke(actionResultMessage);
      }
      catch (Exception e) {
        e.printStackTrace(System.out);
      }
    }
    return h;
  }

  public GoalStatus getGoalStatusMessage() {
    GoalStatus gs = null;
    if (actionResultMessage != null) {
      try {
        Method m = actionResultMessage.getClass().getMethod("getStatus");
        m.setAccessible(true); // workaround for known bug http://bugs.java.com/bugdatabase/view_bug.do?bug_id=6924232
        gs = (GoalStatus)m.invoke(actionResultMessage);
      }
      catch (Exception e) {
        e.printStackTrace(System.out);
      }
    }
    return gs;
  }

  public Message getResultMessage() {
    Message x = null;
    if (actionResultMessage != null) {
      try {
        Method m = actionResultMessage.getClass().getMethod("getResult");
        m.setAccessible(true); // workaround for known bug http://bugs.java.com/bugdatabase/view_bug.do?bug_id=6924232
        x = (Message)m.invoke(actionResultMessage);
      }
      catch (Exception e) {
        e.printStackTrace(System.out);
      }
    }
    return x;
  }
}

