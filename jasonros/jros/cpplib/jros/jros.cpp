#include <string>
#include "ros/ros.h"
#include "std_msgs/String.h"

class JROS{
  public:
    JROS(int argc, char **argv){
      ros::init(argc,argv,"Jason/robot");
      robotPub = n.advertise<std_msgs::String>("/jtopics/confirmation",100);
    }
    int sendConfirmation(std::string action){
      if(ros::ok()){
        std_msgs::String message;
        message.data = action;
        robotPub.publish(message);
        return 0;
      }
      return 1;
    }
    int recvAction(void){
      robotSub = n.subscribe("/jtopics/action",100,callbackFunc);
      ros::spin();
      return 0;
    }
  private:
    ros::NodeHandle n;
    ros::Publisher robotPub;
    ros::Subscriber robotSub;
    void callbackFunc(const jason_msgs::action::ConstPtr& message){
      //TODO
    }
};
