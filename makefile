# Detect OS (Unix-based or Windows)
ifeq ($(OS),Windows_NT)
    RM = rmdir /S /Q
    MKDIR = if not exist $(subst /,\,$(BUILD_DIR)) mkdir $(subst /,\,$(BUILD_DIR))
    CP = copy
    CP_DIR = xcopy /E /I /Y
    CLASSPATH_SEP = ;
    PATH_SEP = \
else
    RM = rm -rf
    MKDIR = mkdir -p
    CP = cp
    CP_DIR = cp -r
    CLASSPATH_SEP = :
    PATH_SEP = /
    JPACKAGE_TYPE_LINUX = deb
    JPACKAGE_TYPE_MAC = dmg
endif


# Define directories and files
SRC_DIR = src
BUILD_DIR = bin
JAR_DIR = build/jar
LIB_DIR = libs
DEPLOYMENT_DIR = deployments
MAIN_CLASS = Controller.ModelWithUI
JAR_FILE = sheepsmeadow.jar

# Default target
# Compile Java classes
compile-source:
	$(MKDIR) $(BUILD_DIR)
	javac -d $(BUILD_DIR) -sourcepath $(SRC_DIR) -cp "src$(CLASSPATH_SEP).$(CLASSPATH_SEP)libs/*$(CLASSPATH_SEP)images/*" $(SRC_DIR)/Controller/ModelWithUI.java
	$(CP) $(SRC_DIR)$(PATH_SEP)Controller$(PATH_SEP)index.html $(BUILD_DIR)$(PATH_SEP)Controller
	$(CP_DIR) images $(BUILD_DIR)

# Compile and run the application
run: compile-source
	java -cp "$(BUILD_DIR)$(CLASSPATH_SEP)libs/*" $(MAIN_CLASS)

compile-tests:
	find tests -name '*.java' -print0 | xargs -0 javac -cp "src$(CLASSPATH_SEP)$(BUILD_DIR)$(CLASSPATH_SEP)libs/*" -d $(BUILD_DIR)

test: compile-tests
	java --enable-preview -cp $(BUILD_DIR)$(CLASSPATH_SEP)libs/* org.junit.runner.JUnitCore $$(find bin -name "*Test.class" -type f | sed 's@^bin/\(.*\)\.class$$@\1@' | sed 's@/@.@g')

# Unzip all project dependencies
unzip-dependencies:
	$(MKDIR) $(JAR_DIR)
	for jar in $(LIB_DIR)/*.jar; do \
		unzip -o -d $(JAR_DIR) $$jar > /dev/null 2>&1; \
	done

# Create the JAR file with dependencies
$(JAR_FILE): compile-source unzip-dependencies
	jar cfe $(DEPLOYMENT_DIR)/jar/$(JAR_FILE) $(MAIN_CLASS) -C $(JAR_DIR) . -C $(BUILD_DIR) . -C $(BUILD_DIR)/images .

# Clean build artifacts
clean:
	$(RM) $(BUILD_DIR) $(JAR_DIR) $(JAR_FILE)

# Deploy for Linux (.deb) and macOS (.dmg or .exe for Windows)
deploy-linux-deb: $(JAR_FILE)
	$(MKDIR) ./$(DEPLOYMENT_DIR)/linux-deb/
	jpackage --name Sheepsmeadow --input . --main-jar $(DEPLOYMENT_DIR)/jar/$(JAR_FILE) --main-class $(MAIN_CLASS) --type $(JPACKAGE_TYPE_LINUX) --dest $(DEPLOYMENT_DIR)/linux-deb/

deploy-macOS: $(JAR_FILE)
	$(MKDIR) ./$(DEPLOYMENT_DIR)/macOS/
	jpackage --name Sheepsmeadow --input . --main-jar $(DEPLOYMENT_DIR)/jar/$(JAR_FILE) --main-class $(MAIN_CLASS) --type $(JPACKAGE_TYPE_MAC) --dest $(DEPLOYMENT_DIR)/macOS/

install-linux-deb: deploy-linux-deb
	$(MKDIR) /tmp/sheeps
