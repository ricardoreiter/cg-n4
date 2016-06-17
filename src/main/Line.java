package main;

import javax.vecmath.Vector3f;

import main.opengl.GraphicLine;

public class Line extends WorldObject {

	public Line(float size, float[] color, Vector3f initPos) {
		super(new GraphicLine(size, color), initPos);
	}

	@Override
	public void update(float deltaTime) {
	}

	public void setSize(float size) {
		((GraphicLine) graphicObject).setSize(size);
	}
	
}
