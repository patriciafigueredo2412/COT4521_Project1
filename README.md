# Computational Geometry — Project 1

A modular Java application and geometric library developed for Computational Geometry, focusing on 2D vector mathematics, coordinate transformations, custom geometric primitives, and real-time Swing/AWT canvas rendering.

---

## 📌 Overview

This project implements core computational geometry algorithms and primitive geometric models from the ground up without relying on high-level external geometry engines. It features custom vector operations, rigid body transformations (translation and rotation around arbitrary pivots), and hierarchical component assembly rendered via a custom 2D coordinate graph framework.

---

## 🚀 Key Features & Mathematical Foundations

* **Vector & Metric Mathematics:**
  * Custom 2D vector algebra ($x, y$ components, magnitude, normalization).
  * Dot product ($\vec{u} \cdot \vec{v}$) and cross product ($\vec{u} \times \vec{v}$) calculations.
  * Angular classification (acute, right, obtuse) and directional orientation testing.
  * Floating-point tolerance handling via calibrated $\epsilon$ thresholds.
* **Geometric Primitives & Models:**
  * Object hierarchy rooted in a common abstract base class (`GeometricObject`).
  * Vertex-explicit implementations of `Point`, `Line`, `Triangle`, and `Rectangle` (defined by explicit vertex coordinates).
  * Composite multi-primitive models (e.g., modular `Car` assembly combining chassis, cabin, and wheels).
* **Affine Transformations:**
  * **Translation:** Rigid displacement along direction vectors $(\Delta x, \Delta y)$.
  * **Rotation:** Euclidean plane rotation of vertices around arbitrary local center points / centroids using trigonometric rotation matrices:
    $$x' = x_c + (x - x_c)\cos\theta - (y - y_c)\sin\theta$$
    $$y' = y_c + (x - x_c)\sin\theta + (y - y_c)\cos\theta$$
* **Interactive GUI Canvas:**
  * Custom rendering engine (`Graph`, `FrameDisplay`) mapping geometric Cartesian space to screen/pixel coordinates.

---

## 📂 Project Architecture

```text
src/
├── com/geometry/
│   ├── core/                  # Core primitives (Point, Line, Vector)
│   ├── model/                 # Geometric shapes & composites (Rectangle, Triangle, Car)
│   ├── transform/             # Translation and rotation utilities
│   └── ui/                    # Swing display frame and canvas rendering (Graph, Frame)
└── Main.java                  # Application driver and demo execution
