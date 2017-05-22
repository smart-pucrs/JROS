#ifndef _JASON_PLUGIN_HH_
#define _JASON_PLUGIN_HH_

#include <gazebo/gazebo.hh>
#include <gazebo/physics/physics.hh>
#include <iostream>

namespace gazebo
{
  class JasonPlugin : public ModelPlugin
  {
    public: JasonPlugin() {}
    public: void moveForward(double vel){
      this->model->GetJointController()->SetVelocityTarget(this->jleft->GetScopedName(),vel);
      this->model->GetJointController()->SetVelocityTarget(this->jright->GetScopedName(),vel);
    }
    public: virtual void Load(physics::ModelPtr _model, sdf::ElementPtr _sdf)
    {

      if(_model->GetJointCount() != 2){
        std::cerr << "Invalid joint count!\n";
        return;
      }
      this->pid = common::PID(0.1, 0, 0);
      this->v = _model->GetJoints();
      this->model = _model;
      this->jleft = v[0];
      this->jright = v[1];
      std::cerr << "\nJoint Left:" << this->jleft->GetName();
      std::cerr << "\nJoint Right:" << this->jright->GetName();
      std::cerr << "\nJason Plugin loaded![" << _model->GetName() << "]\n";
      this->moveForward(0.1);
    }
  private: physics::Joint_V v;
  private: physics::ModelPtr model;
  private: physics::JointPtr jleft,jright;
  private: common::PID pid;
  };
  GZ_REGISTER_MODEL_PLUGIN(JasonPlugin)
}
#endif
