# How to run a simulation?

1. Change to this projects root directory `sheepsmeadow/`.
2. Compile the java source files with `javac -d bin -sourcepath src -cp ".:libs/*" <Sourcefile>`
    - execute this command for each `<Sourcefile>` seperately (it's possible to do it in one line)

## In the Terminal
- Run the simulation with `java -cp "bin:libs/*" Model.Model <MASON flags>?`
    - with `<MASON flags>` you can pass options for the simulation, see the [manual](manual.pdf).

## With GUI
- Run the simulation with `java -cp "bin:libs/*" Controller.ModelWithUI`

# Reflections on this project
- MASON extensively used the MVC paradigm (Model-View-Controller)
    - a **Model** is defined e.g. as a `SimState` instance
    - a **View** is a specific Visualization (2D, 3D, etc.)
    - a **Controller** is a GUI, TUI, CLI to interact with the Model

    > In this regard, all project files are also organised in the MVC pattern.