# Tweet Watch

Last project idea was scuttled, so starting a new one.

This time around, the plan is to set up an XMPP bot that can track twitter terms, and
notify you when they are mentioned, in real time.

## Setup

You will need play framework 1.1. You will need to install some modules, it will
prompt you when you 'play run'

Other dependencies are managed with Apache Ivy. The build script will automatically
install ivy if you don't have it, ant is the only dependency. To install the app's
libs, run 'ant resolve'
