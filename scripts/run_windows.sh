cd ..
javac.exe -d "bin/" $(find . -type f -name "*.java")

cd bin/
java.exe Index

printf "\n\n"
read -p "Press [Enter] key to begin cleanup... "

cd ..
cd scripts/

rm $(find . -type f -name "*.class")
