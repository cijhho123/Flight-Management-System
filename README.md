# Flight Management System
BGU Data Structure course 2nd assignment. Written in Java.

This is a system designed to monitor flights and prevent collisions. 

# Project Overview
## Introduction
The commercial airline company **NoCrash Flying** approached you to get help on planning an air collision prevention system. The main goal of the system is to reliably assure that the distance, in the Euclidean sense, between any two given planes will be at least a specific threshold.  In addition, the system will assure that the density of the planes in the given flight space is not high. Clearly, your advised solution should be efficient as possible. 

A detailed explanation of the system is in [the Assignment document](Assignment%202.pdf).

## Implementation
You are supplied with an empty implementation of the class DataStructure (under [Skeleton](Skeleton) folder). You are required to implement DataStructure so that it supports all the required methods described (under [Implementation](Implementation) folder).
The main challenge is to code a system with the given time complexity detailed in the Assignment.
![table](/Images/time%20complexity.png)

The test files used by the staff are available under [Test Files](Test%20Files) folder.

## Theoretical questions
In addition to the hands-on part, you are required to answer several questions about the theory.
- 6.1. Describe your implementation shortly in a typed document. Explain which data structures you used and describe with words the algorithms you used for implementing the methods of DS interface.
  
- 6.2. Explain **shortly** the run time of **each** of the methods (for most methods, 1-2 lines will be sufficient).
  
- 6.3. Give a pseudo-code for the function **split(int value, Boolean axis)** and analyze the run time of your solution. All points will be given for a solution running in O(|C|) time where |C| is the number of points in the smaller group in the partition. A solution running in O(n) time will get only some of the points, and slower solutions won’t get points at all.

- 6.4. Determine and explain the run time of the method **nearestPair()** (your answer should be as optimal as possible).
  
- 6.5. Bonus (up to 5 points) – Explain how can one build from an existing DataStructure instance, two DataStructure instances, one containing all the points with X value larger than the median (included), and one containing all the points with X value smaller than the median. The build should take O(n) time. The instances must of course support all DataStructure methods correctly.

Optional – Explain how can one use the above for improving **nearestPair()** run time.

## Answers
My answers are available at [Theoretical Part.pdf](Theoretical%20Part.pdf) scored 100% and include the bonuses.

## Graphical user interface
The responsible class is [GUI.java](Implementation/GUI.java) which was provided by the course staff.

The application can load a JPG image and a TXT file describing which object is present in the image. Using the solution of Section 5, the app allows you to mark a certain area on the image and return the number of objects in the selected area (or the objects themselves). 

You should load the image and its matching special-format TXT file (format described below) first. Then you should choose the desired area by a continued left-click on the mouse followed by dragging to mark the area. The output will be shown below upon releasing the left-click. In some of the methods, the matching points in the data structure will be printed (notice in the standard state, points are not shown on the image).
