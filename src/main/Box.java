package main;

import javax.vecmath.Vector3f;

import com.bulletphysics.collision.shapes.BoxShape;

import main.opengl.GraphicBox;

public class Box extends WorldObject {

	public Box(float size, float[] color) {
		super(new GraphicBox(size, color), new BoxShape(new Vector3f(size, size, size)));
		Vector3f localInertia = new Vector3f();
		collisionShape.calculateLocalInertia(1, localInertia);
		getRigidBody().setMassProps(1, localInertia);
	}
	
}
