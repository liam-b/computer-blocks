cd src/
javac.exe -cp ".;lib/*" $1.java
if [[ $? == 0 ]]; then
  java.exe -cp ".;lib/*;native/windows/*" -Djava.library.path=/native/windows $1
fi
find . -name "*.class" -type f -delete