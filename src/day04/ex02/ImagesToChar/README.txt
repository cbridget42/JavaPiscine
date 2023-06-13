#delete target and lib if it exists
rm -rf target lib

#create target and lib directories
mkdir target lib

#download JCommander and JCDP
curl https://repo1.maven.org/maven2/com/beust/jcommander/1.82/jcommander-1.82.jar -o lib/jcommander-1.82.jar
curl https://repo1.maven.org/maven2/com/diogonunes/JCDP/4.0.2/JCDP-4.0.2.jar -o lib/JCDP-4.0.2.jar

#extract files
cd target
jar xf ../lib/jcommander-1.82.jar
jar xf ../lib/JCDP-4.0.2.jar
rm -rf META-INF
cd ..

#compile java source files
#javac -classpath lib/jcommander-1.82.jar lib/JCDP-4.0.2.jar src/java/edu/school21/printer/*/*.java -d ./target
javac -classpath lib/\* src/java/edu/school21/printer/*/*.java -d ./target

#copy resources
cp -r src/resources target/

#create archive
jar cfm target/images-to-chars-printer.jar src/manifest.txt -C target .

#run program
java -jar target/images-to-chars-printer.jar --white=RED --black=GREEN