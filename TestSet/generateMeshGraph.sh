#!/bin/bash

# check directory. If exist, then clear, else create it
Dir="TestSet/MeshGraph"
if [ -d $Dir ]
then 
    if [ "$(ls -A $Dir)" ]
    then rm $Dir/*
    fi
else mkdir $Dir
fi

# generate Mesh graph
row=(10 20 30)
column=(20 30 40)
maxCapacity=20

# argv for java [number of row] [number of column] [max Capacity] [output file name]
for i in ${row[@]}
do
    for j in ${column[@]}
    do
        java generate_graph/MeshGenerator.java \
        $i $j 20 TestSet/MeshGraph/${i}row-${j}col-${maxCapacity}capacity.txt
    done
done