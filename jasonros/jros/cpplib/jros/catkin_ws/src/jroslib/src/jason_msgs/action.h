// Generated by gencpp from file jason_msgs/action.msg
// DO NOT EDIT!


#ifndef JASON_MSGS_MESSAGE_ACTION_H
#define JASON_MSGS_MESSAGE_ACTION_H


#include <string>
#include <vector>
#include <map>

#include <ros/types.h>
#include <ros/serialization.h>
#include <ros/builtin_message_traits.h>
#include <ros/message_operations.h>


namespace jason_msgs
{
template <class ContainerAllocator>
struct action_
{
  typedef action_<ContainerAllocator> Type;

  action_()
    : agent()
    , action()
    , parameters()  {
    }
  action_(const ContainerAllocator& _alloc)
    : agent(_alloc)
    , action(_alloc)
    , parameters(_alloc)  {
  (void)_alloc;
    }



   typedef std::basic_string<char, std::char_traits<char>, typename ContainerAllocator::template rebind<char>::other >  _agent_type;
  _agent_type agent;

   typedef std::basic_string<char, std::char_traits<char>, typename ContainerAllocator::template rebind<char>::other >  _action_type;
  _action_type action;

   typedef std::vector<std::basic_string<char, std::char_traits<char>, typename ContainerAllocator::template rebind<char>::other > , typename ContainerAllocator::template rebind<std::basic_string<char, std::char_traits<char>, typename ContainerAllocator::template rebind<char>::other > >::other >  _parameters_type;
  _parameters_type parameters;




  typedef boost::shared_ptr< ::jason_msgs::action_<ContainerAllocator> > Ptr;
  typedef boost::shared_ptr< ::jason_msgs::action_<ContainerAllocator> const> ConstPtr;

}; // struct action_

typedef ::jason_msgs::action_<std::allocator<void> > action;

typedef boost::shared_ptr< ::jason_msgs::action > actionPtr;
typedef boost::shared_ptr< ::jason_msgs::action const> actionConstPtr;

// constants requiring out of line definition



template<typename ContainerAllocator>
std::ostream& operator<<(std::ostream& s, const ::jason_msgs::action_<ContainerAllocator> & v)
{
ros::message_operations::Printer< ::jason_msgs::action_<ContainerAllocator> >::stream(s, "", v);
return s;
}

} // namespace jason_msgs

namespace ros
{
namespace message_traits
{



// BOOLTRAITS {'IsFixedSize': False, 'IsMessage': True, 'HasHeader': False}
// {'jason_msgs': ['/home/iancalaca/jason/projects/JROS/jasonros/jros/cpplib/jros/catkin_ws/src/jason_msgs/msg']}

// !!!!!!!!!!! ['__class__', '__delattr__', '__dict__', '__doc__', '__eq__', '__format__', '__getattribute__', '__hash__', '__init__', '__module__', '__ne__', '__new__', '__reduce__', '__reduce_ex__', '__repr__', '__setattr__', '__sizeof__', '__str__', '__subclasshook__', '__weakref__', '_parsed_fields', 'constants', 'fields', 'full_name', 'has_header', 'header_present', 'names', 'package', 'parsed_fields', 'short_name', 'text', 'types']




template <class ContainerAllocator>
struct IsFixedSize< ::jason_msgs::action_<ContainerAllocator> >
  : FalseType
  { };

template <class ContainerAllocator>
struct IsFixedSize< ::jason_msgs::action_<ContainerAllocator> const>
  : FalseType
  { };

template <class ContainerAllocator>
struct IsMessage< ::jason_msgs::action_<ContainerAllocator> >
  : TrueType
  { };

template <class ContainerAllocator>
struct IsMessage< ::jason_msgs::action_<ContainerAllocator> const>
  : TrueType
  { };

template <class ContainerAllocator>
struct HasHeader< ::jason_msgs::action_<ContainerAllocator> >
  : FalseType
  { };

template <class ContainerAllocator>
struct HasHeader< ::jason_msgs::action_<ContainerAllocator> const>
  : FalseType
  { };


template<class ContainerAllocator>
struct MD5Sum< ::jason_msgs::action_<ContainerAllocator> >
{
  static const char* value()
  {
    return "88f4750ff844da2b938564005232aa28";
  }

  static const char* value(const ::jason_msgs::action_<ContainerAllocator>&) { return value(); }
  static const uint64_t static_value1 = 0x88f4750ff844da2bULL;
  static const uint64_t static_value2 = 0x938564005232aa28ULL;
};

template<class ContainerAllocator>
struct DataType< ::jason_msgs::action_<ContainerAllocator> >
{
  static const char* value()
  {
    return "jason_msgs/action";
  }

  static const char* value(const ::jason_msgs::action_<ContainerAllocator>&) { return value(); }
};

template<class ContainerAllocator>
struct Definition< ::jason_msgs::action_<ContainerAllocator> >
{
  static const char* value()
  {
    return "string agent\n\
string action\n\
string[] parameters\n\
";
  }

  static const char* value(const ::jason_msgs::action_<ContainerAllocator>&) { return value(); }
};

} // namespace message_traits
} // namespace ros

namespace ros
{
namespace serialization
{

  template<class ContainerAllocator> struct Serializer< ::jason_msgs::action_<ContainerAllocator> >
  {
    template<typename Stream, typename T> inline static void allInOne(Stream& stream, T m)
    {
      stream.next(m.agent);
      stream.next(m.action);
      stream.next(m.parameters);
    }

    ROS_DECLARE_ALLINONE_SERIALIZER;
  }; // struct action_

} // namespace serialization
} // namespace ros

namespace ros
{
namespace message_operations
{

template<class ContainerAllocator>
struct Printer< ::jason_msgs::action_<ContainerAllocator> >
{
  template<typename Stream> static void stream(Stream& s, const std::string& indent, const ::jason_msgs::action_<ContainerAllocator>& v)
  {
    s << indent << "agent: ";
    Printer<std::basic_string<char, std::char_traits<char>, typename ContainerAllocator::template rebind<char>::other > >::stream(s, indent + "  ", v.agent);
    s << indent << "action: ";
    Printer<std::basic_string<char, std::char_traits<char>, typename ContainerAllocator::template rebind<char>::other > >::stream(s, indent + "  ", v.action);
    s << indent << "parameters[]" << std::endl;
    for (size_t i = 0; i < v.parameters.size(); ++i)
    {
      s << indent << "  parameters[" << i << "]: ";
      Printer<std::basic_string<char, std::char_traits<char>, typename ContainerAllocator::template rebind<char>::other > >::stream(s, indent + "  ", v.parameters[i]);
    }
  }
};

} // namespace message_operations
} // namespace ros

#endif // JASON_MSGS_MESSAGE_ACTION_H
