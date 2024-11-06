# Detect OS (Unix-based or Windows)
# TODO: GGF AUCH ARCHITEKTUR DES PROZESSORS ERFASSEN
ifeq ($(OS), Windows_NT)
    DETECTED_OS 		:= Windows
else
    DETECTED_OS 		:= $(shell uname -s)
endif

# Define directories and files
SRC_DIR					:= src
BIN_DIR 				:= bin
BUILD_DIR 				:= build
IMAGES_DIR				:= images
JAR_DIR					:= jar
LIB_DIR 				:= libs
DEPLOYMENT_DIR 			:= deployments
MAIN_CLASS 				:= Controller.ModelWithUI
JAR_FILE 				:= sheepsmeadow.jar
VERSION					:= 0.0.0

# Define OS based commands
ifeq ($(DETECTED_OS),Windows)
    RM 					:= rmdir /S /Q
    CREATE_BINDDIR		:= if not exist $(subst /,\,$(BIN_DIR)) mkdir $(subst /,\,$(BIN_DIR))
    CREATE_BUILDDIR		:= if not exist $(subst /,\,$(BUILD_DIR)) mkdir $(subst /,\,$(BUILD_DIR))
    CREATE_DEPLOYMENTDIR:= if not exist $(subst /,\,$(DEPLOYMENT_DIR)) mkdir $(subst /,\,$(DEPLOYMENT_DIR))
    CP 					:= copy
    CP_DIR 				:= xcopy /E /I /Y
    CLASSPATH_SEP 		:= ;
    PATH_SEP 			:= \\
    UNZIP_TOOL			:= 7z
    UNZIP_JAR_LOOP      := cmd /c "FOR %%f IN ($(LIB_DIR)$(PATH_SEP)*.jar) DO $(UNZIP_TOOL) x -o$./(BUILD_DIR) ./%%f -y"
    JPACKAGE_TYPE	 	:= exe
else ifeq ($(DETECTED_OS),Linux)
    RM 					:= rm -r
    CREATE_BINDDIR		:= mkdir -p $(BIN_DIR)
    CREATE_BUILDDIR		:= mkdir -p $(BUILD_DIR)
    CREATE_DEPLOYMENTDIR:= mkdir -p $(DEPLOYMENT_DIR)
    CP 					:= cp
    CP_DIR 				:= cp -r
    CLASSPATH_SEP 		:= :
    PATH_SEP 			:= /
    UNZIP_TOOL			:= unzip
    UNZIP_JAR_LOOP 		:= for jar in $(LIB_DIR)$(PATH_SEP)*.jar; do \
        $(UNZIP_TOOL) -o -d $(BUILD_DIR) $$jar > /dev/null 2>&1; \
    done
    JPACKAGE_TYPE	 	:= deb
else ifeq ($(DETECTED_OS),Darwin)
    RM 					:= rm -r
    CREATE_BINDDIR		:= mkdir -p $(BIN_DIR)
    CREATE_BUILDDIR		:= mkdir -p $(BUILD_DIR)
    CP 					:= cp
    CP_DIR 				:= cp -r
    CLASSPATH_SEP 		:= :
    PATH_SEP 			:= /
    UNZIP_TOOL			:= unzip
    UNZIP_JAR_LOOP 		:= $(shell for jar in $(LIB_DIR)$(PATH_SEP)*.jar; do \
        $(UNZIP_TOOL) -o -d $(BUILD_DIR) $$jar > /dev/null 2>&1; \
    done)
    JPACKAGE_TYPE		:= dmg
endif

all: compile-source run

# Compile Java classes
compile-source:
	$(CREATE_BUILDDIR)
# compile sources
	javac -d $(BIN_DIR) \
	-sourcepath $(SRC_DIR) \
	-cp "src$(CLASSPATH_SEP).$(CLASSPATH_SEP)libs/*$(CLASSPATH_SEP)images/*" \
	$(SRC_DIR)/Controller/ModelWithUI.java
# copy "about page"
	$(CP) $(SRC_DIR)$(PATH_SEP)Controller$(PATH_SEP)index.html $(BIN_DIR)$(PATH_SEP)Controller
