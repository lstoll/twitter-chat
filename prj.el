(jde-project-file-version "1.0")
(jde-set-variables
  ;; What should be put as java file header
 '(jde-gen-buffer-boilerplate 
   (quote 
    ("/*" 
     " * Tweet Eagle" 
     " * (C) 2010 Lincoln Stoll" 
     " *" 
     " */")))
 ;; Sometimes JDEE prints useful messages, but if everything works well 
 ;; you will be not using this.
 '(jde-log-max 5000)
 ;; Must be on to improve your coding: you write: "if " and JDEE generates
 ;; templeate code for "if" statement.
 '(jde-enable-abbrev-mode t)
 ;; Path to source files for automatic loading
 '(jde-sourcepath 
   (quote 
    ("./app/")))
 ;; Classpath for browsing files and generates code templates
 '(jde-global-classpath 
   (quote 
    ("/usr/local/Cellar/play/1.0.2.1/framework/play.jar"
     "/usr/local/Cellar/play/1.0.2.1/modules/gae-1.0.2/lib/appengine-api-1.0-sdk-1.3.0.jar"
"/usr/local/Cellar/app-engine-java-sdk/1.3.3.1/libexec/lib/shared/geronimo-servlet_2.5_spec-1.2.jar"
"/Users/lstoll/sw/play-1.1-unstable-r892/framework/lib/junit-4.8.1.jar"
"/usr/local/Cellar/app-engine-java-sdk/1.3.3.1/libexec/lib/testing/appengine-testing.jar"
"/Users/lstoll/sw/play-1.1-unstable-r892/modules/siena-1.1/lib/siena-0.7.0.jar"
                                        ; This is for flymakes benefit
    "/tmp/flymake-build-twitter-chat")))
 ;; This makes ecj build everything, so cross file references work
 '(jde-ecj-command-line-args '("-d"
                               "/tmp/flymake-build-twitter-chat"
                               "-1.5"
                               "-referenceInfo"
                               "-enableJavadoc"
                               "-warn:+over-ann,uselessTypeCheck,javadoc"))

 ;; Defines bracket placement style - now it is set according to SUN standards
 '(jde-gen-k&r t)
 ;; Do you prefer to have java.io.* imports or separate import for each 
 ;; used class - now it is set for importing classes separately
 '(jde-import-auto-collapse-imports nil)
 ;; You can define many JDKs and choose one for each project
 '(jde-compile-option-target (quote ("1.6")))
 ;; Nice feature sorting imports.
 '(jde-import-auto-sort t)
 ;; For syntax highlighting and basic syntax checking parse buffer
 ;; number of seconds from the time you changed the buffer.
 '(jde-auto-parse-buffer-interval 600)
 ;; Only for CygWin users it improves path resolving
 '(jde-cygwin-path-converter (quote (jde-cygwin-path-converter-cygpath)))
 ;; You can set different user name and e-mail address for each project
 '(user-mail-address "l@lds.li")
)
