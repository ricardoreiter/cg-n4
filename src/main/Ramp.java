package main;

import javax.vecmath.Vector3f;

import com.bulletphysics.collision.shapes.BoxShape;

import main.opengl.GraphicRamp;

public class Ramp extends WorldObject {

	public Ramp(float size, float[] color, float mass, Vector3f initPos) {
		super(new GraphicRamp(size, color), new BoxShape(new Vector3f(size/2, 0.25f, size/2)), initPos);
		setMass(mass);
	}
	
	public Ramp(float size, float[] color, Vector3f initPos) {
		super(new GraphicRamp(size, color), initPos);
	}

	@Override
	public void update(float deltaTime) {
	}

}
