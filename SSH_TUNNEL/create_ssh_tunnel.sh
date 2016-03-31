#!/bin/bash

#################################################################################
#	Description: Creates multihop(2 Hops) ssh Tunnel for Remote Debugging	#
#################################################################################

#
# Load Conf File
#

source ssh_tunnel.conf

case $1 in
ACPU)
	DESTINATION_MACHINE_IP=$ACPUIP
	DESTINATION_MACHINE_SSH_PORT=$ACPUPORT
	DESTINATION_MACHINE_TUNNEL_PORT=$ACPUPORT
	DESTINATION_MACHINE_USER=$ACPUMACHINEUSER
	PROCESSTUNNEL1="-L9001:localhost:9001 -L9003:localhost:9003 -L9005:localhost:9005"
	PROCESSTUNNEL2="-L9001:localhost:9001 -L9003:localhost:9003 -L9005:localhost:9005"
	echo "Use below ports for profiling"
	echo "PCS - 9001"
	echo "FCC - 9003"
	echo "HA  - 9005"
  ;;
ACPU2P1)
	DESTINATION_MACHINE_IP=$ACPUIP
	DESTINATION_MACHINE_SSH_PORT=$ACPUPORT
	DESTINATION_MACHINE_TUNNEL_PORT=$ACPUPORT
	DESTINATION_MACHINE_USER=$ACPUMACHINEUSER
	PROCESSTUNNEL1="-L9001:localhost:9001 -L9003:localhost:9003 -L9005:localhost:9005"
	PROCESSTUNNEL2="-L9001:localhost:9001 -L9003:localhost:9003 -L9005:localhost:9005"
	echo "Use below ports for profiling"
	echo "PCS - 9001"
	echo "FCC - 9003"
	echo "HA  - 9005"
  ;;
ACPU2P2)
	DESTINATION_MACHINE_IP=$ACPUP2IP
	DESTINATION_MACHINE_SSH_PORT=$ACPUP2PORT
	DESTINATION_MACHINE_TUNNEL_PORT=$ACPUP2PORT
	DESTINATION_MACHINE_USER=$ACPUMACHINEUSER
	PROCESSTUNNEL1="-L9001:localhost:9001"
	PROCESSTUNNEL2="-L9001:localhost:9001"
  ;;
 *)
	# 
	# This can lead to infinite call, But i am lazy to check :P
	# Please press cntrl + c to kill the script
	#
	if [ -z $2 ]
	then
		$0 $PROFILE 1;
		exit 0;
	fi
	echo "" >&2
	echo "Please provide a valid profile name" >&2
	exit 1;
esac

if [ "$MAINTENANCEREQUIRED" == "YES" ] 
then
	PROCESSTUNNEL1="$PROCESSTUNNEL1 -L8084:localhost:8084"
	PROCESSTUNNEL2="$PROCESSTUNNEL2 -L8084:localhost:8084"
fi

#
# Create Tunnel
#
ssh -L$LOCALMACHINE_PORT:localhost:$SIMULATOR_TUNNEL_PORT $PROCESSTUNNEL1 $SIMULATOR_USER@$SIMULATOR_IP -t ssh -L$SIMULATOR_TUNNEL_PORT:localhost:$DESTINATION_MACHINE_TUNNEL_PORT $PROCESSTUNNEL2 -p $DESTINATION_MACHINE_SSH_PORT $DESTINATION_MACHINE_USER@$DESTINATION_MACHINE_IP