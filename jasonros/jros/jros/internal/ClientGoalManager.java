import org.ros.internal.message.Message;
import actionlib_msgs.GoalID;
import actionlib_msgs.GoalStatus;

/**
 * Class that binds and action goal with a state machine to track its state.
 * @author Ernesto Corbellini ecorbellini@ekumenlabs.com
 */
public class ClientGoalManager<T_ACTION_GOAL extends Message> {
  ActionGoal<T_ACTION_GOAL> actionGoal = null;
  ClientStateMachine stateMachine = null;

  public ClientGoalManager(ActionGoal<T_ACTION_GOAL> ag) {
    actionGoal = ag;
  }

  public void setGoal(ActionGoal<T_ACTION_GOAL> ag) {
    actionGoal = ag;
    stateMachine = new ClientStateMachine();
    stateMachine.setState(ClientStateMachine.ClientStates.WAITING_FOR_GOAL_ACK);
  }

  public void setGoal(T_ACTION_GOAL agm) {
    ActionGoal<T_ACTION_GOAL> ag = new ActionGoal();
    ag.setActionGoalMessage(agm);
    setGoal(ag);
  }

  public boolean cancelGoal() {
    return stateMachine.cancel();
  }

  public void resultReceived() {
    stateMachine.resultReceived();
  }

  public void updateStatus(int status) {
    stateMachine.transition(status);
  }

  public int getGoalState() {
    int ret = -666;
    if (stateMachine != null) {
      ret = stateMachine.getState();
    }
    return ret;
  }
}

