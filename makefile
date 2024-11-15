# Detect OS (Unix-based or Windows)
# TODO: GGF AUCH ARCHITEKTUR DES PROZESSORS ERFASSEN
ifeq ($(OS), Windows_NT)
    DETECTED_OS 		:= Windows
else
    DETECTED_OS 		:= $(shell uname -s)
endif

# get dir of this makefile
MKFILE_PATH				:= $(abspath $(lastword $(MAKEFILE_LIST)))

# Define directories and files
SRC_DIR					:= src
BIN_DIR 				:= bin
BUILD_DIR 				:= build
IMAGES_DIR				:= images
TEST_DIR				:= tests
JAR_DIR					:= jar
LIB_DIR 				:= libs
DEPLOYMENT_DIR 			:= deployments
MAIN_CLASS 				:= Controller.ModelWithUI
JAR_FILE 				:= sheepsmeadow.jar
ABOUT_PAGE				:= index.html
VERSION					:= 0.0.0
APP_NAME				:= Sheepsmeadow_$(VERSION)

# Define OS based commands
ifeq ($(DETECTED_OS),Windows)
    PATH_SEP					:= \\
    CURRENT_DIR 				:= $(patsubst %$(PATH_SEP),%,$(subst /,\,$(dir $(MKFILE_PATH))))
    RM 							:= rmdir /S /Q
    CREATE_BINDDIR				:= if not exist $(subst /,\,$(BIN_DIR)) mkdir $(subst /,\,$(BIN_DIR))
    CREATE_BUILDDIR				:= if not exist $(subst /,\,$(BUILD_DIR)) mkdir $(subst /,\,$(BUILD_DIR))
    CREATE_DEPLOYMENTDIR		:= if not exist $(subst /,\,$(DEPLOYMENT_DIR)) mkdir $(subst /,\,$(DEPLOYMENT_DIR))
    LIST_SRC_FILES				:= dir /B /S $(SRC_DIR)$(PATH_SEP)*.java | findstr /V /I "Localtest.java"
    SOURCES 					:= $(shell $(LIST_SRC_FILES))
    CLASSES 					:= $(patsubst %.java,%.class,$(subst $(CURRENT_DIR)$(SRC_DIR),$(BIN_DIR),$(SOURCES)))
    LIST_TEST_FILES				:= dir /B /S $(TEST_DIR)$(PATH_SEP)*.java
    TEST_FILES					:= $(shell $(LIST_TEST_FILES))
    TEST_FILES_TARGET			:= $(patsubst %.java,%.class, $(subst $(CURRENT_DIR)$(TEST_DIR),$(BIN_DIR),$(TEST_FILES)))
    CP 							:= copy
    CP_DIR 						:= xcopy /E /I /Y
    CLASSPATH_SEP 				:= ;
    JPACKAGE_TYPE	 			:= exe
    ABOUT_PAGE_SOURCE			:= $(SRC_DIR)$(PATH_SEP)Controller$(PATH_SEP)$(ABOUT_PAGE)
    ABOUT_PAGE_TARGET			:= $(subst $(SRC_DIR), $(BIN_DIR), $(ABOUT_PAGE_SOURCE))
    IMAGES_DIR_TARGET			:= $(BIN_DIR)$(PATH_SEP)$(IMAGES_DIR)
else ifeq ($(DETECTED_OS),Linux)
    PATH_SEP 					:= /
    CURRENT_DIR 				:= $(patsubst %$(PATH_SEP),%,$(dir $(MKFILE_PATH)))
    RM 							:= rm -r
    CREATE_BINDDIR				:= mkdir -p $(BIN_DIR)
    CREATE_BUILDDIR				:= mkdir -p $(BUILD_DIR)
    CREATE_DEPLOYMENTDIR		:= mkdir -p $(DEPLOYMENT_DIR)
    LIST_SRC_FILES				:= find $(SRC_DIR) -name "*.java" ! -name "Localtest.java"
    SOURCES 					:= $(shell $(LIST_SRC_FILES))
    CLASSES 					:= $(patsubst %.java,%.class, $(subst $(SRC_DIR),$(BIN_DIR),$(SOURCES)))
    LIST_TEST_FILES				:= find $(TEST_DIR) -name "*.java"
    TEST_FILES					:= $(shell $(LIST_TEST_FILES))
    TEST_FILES_TARGET			:= $(patsubst %.java,%.class, $(subst $(TEST_DIR),$(BIN_DIR),$(TEST_FILES)))
    CP 							:= cp
    CP_DIR 						:= cp -r
    CLASSPATH_SEP 				:= :
    JPACKAGE_TYPE	 			:= deb
    ABOUT_PAGE_SOURCE			:= $(SRC_DIR)$(PATH_SEP)Controller$(PATH_SEP)$(ABOUT_PAGE)
    ABOUT_PAGE_TARGET			:= $(subst $(SRC_DIR), $(BIN_DIR), $(ABOUT_PAGE_SOURCE))
    IMAGES_DIR_TARGET			:= $(BIN_DIR)$(PATH_SEP)$(IMAGES_DIR)
