#include "ros/ros.h"
#include "std_msgs/String.h"
#include <string>
#include <boost/thread.hpp>

class JROS{
  public:
    void init(int argc, char **argv,std::string robotName);
    //std::string getAction(void);
    //std::string getAgent(void);
    //std::vector<std::string> getParameters(void);
    void jasonActionCB(void (*callbackF)(std::string,std::string,std::vector<std::string>));
    void shutdown(void);
    void sendConfirmation(std::string action);
    void sendPerceptions(void);
    void addPerception(std::string perception);
    void clearPerceptions(void);
};
