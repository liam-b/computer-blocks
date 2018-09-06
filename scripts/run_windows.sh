cd ..
javac.exe -d "bin/" -cp ".;src/lib/json.jar" $(find . -type f -name "*.java")

cd bin/
java.exe  -cp ".;../src/lib/json.jar" Index

printf "\n\n"
read -p "Press [Enter] key to begin cleanup... "

cd ..
cd scripts/

# rm $(find . -type f -name "*.class")
