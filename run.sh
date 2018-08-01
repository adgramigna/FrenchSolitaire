#!/bin/bash
javac Space.java
javac PSBoard.java
javac PSGame.java
javac PSSim.java
java PSSim $1 $2 $3 $4
