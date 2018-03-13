#!/bin/bash
javac Space.java
javac FSSim.java
javac FSBoard.java
javac PSGame.java
java FSSim $1 $2 $3 $4
