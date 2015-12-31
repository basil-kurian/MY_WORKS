#!/bin/bash

#################################################################################
#	Description: Creates multihop(2 Hops) ssh Tunnel for Remote Debugging	#
#################################################################################

#
# Load Conf File
#

source ssh_tunnel.conf

#
# Create Tunnel
#

ssh -L$LOCALMACHINE_PORT:localhost:$SIMULATOR_TUNNEL_PORT $SIMULATOR_USER@$SIMULATOR_IP -t ssh -L$SIMULATOR_TUNNEL_PORT:localhost:$DESTINATION_MACHINE_TUNNEL_PORT -p $DESTINATION_MACHINE_SSH_PORT $DESTINATION_MACHINE_USER@$DESTINATION_MACHINE_IP
