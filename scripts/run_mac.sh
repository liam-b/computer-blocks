cd src/
rm *.class
rm **/*.class
rm **/**/*.class
javac $1.java
if [[ $? == 0 ]]; then
  java $1
fi