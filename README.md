

This is a collection of some code that I have pulled out of projects.  It is mostly useful for roguelikes.


How to compile a JAR
====================
To compile from source you will need Java 7 and to install groovy and gradle.  I recommend using http://gvmtool.net/ for that.

If you want to jar it up for a use in a project, try this:

```bash
gradle jar
```


How to use a git submodule
==========================

I am doing this because I am tired of copying, yet do not yet want to mess with deploying to maven.  Someday.

You need to add a submodule to your project

```bash
git submodule add git@github.com:stewsters/stewsters-util.git stewsters-util
git submodule init
git submodule update
```

Then set up your build system to deal with a subproject:

http://www.gradle.org/docs/current/userguide/multi_project_builds.html#sec:project_jar_dependencies

