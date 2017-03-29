#!/bin/bash

#The PythonPath must be set to a copy of the rpi_ws281x library built on the Pi
sudo PYTHONPATH=/home/pi/facecabnet/rpi_ws281x/python/build/lib.linux-armv7l-2.7 python genUnlockLightSignal.py
