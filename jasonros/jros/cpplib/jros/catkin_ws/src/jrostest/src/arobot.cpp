#include "jros.h"
#include "ros/ros.h"
#include <string>
#include <iostream>
#include "stdio.h"
#include "unistd.h"
#include <signal.h>

using namespace std;
volatile sig_atomic_t stop;
JROS *pjros;

void inthand(int signum){
    stop = 1;
}

void jasonActCallback(string agent, string action, vector<string> parameters){
  cout << "Action received!!" << endl;
  cout << "Agent:" << agent << endl;
  cout << "Action:" << action << endl;
  cout << "Parameters:";
  for(int i = 0;i < parameters.size();i++)
    cout << parameters[i] << " ";
  cout << endl;
  pjros->sendConfirmation(action);
}

int main(int argc, char **argv){
  JROS jros;
  pjros = &jros;
  pjros->init(argc,argv,"arobot");
  pjros->jasonActionCB(&jasonActCallback);
  pjros->addPerception("speed(10)");
  pjros->addPerception("direction(35.4)");
  pjros->addPerception("no_obstacles");
  pjros->addPerception("@speed(15)");
  pjros->sendPerceptions();

  sleep(10);
  pjros->shutdown();
  cout << "Shutting down..." << endl;
  signal(SIGINT, inthand);
  cout << "t" << endl;
  while(!stop);
  return 0;
}
