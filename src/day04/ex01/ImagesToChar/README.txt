#delete target if it exists
rm -rf target

#create target directory
mkdir target

#compile java source files
javac src/java/edu/school21/printer/*/*.java -d ./target

#copy resources
cp -r src/resources target/

#create archive
jar cfm target/images-to-chars-printer.jar src/manifest.txt -C target .

#run program
java -jar target/images-to-chars-printer.jar . 0