#delete target if it exists
rm -rf target

#create target directory
mkdir target

#compile java source files
javac src/java/edu/school21/printer/*/*.java -d ./target

#run program
java -cp target/ edu.school21.printer.app.Program . 0 /Users/cbridget/JavaPiscine/src/day04/ex00/ImagesToChar/it.bmp