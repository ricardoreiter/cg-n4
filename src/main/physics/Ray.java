package main.physics;

import javax.vecmath.Vector3f;

public class Ray {

	private Vector3f origin;
	private Vector3f direction;
	
	public Ray(Vector3f origin, Vector3f direction) {
		super();
		this.origin = origin;
		this.direction = direction;
	}
	
	public Vector3f getOrigin() {
		return origin;
	}
	
	public void setOrigin(Vector3f origin) {
		this.origin = origin;
	}
	
	public Vector3f getDirection() {
		return direction;
	}
	
	public void setDirection(Vector3f direction) {
		this.direction = direction;
	}
	
	@Override
	public String toString() {
		return String.format("[origin=%s, direction=%s]", origin, direction);
	}
	
}
