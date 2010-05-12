classpath := $(shell find deps -name "*.jar" -print | tr '\n' ':')
classpath := ${classpath}:$(shell find lib -name "*.jar" -print | tr '\n' ':')

check-syntax:
	-mkdir /tmp/flymake-build-twitter-chat 2>/dev/null
	fsc -classpath ${classpath} -sourcepath app\
		-d /tmp/flymake-build-twitter-chat ${CHK_SOURCES}