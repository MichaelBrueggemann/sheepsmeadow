# compiles all test files and runs them
find tests -name '*.java' -print0 | xargs -0 javac -cp "src:bin:libs/*" -d bin
java --enable-preview -cp bin:libs/* org.junit.runner.JUnitCore $(find bin -name "*Test.class" -type f | sed 's@^bin/\(.*\)\.class$@\1@' | sed 's@/@.@g')

