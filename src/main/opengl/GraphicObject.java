package main.opengl;

import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

public abstract class GraphicObject implements Drawable {

	private final List<GraphicObject> objects = new LinkedList<>();
	private float[] transformMatrix = new float[16];
	protected float[] materialColor;

	public GraphicObject() {
		setIdentity();
	}
	
	public void addGraphicObject(final GraphicObject object) {
		objects.add(object);
	}

	public List<GraphicObject> getGrapicObjects() {
		return objects;
	}
	
	public void setIdentity() {
		for (int i=0; i<16; ++i) {
			transformMatrix[i] = 0.0f;
		}
		transformMatrix[0] = transformMatrix[5] = transformMatrix[10] = transformMatrix[15] = 1.0f;
	}
	
	public abstract void innerDraw(final GL gl, final GLUT glut);

	@Override
	public void draw(final GL gl, final GLUT glut) {
		System.out.println(String.format("Graphic: %s - Render", this));
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, materialColor, 0);
		gl.glEnable(GL.GL_LIGHTING);
		gl.glPushMatrix();

		gl.glMultMatrixd(convertMatrix(), 0);
		innerDraw(gl, glut);

		objects.forEach(o -> o.draw(gl, glut));

		gl.glPopMatrix();
		gl.glDisable(GL.GL_LIGHTING);
	}

	public double[] convertMatrix() {
		double[] result = new double[transformMatrix.length];
		for (int i = 0; i < transformMatrix.length; i++) {
			result[i] = transformMatrix[i];
		}
		return result;
	}
	
	public float[] getMatrix() {
		return transformMatrix;
	}

}
