#!/bin/bash                                                                                                          
echo "$(date '+%Y-%m-%d %H:%M:%S')" >> /tmp/logTime.log                                                              
echo "Time logged: $(tail -1 /tmp/logTime.log)" 
