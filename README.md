# Falling Sand Simulation

### Project Overview
This project is a Falling Sand Simulation implemented in Java. The simulation allows users to interact with a simple physics-based sandbox environment where input can spawn in spawn particles and watch them fall.

##### Features
- Mouse Interaction: Users can draw and place sand particles in the environment by clicking or dragging the mouse.
- Gravity Simulation: Sand particles fall and stack naturally.
- Efficient BitSet Implementation: The sand particles are managed using a BitSet data structure to optimize performance and memory usage.
- Grid-Based Logic: The sand simulation operates on a grid, with each particle’s movement computed based on its neighboring cells.

##### How It Works
- Initialization: A grid is created, with each cell either empty or containing a sand particle.
- User Interaction: The user can add sand particles to the grid by clicking on the window. The mouse coordinates are mapped to the grid.
- Particle Simulation: Sand particles move downward if the space below is empty. If they encounter obstacles (such as other sand particles), they move diagonally or come to rest.

#### Future Plans
- Implementing UI: A UI that allows the user to choose many things, such as sand color, sand speed, FPS, sand extent size, etc.
- Optimizing: Currently, the sand is on a grid, and the program loops through the entire grid every frame to refresh it. To make it as efficient as possible, I want to implement it only by refreshing the sand that moves. This could include using flags for every grain if it is done moving, threading the sand calculations separately, or revamping the graphics system entirely.
