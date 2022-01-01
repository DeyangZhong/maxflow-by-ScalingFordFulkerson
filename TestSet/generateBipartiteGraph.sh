#!/bin/bash

# check directory. If exist, then clear, else create it
Dir="TestSet/BipartiteGraph"
if [ -d $Dir ]
then 
    if [ "$(ls -A $Dir)" ]
    then rm $Dir/*
    fi
else mkdir $Dir
fi

# generate Bipartite graph
leftSide=(10 20 100) # number of source side
rightSide=(15 30 90)  # number of target side
maxPro=0.8 # max probalitity of a edge to create
min=5 # the lower bound on edge capacities
max=20 # the upper bound on edge capacities

# argv for java: [number of left side] [number of right side] [max probality for an edge] [min capacity] [max capacity] [output file name]

for i in ${leftSide[@]}
do
    for j in ${rightSide[@]}
    do
        java generate_graph/BipartiteGraph.java $i $j \
        $maxPro $min $max TestSet/BipartiteGraph/${i}l-${j}r-${maxPro}maxPro-${min}minCapa-${max}maxCapa.txt
    done
done