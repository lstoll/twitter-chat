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
    ("$PROJECTS_HOME/app/")))
 ;; Classpath for browsing files and generates code templates
 '(jde-global-classpath 
   (quote 
    ("/usr/local/Cellar/play-framework/1.0.2/framework/lib/" 
     "/usr/local/Cellar/play-framework/1.0.2/framework/play.jar")))
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