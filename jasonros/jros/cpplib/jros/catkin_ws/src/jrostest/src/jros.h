#include "ros/ros.h"
#include "std_msgs/String.h"
#include <string>

class JROS{
  public:
    int init(int argc, char **argv);
    int sendConfirmation(std::string action);
    int recvAction(void);
};
