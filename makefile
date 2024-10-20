# Detect OS (Unix-based or Windows)
ifeq ($(OS),Windows_NT)
    RM = rmdir $(PATH_SEP)S $(PATH_SEP)Q
    MKDIR = if not exist $(subst $(PATH_SEP),\,$(BUILD_DIR)) mkdir $(subst $(PATH_SEP),\,$(BUILD_DIR))
    CP = copy
    CP_DIR = xcopy $(PATH_SEP)E $(PATH_SEP)I $(PATH_SEP)Y
    CLASSPATH_SEP = ;
	PATH_SEP = \
    JPACKAGE_TYPE_LINUX = exe
    JPACKAGE_TYPE_MAC = exe
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
JAR_DIR = build$(PATH_SEP)jar
LIB_DIR = libs
DEPLOYMENT_DIR = deployments
MAIN_CLASS = Controller.ModelWithUI
JAR_FILE = sheepsmeadow.jar

# Default target
# Compile Java classes
compile-source:
	$(MKDIR) $(BUILD_DIR)
	javac -d $(BUILD_DIR) -sourcepath $(SRC_DIR) -cp "src$(CLASSPATH_SEP).$(CLASSPATH_SEP)libs$(PATH_SEP)*$(CLASSPATH_SEP)images$(PATH_SEP)*" $(SRC_DIR)$(PATH_SEP)Controller$(PATH_SEP)ModelWithUI.java
	$(CP) $(SRC_DIR)$(PATH_SEP)Controller$(PATH_SEP)index.html $(BUILD_DIR)$(PATH_SEP)Controller
	$(CP_DIR) images $(BUILD_DIR)

# Compile and run the application
run: compile-source
	java -cp "$(BUILD_DIR)$(CLASSPATH_SEP)libs$(PATH_SEP)*" $(MAIN_CLASS)

compile-tests:
	find tests -name '*.java' -print0 | xargs -0 javac -cp "src$(CLASSPATH_SEP)$(BUILD_DIR)$(CLASSPATH_SEP)libs$(PATH_SEP)*" -d $(BUILD_DIR)

test: compile-tests
	java --enable-preview -cp $(BUILD_DIR)$(CLASSPATH_SEP)libs$(PATH_SEP)* org.junit.runner.JUnitCore $$(find bin -name "*Test.class" -type f | sed 's@^bin$(PATH_SEP)\(.*\)\.class$$@\1@' | sed 's@$(PATH_SEP)@.@g')

# Unzip all project dependencies
unzip-dependencies:
	$(MKDIR) $(JAR_DIR)
	for jar in $(LIB_DIR)$(PATH_SEP)*.jar; do \
		unzip -o -d $(JAR_DIR) $$jar > $(PATH_SEP)dev$(PATH_SEP)null 2>&1; \
	done

# Create the JAR file with dependencies
$(JAR_FILE): compile-source unzip-dependencies
	jar cfe $(DEPLOYMENT_DIR)$(PATH_SEP)jar$(PATH_SEP)$(JAR_FILE) $(MAIN_CLASS) -C $(JAR_DIR) . -C $(BUILD_DIR) . -C $(BUILD_DIR)$(PATH_SEP)images .

# Clean build artifacts
clean:
	$(RM) $(BUILD_DIR) $(JAR_DIR) $(JAR_FILE)

# Deploy for Linux (.deb) and macOS (.dmg or .exe for Windows)
deploy-linux-deb: $(JAR_FILE)
	$(MKDIR) .$(PATH_SEP)$(DEPLOYMENT_DIR)$(PATH_SEP)linux-deb$(PATH_SEP)
	jpackage --name Sheepsmeadow --input . --main-jar $(DEPLOYMENT_DIR)$(PATH_SEP)jar$(PATH_SEP)$(JAR_FILE) --main-class $(MAIN_CLASS) --type $(JPACKAGE_TYPE_LINUX) --dest $(DEPLOYMENT_DIR)$(PATH_SEP)linux-deb$(PATH_SEP)

deploy-macOS: $(JAR_FILE)
	$(MKDIR) .$(PATH_SEP)$(DEPLOYMENT_DIR)$(PATH_SEP)macOS$(PATH_SEP)
	jpackage --name Sheepsmeadow --input . --main-jar $(DEPLOYMENT_DIR)$(PATH_SEP)jar$(PATH_SEP)$(JAR_FILE) --main-class $(MAIN_CLASS) --type $(JPACKAGE_TYPE_MAC) --dest $(DEPLOYMENT_DIR)$(PATH_SEP)macOS$(PATH_SEP)

install-linux-deb: deploy-linux-deb
	$(MKDIR) $(PATH_SEP)tmp$(PATH_SEP)sheeps
