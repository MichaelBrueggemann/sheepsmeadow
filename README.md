# How to run a simulation?

1. Change to this projects root directory `sheepsmeadow/`.
2. Compile the java source files with `javac -d bin -sourcepath src -cp ".:libs/*" src/Model.java`
3. Run the simulation with `java -cp "bin:libs/*" Model <MASON flags>?`
    - with `<MASON flags>` you can pass options for the simulation, see the [manual](manual.pdf).

# Reflections on this project
- MASON extensively used the MVC paradigm (Model-View-Controller)
    - a **Model** is defined e.g. as a `SimState` instance
    - a **View** is a specific Visualization (2D, 3D, etc.)
    - a **Controller** is a GUI, TUI, CLI to interact with the Model