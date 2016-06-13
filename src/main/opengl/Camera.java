package main.opengl;

import javax.vecmath.Vector3d;

public class Camera {

	private Vector3d eye;
	private Vector3d center;
	private Vector3d up;
	
	public Camera(Vector3d eye, Vector3d center, Vector3d up) {
		this.eye = eye;
		this.center = center;
		this.up = up;
	}

	public Vector3d getEye() {
		return eye;
	}

	public void setEye(Vector3d eye) {
		this.eye = eye;
	}

	public Vector3d getCenter() {
		return center;
	}

	public void setCenter(Vector3d center) {
		this.center = center;
	}

	public Vector3d getUp() {
		return up;
	}

	public void setUp(Vector3d up) {
		this.up = up;
	}
	
}
