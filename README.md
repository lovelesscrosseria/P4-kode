# P4-kode
This is a compiler for the Java-based language Rooster, that makes it easier to write strategies for Robocode.

# Assumptions
The Rooster compiler assumes that Robocode is installed and a folder with the name 'Rooster' is present in "C:\robocode\robots" and Java version 11+ is installed on the machine

# To compile
Run the following commands in a windows cmd inside the folder 'P4-kode'

doskey antlr4 = java  -cp antlr-4.8-complete.jar org.antlr.v4.Tool $*

antlr4 src/robo.g4 -visitor -package GrammarOut -o src/GrammarOut
