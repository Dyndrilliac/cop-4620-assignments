#!/bin/bash
#########################################################
# Automated test bed script for COP 4620 Project 3.     #     
#########################################################
# Author:	Matthew Boyette (Dyndrilliac@gmail.com) #
# Date:		3/22/2019                               #
#########################################################
# Define an automated test bed function.
# Takes 4 arguments: cmd, prj, type, and num.
# See below for explanations of each argument and their assumed constraints.
run_tests () {
	# Handle function arguments.
	if [[ $# != 4 ]]
	then
		echo "*RUNTIME ERROR*: Incorrect number of arguments passed to $0."
	fi
	cmd=$1	# Command used to execute the program being tested.
	prj=$2	# Prefix used by the project's test input files. ("lex" for P1, "parse" for P2, "seman" for P3, "code" for P4, and "sql" for P5)
	type=$3	# Test type. ("a" for acceptance tests, "r" for rejection tests)
	num=$4	# Number of tests to run for the given type.

	# Initialize useful variables.
	underscore="_"	# Constant used for dynamically generating test input file name strings.
	t="t"		# Constant used for dynamically generating test input file name strings.
	ext=""		# File extension for the given project's test input files. ("txt" for P1/P5, "c" for P2/P3/P4)
	success=""	# Success string expected for the given test type. ("ACCEPT" for acceptance tests, "REJECT" for rejection tests)

	# Set ext based on prj.
	if [[ ($prj = "lex") || ($prj = "sql") ]]
	then
		ext="txt"
	else
		ext="c"
	fi

	# Set success based on type.
	if [[ $type = "a" ]]
	then
		success="ACCEPT"
	elif [[ $type = "r" ]]
	then
		success="REJECT"
	fi

	# Run our tests in a loop.
	counter=1
	while [ $counter -le $num ]
	do
		# Dynamically generate test input file name strings.
		file=""
		if [[ $counter -lt 10 ]]
		then
			zero="0"
			file="\"./Test Files/$prj$underscore$type$underscore$t$zero$counter.$ext\""
		else
			file="\"./Test Files/$prj$underscore$type$underscore$t$counter.$ext\""
		fi
		# Capture the STDOUT stream from the program being tested.
		command=""
		if [[ $prj != "sql" ]]
		then
			command="$cmd $file"
		else
			command="$cmd < $file"
		fi
		result=$(eval $command)
		# Check to see if the actual output matches the expected output.
		if [[ $result = $success ]]
		then
			echo "$file: SUCCESS!"
			((counter++))
			continue
		else
			echo "$file: FAILURE!"
		fi
	done
}

cd ../..

# Run acceptance tests.
echo "Running acceptance tests..."
run_tests ./Binaries/P3.jar seman a 22

# Run rejection tests.
echo "Running rejection tests..."
run_tests ./Binaries/P3.jar seman r 22
