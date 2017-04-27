#include <string>
#include <iostream>
#include "ros/ros.h"
#include "std_msgs/String.h"

using namespace std;

class JROS{
  public:
    int init(int argc, char **argv);
    int sendConfirmation(std::string action);
    int recvAction(void);
  private:
    string actName;
};

void callbackFunc(const std_msgs::String::ConstPtr& message){
  cout << message->data.c_str() << endl;
}

int JROS::init(int argc, char **argv){
  ros::init(argc,argv,"jasoncpp");
  cout << "chamou init" << endl;
  ros::NodeHandle n1;
  ros::Subscriber robotSub = n1.subscribe("jaction",100,callbackFunc);
  ros::spin();
  ros::NodeHandle n2;
  ros::Publisher robotPub = n2.advertise<std_msgs::String>("jconfirmation",100);
  ros::Rate loop_rate(10);
  //JROS::actName = "test";
  string s("aaa");
  while(ros::ok()){
    std_msgs::String actMsg;
    actMsg.data = s;
    robotPub.publish(actMsg);
    loop_rate.sleep();
  }
  return 0;
}

int JROS::sendConfirmation(std::string action){
  /*cout << "chamou confirm" << endl;
  if(ros::ok()){
    std_msgs::String message;
    message.data = action;
    JROS::robotPub.publish(message);
    return 0;
  }*/
  return 1;
}


int JROS::recvAction(void){
  //ros::spin();
  return 0;
}
