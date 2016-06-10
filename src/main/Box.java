package main;

import javax.vecmath.Vector3f;

import com.bulletphysics.collision.shapes.BoxShape;

import main.opengl.GraphicBox;

public class Box extends WorldObject {

	public Box(float size, float[] color, float mass, Vector3f initPos) {
		super(new GraphicBox(size, color), new BoxShape(new Vector3f(size/2, size/2, size/2)), initPos);
		setMass(mass);
	}

	@Override
	public void update(float deltaTime) {
	}
	
}
