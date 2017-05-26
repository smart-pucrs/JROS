#include <string>
#include <iostream>
#include <cstdlib>
#include <time.h>
#include "ros/ros.h"
#include <ros/callback_queue.h>
#include "std_msgs/String.h"
#include "jason_msgs/action.h"
#include "jason_msgs/perception.h"
#include <boost/thread.hpp>

using namespace std;
int jargc;
char **jargv;
char aid;
string pubMsg;
string rName;
void (*actionCallback)(std::string,std::string,std::vector<std::string>);
boost::thread *pactThread;
boost::thread *pconfThread;
boost::thread *pperceptThread;
//vector<string> *perceptionsToSend;
vector<string> perceptions;
class JROS{
  public:
    static void callbackFunc(const jason_msgs::action::ConstPtr& message);
    void init(int argc, char **argv,std::string robotName);
    void jasonActionCB(void (*callbackF)(std::string,std::string,std::vector<std::string>));
    void shutdown(void);
    void sendConfirmation(std::string action);
    void sendPerceptions(void);
    void addPerception(std::string perception);
    void clearPerceptions(void);
};

void JROS::callbackFunc(const jason_msgs::action::ConstPtr& message){
  cout << "Callback\n";
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
    cout << "action thread\n" << endl;
    ros::NodeHandlePtr node = boost::make_shared<ros::NodeHandle>();
    std::string topicName(rName);
    topicName += "/jaction";
    ros::Subscriber robotSub = node->subscribe(topicName,1000,JROS::callbackFunc);
    ros::spin();
}

void sendConfirmationThread(){
  ros::NodeHandlePtr node = boost::make_shared<ros::NodeHandle>();
  std::string topicName(rName);
  topicName += "/jconfirmation";
  ros::Publisher robotPub = node->advertise<std_msgs::String>(topicName,1000);
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
  std::string topicName(rName);
  topicName += "/jperceptions";
  ros::Publisher robotPub = node->advertise<jason_msgs::perception>(topicName,1000);
  ros::Rate loop_rate(5);
  while(ros::ok()){
    jason_msgs::perception msg;
    msg.id = aid;
    msg.agent = rName;
    msg.perceptions = perceptions;
    robotPub.publish(msg);
    ros::spinOnce();
    loop_rate.sleep();
  }
}

void JROS::init(int argc, char **argv, std::string robotName){
  cout << "JROS Lib Loaded!\n";
  jargc = argc;
  jargv = argv;
  rName = robotName;
  if(!ros::isInitialized()){
    ros::init(argc,argv,"jroscpp");
  }
  srand(time(NULL));
  if(pactThread == NULL)
    pactThread = new boost::thread(actionRecvThread);
  if(pconfThread == NULL)
    pconfThread = new boost::thread(sendConfirmationThread);
  if(pperceptThread == NULL)
    pperceptThread = new boost::thread(sendPerceptionsThread);
}

void JROS::shutdown(void){
  ros::shutdown();
}

void JROS::sendConfirmation(std::string action){
  pubMsg = action;
}

void JROS::addPerception(std::string perception){
  perceptions.push_back(perception);
}

void JROS::clearPerceptions(void){
  perceptions.clear();
}

void JROS::sendPerceptions(void){
  //perceptionsToSend = &perceptions;
  aid = rand()%128;
}
