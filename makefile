# Define directories and files
# directory with the source code
SRC_DIR = src
# directory with all compiled classes
BUILD_DIR = bin
# directory to store all .jar files of the project dependencies
JAR_DIR = build/jar
# dependencies of the project
LIB_DIR = libs
# directory for the binaries
DEPLOYMENT_DIR = deployments
# entry point in the program
MAIN_CLASS = Controller.ModelWithUI
# name of the output .jar-file
JAR_FILE = sheepsmeadow.jar


# Default target
all: $(JAR_FILE) deploy clean

# Compile Java classes
compile-source:
	mkdir -p $(BUILD_DIR)
	'javac' -d $(BUILD_DIR) -sourcepath $(SRC_DIR) -cp "src:.:libs/*" src/Controller/ModelWithUI.java

# compile and run the application
run: compile-source
	'java' -cp "bin:libs/*" $(MAIN_CLASS)

compile-tests:
	find tests -name '*.java' -print0 | xargs -0 javac -cp "src:bin:libs/*" -d bin

test: compile-tests
	java --enable-preview -cp bin:libs/* org.junit.runner.JUnitCore $$(find bin -name "*Test.class" -type f | sed 's@^bin/\(.*\)\.class$$@\1@' | sed 's@/@.@g')

# Unzip all project dependencies
unzip-dependencies:
	mkdir -p $(JAR_DIR)
	for jar in $(LIB_DIR)/*.jar; do \
		unzip -o -d $(JAR_DIR) $$jar > /dev/null 2>&1; \
	done

# Create the JAR file with dependencies
$(JAR_FILE): compile-source unzip-dependencies
	jar cfe $(DEPLOYMENT_DIR)/jar/$(JAR_FILE) $(MAIN_CLASS) -C $(JAR_DIR) . -C $(BUILD_DIR) .

# Clean build artifacts
clean:
	rm -rf $(BUILD_DIR)/* $(JAR_DIR) $(JAR_FILE) 


deploy-linux-deb: $(JAR_FILE)
	# create .deb
	mkdir -p ./$(DEPLOYMENT_DIR)/linux-deb/
	jpackage --name Sheepsmeadow --input . --main-jar $(DEPLOYMENT_DIR)/jar/sheepsmeadow.jar --main-class Controller.ModelWithUI --type deb --dest $(DEPLOYMENT_DIR)/linux-deb/

install-linux-deb: deploy-linux-deb
	# copy .deb to /tmp
	mkdir -p /tmp/sheepsmeadow
	cp $(DEPLOYMENT_DIR)/linux-deb/sheepsmeadow_1.0_amd64.deb /tmp/sheepsmeadow
	sudo apt install /tmp/sheepsmeadow/sheepsmeadow_1.0_amd64.deb


# declare "phony-targets" to prevent conflicts with files that have the same name as the "phony-target"
.PHONY: all compile-source compile-tests unzip-dependencies clean run deploy-linux-deb install-linux-deb
