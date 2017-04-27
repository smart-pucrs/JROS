#include "jros.h"
#include "ros/ros.h"
#include <string>

int main(int argc, char **argv){
  JROS jros;
  jros.init(argc,argv);
  std::string s ("JROS Test Message");
  //jros.sendConfirmation(s);
  return 0;
}
