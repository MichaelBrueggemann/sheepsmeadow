# Sheepsmeadow
**Sheepsmeadow** is a simulation designed and distributed with the [MASON Framework](https://cs.gmu.edu/~eclab/projects/mason/). It fascilliates options to create a number of Sheeps and Wolves on a Meadow and observe their interaction over time. 

Purpose of this simulation is to give an introduction to "Multi-Agent-Systems" (MAS) for undergrad Students making their first steps in MAS. They can tweak the model parameters and observe the changes of the simulation state in each time steps via a GUI. Also some simple "live statistics" will be provided.

Advanced model statistics will be provided via python scripts for the sake of easier manipulation.

# How to run a simulation?

1. Change to this projects root directory `sheepsmeadow/`
2. Compile the java source files with `javac -d bin -sourcepath src -cp ".:libs/*" <Sourcefile>`
    - execute this command for each `<Sourcefile>` seperately (it's possible to do it in one line)

## In the Terminal
- Run the simulation with `java -cp "bin:libs/*" Model.Model <MASON flags>?`
    - with `<MASON flags>` you can pass options for the simulation, see the [manual](manual.pdf).

## With GUI
- Run the simulation with `java -cp "bin:libs/*" Controller.ModelWithUI`

## Run tests
Tests for this project are defined in `./tests`. Each testfile is automaticly compiled and run on every "push" of this repositoy as part of the Testing Pipeline (see [GitHub Workflow](./.github/workflows/tests.yaml)).

To run a test locally, execute: `bash test.sh`

# Structure of this project

```json
.
├── bin // Project binaries
├── libs // Project libraries
|   src // Source Files
|   ├── Controller
|   │   └── ModelWithUI.java // GUI and Control elements
|   ├── Model // Simulation logic
|   │   ├── Agents
|   │   │   ├── Agent.java // General Agent behavior
|   │   │   ├── Sheep.java
|   │   │   └── Wolve.java
|   │   └── Model.java // Main File
|   └── View
|       └── MeadowDisplay.java // Field & Agent Display
└── tests // Test files
```


# Reflections on this project
In this Section i will note some of my experiences with this project. Those notes aren't necessary to use this simulation tool, so feel free to skip the reading:

## 08.03.2024
- MASON extensively used the MVC paradigm (Model-View-Controller)
    - a **Model** is defined e.g. as a `SimState` instance
    - a **View** is a specific Visualization (2D, 3D, etc.)
    - a **Controller** is a GUI, TUI, CLI to interact with the Model

    > In this regard, all project files are also organised in the MVC pattern.
    - this was a great opportunity for me to refresh my college knowledge of the MVC pattern

## 12.03.2024
- I implemented a automated testing pipeline for the following reasons:
    1. keeping myself accountable. Any push will be checked by the pipeline, so i have to design my code in compliance with my tests or my build will fail (and i get annoying emails because of that).
    2. Learn to set up testing enviroments. Setting up this pipeline was a new experience for me, as these tests arent run on my local machine, but instead are run in a VM on GitHub. Therefore debugging was a bit challenging (i encountered the famous "but it works on my machine" a lot :) ). Using the tool `act` was a great help, to debug the VM locally.

## 11.04.2024
- In the current implementation, each agent will get a new Int2D-Object every time a location has to be changed. This is incredibly inefficient, as this pollutes memory. I decided to leave it in, as the effort needed to change this doesn't outweigh the benefit, as this project doesn't aim to provide the most performant simulation, but instead provide a simple example to learn Agent-Based-Modelling. This programm should be used as explanatory material in an first year undergrad course, so i think this is a fair consideration, as this is only a hobby project of me.