# copy images
	$(CP_DIR) .$(PATH_SEP)images $(BIN_DIR)$(PATH_SEP)images

# Compile and run the application
run: compile-source
	java -cp "$(BIN_DIR)$(CLASSPATH_SEP).$(CLASSPATH_SEP)libs/*" $(MAIN_CLASS)

compile-tests:
	find tests \
	-name '*.java' \
	-print0 | xargs -0 javac -cp "src$(CLASSPATH_SEP)$(BIN_DIR)$(CLASSPATH_SEP)libs/*" -d $(BIN_DIR)

test: compile-tests
	java --enable-preview \
	-cp $(BIN_DIR)$(CLASSPATH_SEP)libs/* \
	org.junit.runner.JUnitCore \
	$$(find bin -name "*Test.class" -type f | sed 's@^bin/\(.*\)\.class$$@\1@' | sed 's@/@.@g')

# Unzip all project dependencies
unzip-dependencies:
	$(CREATE_BUILDDIR)
	$(UNZIP_JAR_LOOP)

# Create the JAR file with dependencies
$(JAR_FILE): $(DEPLOYMENT_DIR) $(BUILD_DIR) $(BIN_DIR) compile-source unzip-dependencies
	jar cfe $(DEPLOYMENT_DIR)$(PATH_SEP)$(JAR_DIR)$(PATH_SEP)$(JAR_FILE) \
	$(MAIN_CLASS) \
	-C $(BUILD_DIR) . \
	-C $(BIN_DIR) . \

deploy-windows: $(JAR_FILE) 
	$(CREATE_DEPLOYMENTDIR)
	jpackage --app-version $(VERSION) \
	--description "Educational simulation program, to explore the world of agent-based modeling" \
	--name Sheepsmeadow \
	--input . \
	--main-jar $(DEPLOYMENT_DIR)$(PATH_SEP)$(JAR_DIR)$(PATH_SEP)$(JAR_FILE) \
	--main-class $(MAIN_CLASS) \
	--type $(JPACKAGE_TYPE) \
	--dest $(DEPLOYMENT_DIR)$(PATH_SEP)windows \
	--win-dir-chooser \
	--win-shortcut

# Deploy for Linux (.deb) and macOS (.dmg or .exe for Windows)
deploy-linux-deb: $(JAR_FILE)
	$(CREATE_DEPLOYMENTDIR)$(PATH_SEP)linux-deb
	jpackage --app-version $(VERSION) \
	--description "Educational simulation program, to explore the world of agent-based modeling" \
	--name Sheepsmeadow \
	--input . \
	--main-jar $(DEPLOYMENT_DIR)/jar/$(JAR_FILE) \
	--main-class $(MAIN_CLASS) \
	--type $(JPACKAGE_TYPE) \
	--dest $(DEPLOYMENT_DIR)/linux-deb/

deploy-macOS: $(JAR_FILE)
	$(CREATE_DEPLOYMENTDIR)$(PATH_SEP)macOS
	jpackage --app-version $(VERSION) \
	--description "Educational simulation program, to explore the world of agent-based modeling" \
	--name Sheepsmeadow \
	--input . \
	--main-jar $(DEPLOYMENT_DIR)/jar/$(JAR_FILE) \
	--main-class $(MAIN_CLASS) \
	--type $(JPACKAGE_TYPE) \
	--dest $(DEPLOYMENT_DIR)/macOS/

# ensure that those directories exist
$(BIN_DIR) $(BUILD_DIR) $(DEPLOYMENT_DIR) $(JAR_DIR):
	mkdir $@

# Clean build artifacts
clean: $(BIN_DIR) $(BUILD_DIR) $(DEPLOYMENT_DIR) 
	$(RM) \
	$(BIN_DIR)$(PATH_SEP)* \
	$(BUILD_DIR)$(PATH_SEP)* \
	$(DEPLOYMENT_DIR)$(PATH_SEP)* \

.PHONY: all compile-source run compile-tests test deploy-windows deploy-linux-deb deploy-macOS install-linux-deb clean