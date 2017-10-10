# George Poulos : CS441 HW2

# Description
This Project uses the jedis library located [here](https://github.com/xetorthio/jedis) and runs Junit Tests to 
create a coverage report. The coverage report is then parsed from XML to CSV using a parser that I created with some 
guidance from [here](http://www.beingjavaguys.com/2013/06/jdom-xml-parser-in-java.html). After parsing this xml file, 
I then create a jar of my map-reduce implementation to use on the on an AWS EMR cluster. 

## Import Using Intellij
    1. Create new Project using VCS in Intellij
    2. Use Gradle when importing project

## Run with .gradle 
From the project root, do the following

    gradle clean  //clean first
    gradle build  //build
    gradle test   //run JUnit tests
    gradle cloverCoverageReport //makes XML coverage report
    cd map-reduce //switch project dir
    gradle jar 
    
*NOTE Now you must open Intellij and Run the file located at ./map-reduce/src/XMLToCSV
This will convert the xml to a easier to read csv file.


## Run with .sbt
From the project root, do the following

    sbt makeReport //compiles and runs tests also makes coverage report
    cd map-reduce //switch project dir
    gradle jar //convert
    
*NOTE Now you must open Intellij and Run the file located at `./map-reduce/src/XMLToCSV.java`
This will convert the xml to a easier to read csv file. The CSV file will be located at 
*/build/reports/clover/export.csv

*NOTE Coverage Report is located at `*/build/reports/clover/clover.xml` after running 
`sbt makeReport`

*NOTE The map-reduce jar will be located at `*/map-reduce/build/libs/map-reduce.jar`


# Limitations
Some limitations of this implementation are I did not have a chance to automate the whole build using SBT.
Also, I would like to create a better algorithm to parse the XML to CSV, currently O(n^3) time.
