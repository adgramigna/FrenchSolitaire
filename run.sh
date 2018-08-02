#Example: ./run.sh E 7 0 2

#!/bin/bash
javac Space.java
javac PSBoard.java
javac PSGame.java
javac PSSim.java
java PSSim $1 $2 $3 $4
