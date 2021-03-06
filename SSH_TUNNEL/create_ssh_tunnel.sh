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
	if [ "$PROFILINGREQUIRED" == "YES" ]
	then
		PROCESSTUNNEL1="-L9001:localhost:9001 -L9002:localhost:9002 -L9003:localhost:9003 -L9004:localhost:9004 -L9005:localhost:9005 -L9006:localhost:9006"
		PROCESSTUNNEL2="-L9001:localhost:9001 -L9002:localhost:9002 -L9003:localhost:9003 -L9004:localhost:9004 -L9005:localhost:9005 -L9006:localhost:9006"
		
		echo "Use below ports for profiling"
		echo "PCS - 9001"
		echo "FCC - 9003"
		echo "HA  - 9005"
	fi
	
  ;;
ACPU2P1)
	DESTINATION_MACHINE_IP=$ACPUIP
	DESTINATION_MACHINE_SSH_PORT=$ACPUPORT
	DESTINATION_MACHINE_TUNNEL_PORT=$ACPUPORT
	DESTINATION_MACHINE_USER=$ACPUMACHINEUSER
	if [ "$PROFILINGREQUIRED" == "YES" ]
	then
		PROCESSTUNNEL1="-L9001:localhost:9001 -L9002:localhost:9002 -L9003:localhost:9003 -L9004:localhost:9004 -L9005:localhost:9005 -L9006:localhost:9006"
		PROCESSTUNNEL2="-L9001:localhost:9001 -L9002:localhost:9002 -L9003:localhost:9003 -L9004:localhost:9004 -L9005:localhost:9005 -L9006:localhost:9006"
		
		echo "Use below ports for profiling"
		echo "PCS - 9001"
		echo "FCC - 9003"
		echo "HA  - 9005"
	fi
  ;;
ACPU2P2)
	DESTINATION_MACHINE_IP=$ACPUP2IP
	DESTINATION_MACHINE_SSH_PORT=$ACPUP2PORT
	DESTINATION_MACHINE_TUNNEL_PORT=$ACPUP2PORT
	DESTINATION_MACHINE_USER=$ACPUMACHINEUSER
	if [ "$PROFILINGREQUIRED" == "YES" ]
	then
		PROCESSTUNNEL1="-L9001:localhost:9001 -L9002:localhost:9002"
		PROCESSTUNNEL2="-L9001:localhost:9001 -L9002:localhost:9002"
		echo "Use below ports for profiling"
		echo "FCC - 9001"
	fi
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
	
	echo "Use below ports for Maintenance page"
	echo "Maintenance - 8084"
fi

#
# Create Tunnel
#
#set -x
ssh -L$LOCALMACHINE_PORT:localhost:$SIMULATOR_TUNNEL_PORT $PROCESSTUNNEL1 $SIMULATOR_USER@$SIMULATOR_IP -t ssh -L$SIMULATOR_TUNNEL_PORT:localhost:$DESTINATION_MACHINE_TUNNEL_PORT $PROCESSTUNNEL2 -p $DESTINATION_MACHINE_SSH_PORT $DESTINATION_MACHINE_USER@$DESTINATION_MACHINE_IP
#set +x
