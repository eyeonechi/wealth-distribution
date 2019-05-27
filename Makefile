# Ivan Ken Weng Chee 736901
# Shorye Chopra 689913
# Saksham Agrawal 866102

EXECUTABLE=JNetLogo
BINARY=*.class
SOURCE=*.java
COMPILER?=javac
EXECUTOR?=java
OUTPUT?=data.csv

all: original

original:
	cd src/original/ && $(MAKE)

extension1:
	cd src/extension1/ && $(MAKE)

extension2:
	cd src/extension2/ && $(MAKE)

clean:
	cd src/original/ && $(MAKE) clean
	cd src/extension1/ && $(MAKE) clean
	cd src/extension2/ && $(MAKE) clean
