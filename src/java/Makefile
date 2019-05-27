# Ivan Ken Weng Chee 736901
# Shorye Chopra 689913
# Saksham Agrawal 866102

EXECUTABLE=JNetLogo
BINARY=*.class
SOURCE=*.java
COMPILER?=javac
EXECUTOR?=java
OUTPUT?=data.csv

all: compile run

compile:
	${COMPILER} ${SOURCE}

run:
	${EXECUTOR} ${EXECUTABLE}

clean:
	rm ${BINARY}
