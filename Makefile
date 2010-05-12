install:
	find . -name "*.scala" -print0 | xargs -0 fsc -cp ~/sw/play-1.1-unstable-r892/framework/play.jar:~/sw/play-1.1-unstable-r892/framework/lib/*.jar -d bin/

#tags:
#	ctags -h ".scala" -R --exclude="*.js" --exclude="tmp" --exclude="*.class" -e

check-syntax:
	-mkdir /tmp/flymake-build-twitter-chat 2>/dev/null
	fsc -classpath ${HOME}/sw/play-1.1-unstable-r892/framework/play.jar:${HOME}/sw/play-1.1-unstable-r892/framework/lib/jregex-1.2_01.jar:${HOME}/sw/play-1.1-unstable-r892/framework/lib/javassist-3.9.0.GA.jar:${HOME}/sw/play-1.1-unstable-r892/framework/lib/gson-1.4.jar:${HOME}/sw/play-1.1-unstable-r892/modules/siena-1.1/lib/siena-0.7.0.jar:${HOME}/sw/play-1.1-unstable-r892/framework/lib/log4j-1.2.15.jar:${HOME}/sw/play-1.1-unstable-r892/modules/scala-0.3/lib/play-scala.jar:/tmp/flymake-build-twitter-chat -sourcepath app -d /tmp/flymake-build-twitter-chat ${CHK_SOURCES}