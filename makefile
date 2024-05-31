
all:
	'javac' -d bin -sourcepath src -cp "src:.:libs/*" src/Controller/ModelWithUI.java
	'java' -cp "bin:libs/*" Controller.ModelWithUI
