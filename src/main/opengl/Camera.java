package main.opengl;

import javax.vecmath.Matrix3f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import com.bulletphysics.linearmath.MatrixUtil;
import com.bulletphysics.linearmath.Transform;

public class Camera {

	private Vector3f eye;
	private Vector3f center;
	private Vector3f up;
	private Transform transform;
	
	public Camera(Vector3f eye, Vector3f center, Vector3f up) {
		this.eye = eye;
		this.center = center;
		this.up = up;
		this.transform = new Transform();
		this.transform.setIdentity();
	}
	
	public void translate(Vector3f translate) {
		Matrix4f out = new Matrix4f();
		transform.getMatrix(out);
		Vector3f oldTranslate = new Vector3f();
		out.get(oldTranslate);
		translate.add(oldTranslate);
		out.setTranslation(translate);
		transform.set(out);
	}
	
	public void rotate(float angleX, float angleY) {
		Matrix4f out = new Matrix4f();
		transform.getMatrix(out);
	
		Vector3f oldTranslate = new Vector3f();
		out.get(oldTranslate);

		Matrix3f rotation = new Matrix3f();
		MatrixUtil.setEulerZYX(rotation, (float) Math.toRadians(angleX), (float) Math.toRadians(angleY), 0);
		oldTranslate.negate();
		out.setTranslation(oldTranslate);
		out.set(rotation);
		oldTranslate.negate();
		out.setTranslation(oldTranslate);
	
		transform.set(out);
	}

	public Vector3f getEye() {
		Vector3f out = new Vector3f(eye);
		transform.transform(out);
		return out;
	}

	public void setEye(Vector3f eye) {
		this.eye = eye;
	}

	public Vector3f getCenter() {
		Vector3f out = new Vector3f(center);
		transform.transform(out);
		return out;
	}

	public void setCenter(Vector3f center) {
		this.center = center;
	}

	public Vector3f getUp() {
		return up;
	}

	public void setUp(Vector3f up) {
		this.up = up;
	}
	
}
