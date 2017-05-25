#ifndef _JASON_PLUGIN_HH_
#define _JASON_PLUGIN_HH_

#include <gazebo/gazebo.hh>
#include <gazebo/physics/physics.hh>
#include <gazebo/gazebo_config.h>
#include <gazebo/transport/transport.hh>
#include <gazebo/msgs/msgs.hh>
#include <iostream>
#define PI 3.14159265358979323846
//Definidos no .sdf
#define WHEEL_DIAM 0.070
#define WHEEL_SEP .230
#define TORQUE 1.0
#define UPDATE_RATE 100.0
#define ACCEL 0.0
#define EPSILON 0.01
namespace gazebo
{ //TODO:move => mover X metros | rotate => rotacionar X graus
  //TODO:Conexao com o ROS para receber acoes dos topicos do JROS e enviar percepcoes aos agentes
  class JasonPlugin : public ModelPlugin
  {
    public: JasonPlugin() {}
    //Com joints
    public: void move(double speed){// m/s
      this->linearVel = speed;
    }

  public: void rotate(double ang, double speed){// graus | graus/s
    this->angularVel = fabs(speed * 2 * PI / 360);
    this->angleGoal = ang;
    this->speedDegree = speed;
  }

  public: void getVel(){
    std::cerr << "Vel:" << linearVel << std::endl;
    boost::mutex::scoped_lock scoped_lock(lock);
    double lv = linearVel;
    double av = angularVel;
    wspeedL = lv + av * WHEEL_SEP/2.0;
    wspeedR = lv - av * WHEEL_SEP/2.0;
  }
  public: void Update(){
    common::Time currTime = model->GetWorld()->GetSimTime();
    double deltaTime = (currTime - lastTime).Double();
    double rotationTime = fabs(this->angleGoal / this->speedDegree);//segs
    /*if(deltaTime > updateRate){
      if(angleGoal != 0){
        if(deltaTime >= rotationTime){
          this->angularVel = 0;
          this->angleGoal = 0;
        }
      }*/
      getVel();
      double currSpeedL,currSpeedR;
      currSpeedL = this->jleft->GetVelocity(0)*(WHEEL_DIAM/2.0);
      currSpeedR = this->jright->GetVelocity(0)*(WHEEL_DIAM/2.0);
      double epL = fabs(wspeedL - currSpeedL);
      double epR = fabs(wspeedR - currSpeedR);
      if((ACCEL == 0) || (epL < EPSILON) || (epR < EPSILON)){
        this->model->GetJointController()->SetVelocityTarget(this->jleft->GetScopedName(),wspeedL/(WHEEL_DIAM/2.0));
        this->model->GetJointController()->SetVelocityTarget(this->jright->GetScopedName(),wspeedR/(WHEEL_DIAM/2.0));
      }else{
        if(wspeedL >= currSpeedL){
          wspeedLAux += fmin(wspeedL - currSpeedL,ACCEL * deltaTime);
        }else{
          wspeedLAux += fmax(wspeedL - currSpeedL,-ACCEL * deltaTime);
        }

        if(wspeedR>currSpeedR){
          wspeedRAux += fmin(wspeedR - currSpeedR,ACCEL * deltaTime);
        }else{
          wspeedRAux += fmax(wspeedR - currSpeedR,-ACCEL * deltaTime);
        }
        this->model->GetJointController()->SetVelocityTarget(this->jleft->GetScopedName(),wspeedLAux/(WHEEL_DIAM/2.0));
        this->model->GetJointController()->SetVelocityTarget(this->jright->GetScopedName(),wspeedRAux/(WHEEL_DIAM/2.0));
      }
      lastTime += common::Time(updateRate);
    }
  }
    public: virtual void Load(physics::ModelPtr _model, sdf::ElementPtr _sdf)
    {

      if(_model->GetJointCount() != 2){
        std::cerr << "Invalid joint count!\n";
        return;
      }
      this->v = _model->GetJoints();
      this->model = _model;
      this->jleft = v[0];
      this->jright = v[1];
      std::cerr << "\nJoint Left:" << this->jleft->GetName();
      std::cerr << "\nJoint Right:" << this->jright->GetName();
      std::cerr << "\nJason Plugin loaded![" << _model->GetName() << "]\n";
      this->pid = common::PID(0.1, 0, 0);
      this->model->GetJointController()->SetVelocityPID(this->jleft->GetScopedName(),this->pid);
      this->model->GetJointController()->SetVelocityPID(this->jright->GetScopedName(),this->pid);
      if(UPDATE_RATE > 0.0) this->updateRate = 1.0/UPDATE_RATE;
      else this->updateRate = 0.0;
      lastTime = model->GetWorld()->GetSimTime();
      this->updateConn = event::Events::ConnectWorldUpdateBegin(boost::bind(&JasonPlugin::Update,this));
      this->move(0.5);
    }
  private: double wspeedR = 0;
  private: double wspeedL = 0;
  private: double wspeedRAux = 0;
  private: double wspeedLAux = 0;
  private: double linearVel = 0;
  private: double angularVel = 0;
  private: double angleGoal = 0;
  private: double speedDegree = 0;
  private: double updateRate;
  private: boost::mutex lock;
  private: physics::Joint_V v;
  private: physics::ModelPtr model;
  private: physics::JointPtr jleft,jright;
  private: common::PID pid;
  private: common::Time lastTime;
  event::ConnectionPtr updateConn;
  };
  GZ_REGISTER_MODEL_PLUGIN(JasonPlugin)
}
#endif
