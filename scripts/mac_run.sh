cd src/
javac -cp '.:lib/json.jar' $1.java
if [[ $? == 0 ]]; then
  java -cp '.:lib/json.jar' $1
fi
find . -name "*.class" -type f -delete
