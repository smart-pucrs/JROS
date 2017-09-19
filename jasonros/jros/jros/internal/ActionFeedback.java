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
public class ActionFeedback<T_ACTION_FEEDBACK extends Message> {
  private T_ACTION_FEEDBACK actionFeedbackMessage = null;

  public ActionFeedback(T_ACTION_FEEDBACK fmsg) {
    actionFeedbackMessage = fmsg;
  }

  public Header getHeaderMessage() {
    Header h = null;
    if (actionFeedbackMessage != null) {
      try {
        Method m = actionFeedbackMessage.getClass().getMethod("getHeader");
        m.setAccessible(true); // workaround for known bug http://bugs.java.com/bugdatabase/view_bug.do?bug_id=6924232
        h = (Header)m.invoke(actionFeedbackMessage);
      }
      catch (Exception e) {
        e.printStackTrace(System.out);
      }
    }
    return h;
  }

  public GoalStatus getGoalStatusMessage() {
    GoalStatus gs = null;
    if (actionFeedbackMessage != null) {
      try {
        Method m = actionFeedbackMessage.getClass().getMethod("getStatus");
        m.setAccessible(true); // workaround for known bug http://bugs.java.com/bugdatabase/view_bug.do?bug_id=6924232
        gs = (GoalStatus)m.invoke(actionFeedbackMessage);
      }
      catch (Exception e) {
        e.printStackTrace(System.out);
      }
    }
    return gs;
  }

  public Message getFeedbackMessage() {
    Message x = null;
    if (actionFeedbackMessage != null) {
      try {
        Method m = actionFeedbackMessage.getClass().getMethod("getFeedback");
        m.setAccessible(true); // workaround for known bug http://bugs.java.com/bugdatabase/view_bug.do?bug_id=6924232
        x = (Message)m.invoke(actionFeedbackMessage);
      }
      catch (Exception e) {
        e.printStackTrace(System.out);
      }
    }
    return x;
  }
}
