#include "ros/ros.h"
#include "std_msgs/String.h"
#include <string>
#include <boost/thread.hpp>

class JROS{
  public:
    JROS(int argc, char **argv);
    std::string getAction(void);
    void sendConfirmation(std::string action);
};
