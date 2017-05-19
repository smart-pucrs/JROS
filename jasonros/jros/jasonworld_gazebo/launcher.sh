#!/bin/bash
DIR=$(pwd)
export DIR=${DIR}
export GAZEBO_MODEL_PATH=${DIR}/models/
roslaunch ${DIR}/launch/jasonworld.launch
