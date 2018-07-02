#include "../include/jroslib/jros.h"

#define WRITE 0
#define READ 1
using namespace std;

void JROS::callbackFuncM(const jason_msgs::action::ConstPtr& message, boost::function<void(int,std::string,std::string,std::vector<std::string>)> f){
  if(this->getConfirmation() == 1){
      cout << "callbackfunc" << endl;
      if(f != NULL){
        cout << "entrou cb2" << endl;
        f(message->id,message->agent,message->action,message->parameters);
      }else{
        cout << "jasonActionCB: Error! Callback function not defined." << endl;
        ros::shutdown();
      }
    }
}

void JROS::callbackFuncS(const jason_msgs::action::ConstPtr& message, void (*f)(int,std::string,std::string,std::vector<std::string>)){
  if(this->getConfirmation() == 1){
      cout << "callbackfunc" << endl;
      if(f != NULL){
        cout << "entrou cb2" << endl;
        (*f)(message->id,message->agent,message->action,message->parameters);
      }else{
        cout << "jasonActionCB: Error! Callback function not defined." << endl;
        ros::shutdown();
      }
    }
}


void JROS::actionRecvThreadM(JROS *i,boost::function<void(int,std::string,std::string,std::vector<std::string>)> f){
    printf("Action instance:%p\n", i);
    char *ptra = i->rName;
    printf("Name:%s\n", ptra);
    ros::NodeHandlePtr node = boost::make_shared<ros::NodeHandle>();
    std::string topicName(ptra);
    topicName += "/jaction";
    ros::Subscriber robotSub = node->subscribe<jason_msgs::action>(topicName,1000,boost::bind(&JROS::callbackFuncM,this,_1,f));
    ros::MultiThreadedSpinner spinner;
    spinner.spin();
}

/*void JROS::actionRecvThreadS(void (*f)(int,std::string,std::string,std::vector<std::string>)){
    char *ptr = this->rName;
    cout << "action thread\n" << endl;
    ros::NodeHandlePtr node = boost::make_shared<ros::NodeHandle>();
    std::string topicName(ptr);
    topicName += "/jaction";
    ros::Subscriber robotSub = node->subscribe<jason_msgs::action>(topicName,1000,boost::bind(&JROS::callbackFuncS,this,_1,f));
    ros::MultiThreadedSpinner spinner;
    spinner.spin();
}*/


void JROS::sendConfirmationFunc(JROS *j){
  if((j->pubMsg != NULL) && (j->rName != NULL)){
  char *ptr = j->pubMsg;
  printf("Confirmation instance:%p\n", j);
  char *ptr2 = j->rName;
  printf("Confirmation Name:%s\n", ptr2);
  ros::NodeHandlePtr node = boost::make_shared<ros::NodeHandle>();
  std::string topicName(ptr2);
  topicName += "/jconfirmation";
  cout << topicName << endl;
  ros::Publisher robotPub = node->advertise<std_msgs::String>(topicName,1000);
  ros::Rate loop_rate(10);
  this->setConf();
  this->pubMsg = ptr;
  while(ros::ok){
    //printf("sendconf:%s\n",ptr);
      std_msgs::String msg;
      msg.data = string(j->pubMsg);
      robotPub.publish(msg);
      ros::spinOnce();
      loop_rate.sleep();
  }
}
}
/*
void JROS::sendPerceptionsThread(){
  cout << "perception thread\n" << endl;
  ros::NodeHandlePtr node = boost::make_shared<ros::NodeHandle>();
  std::string topicName(rName);
  topicName += "/jperceptions";
  ros::Publisher robotPub = node->advertise<jason_msgs::perception>(topicName,1000);
  ros::Rate loop_rate(5);
  while(ros::ok()){
    jason_msgs::perception msg;
    msg.id = aid;
    //msg.agent = *prName;
    msg.perceptions = perceptions;
    robotPub.publish(msg);
    ros::spinOnce();
    loop_rate.sleep();
  }
}*/


void JROS::init(int argc, char **argv, string robotName, boost::function<void(int,std::string,std::string,std::vector<std::string>)> f){
  cout << "JROS Lib Loaded!\n";
  //this->setRobotName(robotName.c_str());

  if(!ros::isInitialized()){
    ros::init(argc,argv,"jroscpp");
  }
  srand(time(NULL));
  cout << "chegou" << endl;
  this->rName = (char *)malloc(256);
  this->pubMsg = (char *)malloc(256);
  strcpy(this->rName, robotName.c_str());
  strcpy(this->pubMsg,(char *)"");
  printf("Init instance:%p\n", this);
  boost::thread acthread(&JROS::actionRecvThreadM, this,this,f);
  printf("Init instance:%p\n", this);
  boost::thread confthread(&JROS::sendConfirmationFunc,this,this);
  //boost::thread percepthread(&JROS::sendPerceptionsThread, this);
}

/////////////////////////////////////////////////////////qqq
/*void JROS::init(int argc, char **argv, string robotName, void (*f)(int,std::string,std::string,std::vector<std::string>)){
  cout << "JROS Lib Loaded!\n";
  this->setRobotName(robotName.c_str());
  this->pubMsg = (char *)malloc(256);
  strcpy(this->pubMsg,(char *)"");
  if(!ros::isInitialized()){
    ros::init(argc,argv,"jroscpp");
  }
  srand(time(NULL));
  cout << "chegou" << endl;
  boost::thread acthread(&JROS::actionRecvThreadS, this, f);
  boost::thread confthread(&JROS::sendConfirmationFunc,this);
  //boost::thread percepthread(&JROS::sendPerceptionsThread, this);
}*/

void JROS::removePerception(string perception){
  for(int i = 0;i < perceptions.size();i++){
    if(perceptions[i] == perception)
      perceptions.erase(perceptions.begin()+i);
  }
}

void JROS::shutdown(void){
  ros::shutdown();
}


int JROS::getConfirmation(){
  if(this->command == 872){
    return 1;
  }else return 0;
}


void JROS::sendConfirmation(const char* cstr){
  this->changePubMsg(cstr);
}

void JROS::addPerception(std::string iperception){
  string perception;
  int qtd = 0;
  if(iperception[0] == '@'){
    qtd = iperception.find("(");
    perception = iperception.substr(0,qtd);//@speed() => speed
  }else perception = iperception;
  for(int i = 0;i < perceptions.size();i++){
    if(perceptions[i] == perception)
      return;
    if(perception[0] == '@' && qtd < perceptions[i].length()){
      if(perception == perceptions[i].substr(0,qtd)){
        perceptions[i] = iperception;
        return;
      }
    }
  }
  perceptions.push_back(string(perception));
}

void JROS::clearPerceptions(void){
  perceptions.clear();
}

void JROS::sendPerceptions(void){
  //perceptionsToSend = &perceptions;
  aid = rand()%128;
}
