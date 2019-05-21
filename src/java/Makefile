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
