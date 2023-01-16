CUR_DIR := $(shell pwd)

compile:
	mvn clean
	mvn compile
	mvn install
	cp src/main/lib/libsmartcalc.dylib target
	cp src/main/resources/history.txt target
	chmod +x target/SmartCalc-3.0.jar

install: uninstall
	make create_file_on_desktop

create_file_on_desktop:
	mkdir ~/Desktop/SmartCalc.app
	cp target/SmartCalc-3.0.jar ~/Desktop/SmartCalc.app
	cp src/main/lib/libsmartcalc.dylib ~/Desktop/SmartCalc.app
	cp src/main/resources/history.txt ~/Desktop/SmartCalc.app
	echo "#!/bin/bash\njava -jar SmartCalc-3.0.jar" >> ~/Desktop/SmartCalc.app/SmartCalc
	chmod +x ~/Desktop/SmartCalc.app/SmartCalc

uninstall:
	rm -rf ~/Desktop/SmartCalc.app
