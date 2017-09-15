JROS
====

For tests with single agent
-----------------------------
1 - Go to jasonworld_ws directory using terminal (jasonros/jros/jasonworld_ws/)

2 - Change the actual context.
```bash
source devel/setup.bash
```
3 - Run jason_turtlebot for gazebo.
```bash
roslaunch jasonworld_gazebo jasonworld.launch
```
4 - Run Jason Application(jasonros) with the agent called "turtlebot_simple".

For tests with multiple agents
-------------------------------
1 - Go to jasonworld_ws directory using terminal (jasonros/jros/jasonworld_ws/)

2 - Change the actual context with 'source'.
```bash
source devel/setup.bash
```
3 - Run jason_turtlebot for gazebo.
```bash
roslaunch jasonworld_gazebo jasonworld_time.launch
```
4 - Run Jason Application(jasonros) with the agent called "time".

