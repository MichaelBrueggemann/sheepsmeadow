# How to run a simulation?

1. Change to this projects root directory `sheepsmeadow`.
2. Compile the java source files with `javac -d bin -sourcepath src -cp ".:libs/*" src/Model.java`
3. Run the simulation with `java -cp "bin:libs/*" Model <MASON flags>?`
    - with `<MASON flags>` you can pass options for the simulation, see the [manual](manual.pdf).