else ifeq ($(DETECTED_OS),Darwin)
    PATH_SEP 					:= /
    CURRENT_DIR 				:= $(patsubst %$(PATH_SEP),%,$(dir $(MKFILE_PATH)))
    RM 							:= rm -r
    CREATE_BINDDIR				:= mkdir -p $(BIN_DIR)
    CREATE_BUILDDIR				:= mkdir -p $(BUILD_DIR)
    CREATE_DEPLOYMENTDIR		:= mkdir -p $(DEPLOYMENT_DIR)
    LIST_SRC_FILES				:= find $(SRC_DIR) -name "*.java" ! -name "Localtest.java"
    SOURCES 					:= $(shell $(LIST_SRC_FILES))
    CLASSES 					:= $(patsubst %.java,%.class, $(subst $(SRC_DIR),$(BIN_DIR),$(SOURCES)))
    LIST_TEST_FILES				:= find $(TEST_DIR) -name "*.java"
    TEST_FILES					:= $(shell $(LIST_TEST_FILES))
    TEST_FILES_TARGET			:= $(patsubst %.java,%.class, $(subst $(TEST_DIR),$(BIN_DIR),$(TEST_FILES)))
    CP 							:= cp
    CP_DIR 						:= cp -r
    CLASSPATH_SEP 				:= :
    JPACKAGE_TYPE				:= dmg
    ABOUT_PAGE_SOURCE			:= $(SRC_DIR)$(PATH_SEP)Controller$(PATH_SEP)$(ABOUT_PAGE)
    ABOUT_PAGE_TARGET			:= $(subst $(SRC_DIR), $(BIN_DIR), $(ABOUT_PAGE_SOURCE))
    IMAGES_DIR_TARGET			:= $(BIN_DIR)$(PATH_SEP)$(IMAGES_DIR)
endif

all: compile run

t: 
	@echo $(TEST_FILES_TARGET)
	@echo
	@echo $(CLASSES)

compile: $(CLASSES)

# copy "about page" for the app
$(ABOUT_PAGE_TARGET): $(ABOUT_PAGE_SOURCE) | $(BIN_DIR) $(SRC_DIR)
	$(CP) $< $@

# copy images for the app
$(IMAGES_DIR_TARGET): $(IMAGES_DIR)
	$(CP_DIR) $< $@

# compiles a file like "bin\Controller\ModelWithUI.class" if the corresponding file in "src\Controller\ModelWithUI.java" exists
$(BIN_DIR)$(PATH_SEP)%.class: $(SRC_DIR)$(PATH_SEP)%.java | $(BIN_DIR) $(SRC_DIR)
	javac -d $(BIN_DIR) \
	-cp "$(SRC_DIR)$(CLASSPATH_SEP).$(CLASSPATH_SEP)$(LIB_DIR)/*$(CLASSPATH_SEP)$(IMAGES_DIR)/*" \
	$<	

# Compile and run the application
run: compile $(ABOUT_PAGE_TARGET) $(IMAGES_DIR_TARGET)
	java -cp "$(BIN_DIR)$(CLASSPATH_SEP).$(CLASSPATH_SEP)$(LIB_DIR)/*" $(MAIN_CLASS)

# compile a test file
$(BIN_DIR)$(PATH_SEP)%.class: $(TEST_DIR)$(PATH_SEP)%.java | $(BIN_DIR) $(TEST_DIR)
	javac -d $(BIN_DIR) \
	-cp "$(SRC_DIR)$(CLASSPATH_SEP)$(BIN_DIR)$(CLASSPATH_SEP)$(LIB_DIR)/*$(CLASSPATH_SEP)$(TEST_DIR)" \
	$< 

