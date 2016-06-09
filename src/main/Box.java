package main;

import javax.vecmath.Vector3f;

import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.linearmath.Transform;

import main.opengl.GraphicBox;

public class Box extends WorldObject {

	public Box(float size, float[] color, float mass, Vector3f initPos) {
		super(new GraphicBox(size, color), new BoxShape(new Vector3f(size, size, size)), initPos);
		setMass(mass);
	}

	@Override
	public void update(float deltaTime) {
		Transform trans = new Transform();
		getRigidBody().getMotionState().getWorldTransform(trans);
	}
	
}
