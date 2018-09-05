cd src/
rm *.class
rm **/*.class
rm **/**/*.class
javac -cp '.:lib/json.jar' $1.java
if [[ $? == 0 ]]; then
  java -cp '.:lib/json.jar' $1
fi