#!/bin/bash

# check directory. If exist, then clear, else create it
Dir="TestSet/RandomGraph"
if [ -d $Dir ]
then 
    if [ "$(ls -A $Dir)" ]
    then rm $Dir/*
    fi
else mkdir $Dir
fi

# generate Random graph
array1=(10 20 100) # number of vertices
array2=(3 5 8) # number of edges from one node
min=5 # the lower bound on edge capacities
max=20 # the upper bound on edge capacities

# argv to RandomGraph.java is [number of vertices] [number os edges] [min capacity] [max capacity] [output file name]
for i in ${array1[@]}
do
    for j in ${array2[@]}
    do
        java generate_graph/RandomGraph.java \
        $i $j $min $max TestSet/RandomGraph/${i}v-${j}out-${min}min-${max}max.txt
    done
done