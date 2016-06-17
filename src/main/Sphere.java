package main;

import javax.vecmath.Vector3f;

import com.bulletphysics.collision.shapes.SphereShape;

import main.opengl.GraphicSphere;

public class Sphere extends WorldObject {

	public Sphere(float radius, float[] color, float mass, Vector3f initPos) {
		super(new GraphicSphere(radius, color), new SphereShape(radius), initPos);
		setMass(mass);
	}
	
	public Sphere(float size, float[] color, Vector3f initPos) {
		super(new GraphicSphere(size, color), initPos);
	}

	@Override
	public void update(float deltaTime) {
	}

}
