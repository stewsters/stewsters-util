Stewsters-Util
==============

This is a collection of some code that I have pulled out of projects.  It is mostly useful for roguelikes.
![TravisCI](https://travis-ci.org/stewsters/stewsters-util.svg?branch=master)

I have tried to port any 2d algorithms I have found into 3d as well.

* Reusable predicate/brush components for map generation algorithms. (http://www.roguebasin.com/index.php?title=Designing_Flexible,_Reusable_Algorithms)
* A* pathfinding.
* A KdTree for spatial hashing
* A schedular for turns.
* Noise Functions
* Djikstra searching for objectives.
* A few Math functions that I have found useful.
* A variant of SquidLib's shadowcasting that reuses the same array.



My apologies for the lack of documentation. For examples on how to use these, look at the tests.


How to use with Gradle
=======================

Add jcenter to your repositories:

```gradle
repositories {
    jcenter()
}
```

Then add it to your dependencies:
```gradle
dependencies{
    compile 'com.stewsters.util:stewsters-util:0.8'
}
```




How to compile a JAR
====================
To compile from source you will need Java 7 and to install groovy and gradle.  I recommend using http://gvmtool.net/ for that.

If you want to jar it up for a use in a project, try this:

```bash
gradle jar
```

How to use as a git submodule
=============================

You need to add a submodule to your project

```bash
git submodule add git@github.com:stewsters/stewsters-util.git stewsters-util
git submodule init
git submodule update
```

Then set up your build system to deal with a subproject:

http://www.gradle.org/docs/current/userguide/multi_project_builds.html#sec:project_jar_dependencies

