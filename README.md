# Falling Sand Simulation

### Project Overview
This project is a Falling Sand Simulation implemented in Java. The simulation allows users to interact with a simple physics-based sandbox environment where user input can spawn in spawn particles and watch them fall.

##### Features
- Mouse Interaction: Users can draw and place sand particles in the environment by clicking or dragging the mouse.
- Gravity Simulation: Sand particles fall and stack naturally.
- Efficient BitSet Implementation: The sand particles are managed using a BitSet data structure to optimize performance and memory usage.
- Grid-Based Logic: The sand simulation operates on a grid, with each particle’s movement computed based on its neighboring cells.

##### How It Works
- Initialization: A grid is created, where each cell can either be empty or contain a sand particle.
- User Interaction: The user can add sand particles to the grid by clicking on the window. The mouse coordinates are mapped to the grid.
- Particle Simulation: Sand particles move downward if the space below is empty. If they encounter obstacles (such as other sand particles), they move diagonally or come to rest.
