###
java -cp target/commandLine-1.0-jar-with-dependencies.jar org.example.Main --version
1.0.0
Process Completed Normally.

###
java -cp target/commandLine-1.0-jar-with-dependencies.jar org.example.Main -v
1.0.0
Process Completed Normally.

###
java -cp target/commandLine-1.0-jar-with-dependencies.jar org.example.Main --file xyz.json  
Alternate Pom File :  xyz.json
Hello world!


###

java -cp target/commandLine-1.0-jar-with-dependencies.jar org.example.Main  --help

usage: mvn [args]

Options:
-am,--also-make                                   If project list is specified, also build projects required by the list
-amd,--also-make-dependents                       If project list is specified, also build projects that depend on projects on the list
-B,--batch-mode                                   Run in non-interactive mode. Alias for --non-interactive (kept for backwards compatability)
-b,--builder <arg>                                The id of the build strategy to use
-C,--strict-checksums                             Fail the build if checksums don't match
-c,--lax-checksums                                Warn if checksums don't match
-canf,--cache-artifact-not-found <arg>            Defines caching behaviour for 'not found' artifacts. Supported values are 'true' (default), 'false'.
--color <arg>                                  Defines the color mode of the output. Supported are 'auto', 'always', 'never'.
-D <arg>                                          Define a user property
--debug                                        Produce execution verbose output (deprecated; only kept for backward compatibility)
-e,--errors                                       Produce execution error messages
-emp,--encrypt-master-password <arg>              Encrypt master security password
-ep,--encrypt-password <arg>                      Encrypt server password
-f,--file <arg>                                   Force the use of an alternate POM file (or directory with pom.xml)
-fae,--fail-at-end                                Only fail the build afterwards; allow all non-impacted builds to continue
-ff,--fail-fast                                   Stop at first failure in reactorized builds
-fn,--fail-never                                  NEVER fail the build, regardless of project result
--force-interactive                            Run in interactive mode. Overrides, if applicable, the CI environment variable and --non-interactive/--batch-mode options
-fos,--fail-on-severity <arg>                     Configure which severity of logging should cause the build to fail
-gs,--global-settings <arg>                       Alternate path for the global settings file
-gt,--global-toolchains <arg>                     Alternate path for the global toolchains file
-h,--help                                         Display help information
-itr,--ignore-transitive-repositories             If set, Maven will ignore remote repositories introduced by transitive dependencies.
-l,--log-file <arg>                               Log file where all build output will go (disables output color)
-llr,--legacy-local-repository                    UNSUPPORTED: Use of this option will make Maven invocation fail.
-N,--non-recursive                                Do not recurse into sub-projects. When used together with -pl, do not recurse into sub-projects of selected aggregators
--non-interactive                              Run in non-interactive mode. Alias for --batch-mode
-nsu,--no-snapshot-updates                        Suppress SNAPSHOT updates
-ntp,--no-transfer-progress                       Do not display transfer progress when downloading or uploading
-o,--offline                                      Work offline
-P,--activate-profiles <arg>                      Comma-delimited list of profiles to activate. Prefixing a profile with ! excludes it, and ? marks it as optional
-pl,--projects <arg>                              Comma-delimited list of specified reactor projects to build instead of all projects. A project can be specified by [groupId]:artifactId or by its relative path. Prefixing a project with ! excludes it, and ? marks it as optional
-ps,--project-settings <arg>                      Alternate path for the project settings file
-q,--quiet                                        Quiet output - only show errors
-r,--resume                                       Resume reactor from the last failed project, using the resume.properties file in the build directory
-rf,--resume-from <arg>                           Resume reactor from specified project
-s,--settings <arg>                               Alternate path for the user settings file
-sadp,--strict-artifact-descriptor-policy <arg>   Defines 'strict' artifact descriptor policy. Supported values are 'true', 'false' (default).
-t,--toolchains <arg>                             Alternate path for the user toolchains file
-T,--threads <arg>                                Thread count, for instance 4 (int) or 2C/2.5C (int/float) where C is core multiplied
-U,--update-snapshots                             Forces a check for missing releases and updated snapshots on remote repositories
-v,--version                                      Display version information
-V,--show-version                                 Display version information WITHOUT stopping build
-X,--verbose                                      Produce execution verbose output

Process Completed Normally.
 