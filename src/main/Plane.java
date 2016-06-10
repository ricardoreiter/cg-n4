package main;

import javax.vecmath.Vector3f;

import com.bulletphysics.collision.shapes.StaticPlaneShape;

import main.opengl.GraphicPlane;

public class Plane extends WorldObject {

	public Plane(float[] color, Vector3f initPos) {
		super(new GraphicPlane(color), new StaticPlaneShape(new Vector3f(0, 1, 0), 1), initPos);
	}

	@Override
	public void update(float deltaTime) {
	}
	
}
