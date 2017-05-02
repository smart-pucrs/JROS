#include <string>
#include <iostream>
#include "ros/ros.h"
#include <ros/callback_queue.h>
#include "std_msgs/String.h"
#include "jason_msgs/action.h"
#include "jason_msgs/perception.h"
#include <boost/thread.hpp>

using namespace std;
int jargc;
char **jargv;
//string recvAgent;
//string recvAction;
//vector<string> recvParameters;
void (*actionCallback)(std::string,std::string,std::vector<std::string>);
string pubMsg;
boost::thread *psubThread;
boost::thread *ppubThread;
class JROS{
  public:
    void callbackFunc(const jason_msgs::action::ConstPtr& message);
    void init(int argc, char **argv);
    //std::string getAction(void);
    //std::string getAgent(void);
    //std::vector<std::string> getParameters(void);
    void jasonActionCB(void (*callbackF)(std::string,std::string,std::vector<std::string>));
    void shutdown(void);
    void sendConfirmation(std::string action);
};

void JROS::callbackFunc(const jason_msgs::action::ConstPtr& message){
  //recvAgent = message->agent;
  //recvAction = message->action;
  //recvParameters = message->parameters;
  (*actionCallback)(message->agent,message->action,message->parameters);
}

void JROS::jasonActionCB(void (*callbackF)(std::string,std::string,std::vector<std::string>)){
  actionCallback = callbackF;
}

void subThread(){
  ros::NodeHandlePtr node = boost::make_shared<ros::NodeHandle>();
  JROS f;
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

void JROS::init(int argc, char **argv){
  jargc = argc;
  jargv = argv;
  ros::init(argc,argv,"jroscpp");
  if(psubThread == NULL)
    psubThread = new boost::thread(subThread);
  if(ppubThread == NULL)
    ppubThread = new boost::thread(pubThread);
}

void JROS::shutdown(void){

}

void JROS::sendConfirmation(std::string action){
  pubMsg = action;
}
