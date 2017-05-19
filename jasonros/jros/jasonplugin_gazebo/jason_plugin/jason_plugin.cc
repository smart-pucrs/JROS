#ifndef _VELODYNE_PLUGIN_HH_
#define _VELODYNE_PLUGIN_HH_

#include <gazebo/gazebo.hh>
#include <gazebo/physics/physics.hh>
#include <iostream>

namespace gazebo
{
  /// \brief A plugin to control a Velodyne sensor.
  class JasonPlugin : public ModelPlugin
  {
    /// \brief Constructor
    public: JasonPlugin() {}

    /// \brief The load function is called by Gazebo when the plugin is
    /// inserted into simulation
    /// \param[in] _model A pointer to the model that this plugin is
    /// attached to.
    /// \param[in] _sdf A pointer to the plugin's SDF element.
    public: virtual void Load(physics::ModelPtr _model, sdf::ElementPtr _sdf)
    {
      // Just output a message for now
      std::cerr << "\nJason Plugin loaded![" <<
        _model->GetName() << "]\n";
        std::cout << "\nJasonPlugin loaded!!!\n";
    }
  };

  // Tell Gazebo about this plugin, so that Gazebo can call Load on this plugin.
  GZ_REGISTER_MODEL_PLUGIN(JasonPlugin)
}
#endif
