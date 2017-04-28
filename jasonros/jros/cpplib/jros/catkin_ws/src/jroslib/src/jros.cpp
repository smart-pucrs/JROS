#include <string>
#include <iostream>
#include "ros/ros.h"
#include <ros/callback_queue.h>
#include "std_msgs/String.h"
#include <boost/thread.hpp>

using namespace std;
int jargc;
char **jargv;
string recvMsg;
string pubMsg;
boost::thread *psubThread;
boost::thread *ppubThread;
class JROS{
  public:
    JROS(int argc, char **argv);
    void callbackFunc(const std_msgs::String::ConstPtr& message);
    std::string getAction(void);
    void sendConfirmation(std::string action);
};

void JROS::callbackFunc(const std_msgs::String::ConstPtr& message){
  recvMsg = message->data;
}

void subThread(){
  ros::NodeHandlePtr node = boost::make_shared<ros::NodeHandle>();
  JROS f(jargc,jargv);
  ros::Subscriber robotSub = node->subscribe("jaction",1000,&JROS::callbackFunc,&f);
  ros::spin();
}

void pubThread(){
  ros::NodeHandlePtr node = boost::make_shared<ros::NodeHandle>();
  ros::Publisher robotPub = node->advertise<std_msgs::String>("jconfirmation",1000);
  ros::Rate loop_rate(10);
  while(ros::ok()){
    std_msgs::String msg;
    msg.data = pubMsg;
    robotPub.publish(msg);
    ros::spinOnce();
    loop_rate.sleep();
  }
}

JROS::JROS(int argc, char **argv){
  jargc = argc;
  jargv = argv;
  ros::init(argc,argv,"jroscpp");
  if(psubThread == NULL)
    psubThread = new boost::thread(subThread);
  if(ppubThread == NULL)
    ppubThread = new boost::thread(pubThread);
}

string JROS::getAction(void){
  return recvMsg;
}

void JROS::sendConfirmation(std::string action){
  pubMsg = action;
}
