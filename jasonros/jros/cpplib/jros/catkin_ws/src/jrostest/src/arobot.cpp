#include "jros.h"
#include "ros/ros.h"
#include <string>
#include <iostream>
#include "stdio.h"
#include "unistd.h"

using namespace std;
int main(int argc, char **argv){
  JROS jros(argc,argv);
  std::string s;
  cout << "ss" << endl;
  while(1){
    cout << s << endl;
    s = jros.getAction();
    jros.sendConfirmation("action2");
    sleep(1);
  }
  //jros.sendConfirmation(s);
  return 0;
}
