Stewsters-Util
==============

This is a collection of some code that I have pulled out of projects.  It is mostly useful for roguelikes.

[ ![Download](https://api.bintray.com/packages/stewsters/maven/stewsters-util/images/download.svg) ](https://bintray.com/stewsters/maven/stewsters-util/_latestVersion)
![TravisCI](https://travis-ci.org/stewsters/stewsters-util.svg?branch=master)

I have tried to port any 2d algorithms I have found into 3d as well.

* Reusable predicate/brush components for map generation algorithms. (http://www.roguebasin.com/index.php?title=Designing_Flexible,_Reusable_Algorithms)
* Kevin Glass's A* pathfinding for 2d grids which I have extended to 3d.
* A KdTree for spatial hashing.
* A schedular for turns.
* OpenSimplex Noise Functions for 2d, 3d, and 4d noise.
* Djikstra searching for objectives.
* Djikstra map to quickly allow many entities to path to one objective.
* A few Math functions that I have found useful.
* A variant of SquidLib's shadowcasting that reuses the same array.



My apologies for the lack of documentation. For examples on how to use these, look at the tests folder.


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
    compile 'com.stewsters.util:stewsters-util:0.17'
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

This is really only a good idea if you intend on editing this library within another program.

```bash
git submodule add git@github.com:stewsters/stewsters-util.git stewsters-util
git submodule init
git submodule update
```

Then set up your build system to deal with a subproject:

http://www.gradle.org/docs/current/userguide/multi_project_builds.html#sec:project_jar_dependencies

