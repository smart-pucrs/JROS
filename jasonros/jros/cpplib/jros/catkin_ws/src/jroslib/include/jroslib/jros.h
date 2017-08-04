#include <string>
#include <iostream>
#include <cstdlib>
#include <time.h>
#include "ros/ros.h"
#include "ros/spinner.h"
#include <future>
#include <pthread.h>
#include <map>
#include <ros/callback_queue.h>
#include "std_msgs/String.h"
#include "jason_msgs/action.h"
#include "jason_msgs/perception.h"
#include <boost/thread.hpp>
#include <boost/thread/mutex.hpp>
#include <boost/asio/io_service.hpp>
#include <boost/lockfree/queue.hpp>
#include <boost/atomic.hpp>
#include <boost/shared_ptr.hpp>
#include <gazebo/gazebo.hh>
#include <gazebo/physics/physics.hh>
#include <gazebo/gazebo_config.h>
#include <gazebo/transport/transport.hh>
#include <gazebo/msgs/msgs.hh>
#include <boost/shared_array.hpp>
#include <thread>
#define ROBOT 0
#define GAZEBO 1

using namespace std;
class JROS{
  public:
    void (*mactionCallback)(int,std::string,std::string,std::vector<std::string>);
    void callbackFuncM(const jason_msgs::action::ConstPtr& message, boost::function<void(int,std::string,std::string,std::vector<std::string>)> f);
    void callbackFuncS(const jason_msgs::action::ConstPtr& message, void (*f)(int,std::string,std::string,std::vector<std::string>));
    void init(int argc, char **argv,std::string robotName,void (*f)(int,std::string,std::string,std::vector<std::string>));
    void init(int argc, char **argv,string robotName,boost::function<void(int,std::string,std::string,std::vector<std::string>)> f);
    void shutdown(void);
    void sendConfirmation(const char* cstr);

    void sendPerceptions(void);
    void addPerception(std::string perception);
    void clearPerceptions(void);
    void removePerception(string perception);
    int getConfirmation(void);
    void setConf(void){
      this->command = 872;
    }
    void setRobotName(const char *s){
      this->rName = (char *)malloc(256);
      strcpy(this->rName,s);
    }
    void changeRobotName(const char *s){
      strcpy(this->rName,s);
    }
    void setPubMsg(const char *s){
      this->pubMsg = (char *)malloc(256);
      while(this->pubMsg == NULL)
        this->pubMsg = (char *)malloc(256);
      strcpy(this->pubMsg,s);
    }
    void changePubMsg(const char *s){
      strcpy(this->pubMsg,s);
    }
    char* getRobotName(){
      return this->rName;
    }
    char *pubMsg = NULL;
    char *rName = NULL;
    //void sendConfirmationFunc(void);
  private:
    int command;
    void sendConfirmationFunc(JROS *j);
    void actionRecvThreadM(JROS *i,boost::function<void(int,std::string,std::string,std::vector<std::string>)> f);
    void actionRecvThreadS(void (*f)(int,std::string,std::string,std::vector<std::string>));
    void sendPerceptionsThread(void);
    vector<string> perceptions;
    char aid;
};
