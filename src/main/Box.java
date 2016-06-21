package main;

import javax.vecmath.Vector3f;

import com.bulletphysics.collision.shapes.BoxShape;

import main.opengl.GraphicBox;

public class Box extends WorldObject {

	public Box(Vector3f size, float[] color, float mass, Vector3f initPos) {
		super(new GraphicBox(size, color), new BoxShape(new Vector3f(size.x/2, size.y/2, size.z/2)), initPos);
		setMass(mass);
	}
	
	public Box(Vector3f size, float[] color, Vector3f initPos) {
		super(new GraphicBox(size, color), initPos);
	}

	@Override
	public void update(float deltaTime) {
	}

}
