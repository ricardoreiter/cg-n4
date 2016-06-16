package main.physics;

import javax.vecmath.Vector3d;

public class Ray {

	private Vector3d origin;
	private Vector3d direction;
	
	public Ray(Vector3d origin, Vector3d direction) {
		super();
		this.origin = origin;
		this.direction = direction;
	}
	
	public Vector3d getOrigin() {
		return origin;
	}
	
	public void setOrigin(Vector3d origin) {
		this.origin = origin;
	}
	
	public Vector3d getDirection() {
		return direction;
	}
	
	public void setDirection(Vector3d direction) {
		this.direction = direction;
	}
	
	@Override
	public String toString() {
		return String.format("[origin=%s, direction=%s]", origin, direction);
	}
	
}
