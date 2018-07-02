#include "ros/ros.h"
#include <string>
#include <boost/thread.hpp>
#include <gazebo/gazebo.hh>
#include <gazebo/physics/physics.hh>
#include <gazebo/gazebo_config.h>
#include <gazebo/transport/transport.hh>
#include <gazebo/msgs/msgs.hh>
#define ROBOT 0
#define GAZEBO 1
class JROS{
public:
  //static void callbackFunc(const jason_msgs::action::ConstPtr& message);
  void init(int argc, char **argv,std::string robotName,void (*callbackF)(int,std::string,std::string,std::vector<std::string>));
  void init(int argc, char **argv,std::string robotName,boost::function<void(int,std::string,std::string,std::vector<std::string>)> f);
  void shutdown(void);
  //void sendConfirmation(std::string action);
  void sendConfirmation(const char* cstr);
  int getConfirmation(void);
  void changeRobotName(const char *s);
  void sendPerceptions(void);
  void addPerception(std::string perception);
  void clearPerceptions(void);
  void removePerception(std::string perception);
  gazebo::event::ConnectionPtr getTest();
  char *pubMsg = NULL;
  char *rName = NULL;
};
