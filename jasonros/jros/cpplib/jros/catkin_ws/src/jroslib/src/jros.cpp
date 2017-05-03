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
string pubMsg;
string rName;
void (*actionCallback)(std::string,std::string,std::vector<std::string>);
boost::thread *pactThread;
boost::thread *pconfThread;
vector<string> *perceptionsToSend;
class JROS{
  public:
    static void callbackFunc(const jason_msgs::action::ConstPtr& message);
    void init(int argc, char **argv,std::string robotName);
    void jasonActionCB(void (*callbackF)(std::string,std::string,std::vector<std::string>));
    void shutdown(void);
    void sendConfirmation(std::string action);
    void sendPerceptions(std::vector<std::string> *perceptions);
};

void JROS::callbackFunc(const jason_msgs::action::ConstPtr& message){
  if(actionCallback != NULL){
    (*actionCallback)(message->agent,message->action,message->parameters);
  }else{
    cout << "jasonActionCB: Error! Callback function not defined." << endl;
    ros::shutdown();
  }
}

void JROS::jasonActionCB(void (*callbackF)(std::string,std::string,std::vector<std::string>)){
  actionCallback = callbackF;
}

void actionRecvThread(){
    ros::NodeHandlePtr node = boost::make_shared<ros::NodeHandle>();
    ros::Subscriber robotSub = node->subscribe("jaction",1000,JROS::callbackFunc);
    ros::spin();
}

void sendConfirmationThread(){
  ros::NodeHandlePtr node = boost::make_shared<ros::NodeHandle>();
  ros::Publisher robotPub = node->advertise<std_msgs::String>("jconfirmation",1000);
  ros::Rate loop_rate(10);
  while(ros::ok()){
    if(pubMsg.compare("") != 0){
      std_msgs::String msg;
      msg.data = pubMsg;
      robotPub.publish(msg);
      pubMsg = "";
    }
    ros::spinOnce();
    loop_rate.sleep();
  }
}

void sendPerceptionsThread(){
  ros::NodeHandlePtr node = boost::make_shared<ros::NodeHandle>();
  ros::Publisher robotPub = node->advertise<jason_msgs::perception>("jpercetions",1000);
  ros::Rate loop_rate(10);
  while(ros::ok()){
    if(perceptionsToSend != NULL){
      jason_msgs::perception msg;
      msg.agent = rName;
      msg.perceptions = *perceptionsToSend;
      robotPub.publish(msg);
      perceptionsToSend = NULL;
    }
    ros::spinOnce();
    loop_rate.sleep();
  }
}

void JROS::init(int argc, char **argv, std::string robotName){
  jargc = argc;
  jargv = argv;
  rName = robotName;
  ros::init(argc,argv,"jroscpp");
  if(pactThread == NULL)
    pactThread = new boost::thread(actionRecvThread);
  if(pconfThread == NULL)
    pconfThread = new boost::thread(sendConfirmationThread);
}

void JROS::shutdown(void){
  ros::shutdown();
}

void JROS::sendConfirmation(std::string action){
  pubMsg = action;
}

void JROS::sendPerceptions(std::vector<std::string> *perceptions){
  perceptionsToSend = perceptions;
}
