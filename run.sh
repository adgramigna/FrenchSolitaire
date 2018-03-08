#!/bin/bash
javac Space.java
javac FSSim.java
javac FSBoard.java
java FSSim $1 $2 $3 $4
