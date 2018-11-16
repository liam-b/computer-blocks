cd src/
javac -cp '.:lib/*' $1.java
if [[ $? == 0 ]]; then
  java -cp '.:lib/*:lib/native/macos/*' -XstartOnFirstThread -Djava.library.path=lib/native/macos $1
fi
find . -name "*.class" -type f -delete
