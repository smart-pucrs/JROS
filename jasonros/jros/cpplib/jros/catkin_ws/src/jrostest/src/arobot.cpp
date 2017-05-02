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
  pjros->init(argc,argv);
  pjros->jasonActionCB(&jasonActCallback);
  signal(SIGINT, inthand);
  while(!stop);
  return 0;
}