compile-test: compile $(TEST_FILES_TARGET)

# run test files
test: compile-test
	java \
	--enable-preview \
	-cp "$(BIN_DIR)$(CLASSPATH_SEP)$(LIB_DIR)/*$(CLASSPATH_SEP)$(TEST_DIR)" \
	org.junit.runner.JUnitCore \
	$(patsubst %.class,%,$(filter %Test.class,$(subst $(BIN_DIR).,,$(subst $(PATH_SEP),.,$(TEST_FILES_TARGET)))))

# Create the JAR file with dependencies
$(DEPLOYMENT_DIR)$(PATH_SEP)$(JAR_DIR)$(PATH_SEP)$(JAR_FILE): $(CLASSES) | $(DEPLOYMENT_DIR) $(BIN_DIR) $(LIB_DIR)
	jar cfe $(DEPLOYMENT_DIR)$(PATH_SEP)$(JAR_DIR)$(PATH_SEP)$(JAR_FILE) \
	$(MAIN_CLASS) \
	-C $(BIN_DIR) . \
	-C $(LIB_DIR) . \

deploy-windows: $(DEPLOYMENT_DIR)$(PATH_SEP)$(JAR_DIR)$(PATH_SEP)$(JAR_FILE) 
	$(CREATE_DEPLOYMENTDIR)
	jpackage --app-version $(VERSION) \
	--description "Educational simulation program, to explore the world of agent-based modeling" \
	--icon "$(IMAGES_DIR)$(PATH_SEP)sheepsmeadow32x32.ico" \
	--name $(APP_NAME) \
	--input . \
	--main-jar $(DEPLOYMENT_DIR)$(PATH_SEP)$(JAR_DIR)$(PATH_SEP)$(JAR_FILE) \
	--main-class $(MAIN_CLASS) \
	--type $(JPACKAGE_TYPE) \
	--dest $(DEPLOYMENT_DIR)$(PATH_SEP)windows \
	--win-dir-chooser \
	--win-shortcut

# Deploy for Linux (.deb) and macOS (.dmg or .exe for Windows)
deploy-linux-deb: $(DEPLOYMENT_DIR)$(PATH_SEP)$(JAR_DIR)$(PATH_SEP)$(JAR_FILE)
	$(CREATE_DEPLOYMENTDIR)
	jpackage --app-version $(VERSION) \
	--description "Educational simulation program, to explore the world of agent-based modeling" \
	--icon "$(IMAGES_DIR)$(PATH_SEP)sheepsmeadow32x32.png" \
	--name $(APP_NAME) \
	--input . \
	--main-jar $(DEPLOYMENT_DIR)/jar/$(JAR_FILE) \
	--main-class $(MAIN_CLASS) \
	--type $(JPACKAGE_TYPE) \
	--dest $(DEPLOYMENT_DIR)/linux-deb/

deploy-macOS: $(DEPLOYMENT_DIR)$(PATH_SEP)$(JAR_DIR)$(PATH_SEP)$(JAR_FILE)
	$(CREATE_DEPLOYMENTDIR)
	jpackage --app-version $(VERSION) \
	--description "Educational simulation program, to explore the world of agent-based modeling" \
	--icon "$(IMAGES_DIR)$(PATH_SEP)sheepsmeadow128x128.icns" \
	--name $(APP_NAME) \
	--input . \
	--main-jar $(DEPLOYMENT_DIR)/jar/$(JAR_FILE) \
	--main-class $(MAIN_CLASS) \
	--type $(JPACKAGE_TYPE) \
	--dest $(DEPLOYMENT_DIR)/macOS/

# ensure that those directories exist
$(BIN_DIR) $(BUILD_DIR) $(DEPLOYMENT_DIR) $(JAR_DIR) $(IMAGES_DIR):
	mkdir $@

# Clean build artifacts
clean: $(BIN_DIR) $(BUILD_DIR) $(DEPLOYMENT_DIR) 
	$(RM) \
	$(BIN_DIR) \
	$(BUILD_DIR) \
	$(DEPLOYMENT_DIR)

.PHONY: all compile run compile-test test deploy-windows deploy-linux-deb deploy-macOS clean
