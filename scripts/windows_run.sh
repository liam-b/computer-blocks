cd src/
javac.exe -cp '.;lib/*' $(find . -type f -name "*.java")
if [[ $? == 0 ]]; then
  java.exe -cp '.;lib/*;lib/native/windows/*' $1
fi
find . -name "*.class" -type f -delete
