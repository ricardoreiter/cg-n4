package main.opengl;

import javax.media.opengl.GL;
import javax.vecmath.Vector3f;

import com.sun.opengl.util.GLUT;

public class GraphicSphere extends GraphicObject {

	private float radius;
	
	public GraphicSphere(float radius, float[] color) {
		this.radius = radius;
		this.materialColor = color;
	}
	
	@Override
	public void innerDraw(final GL gl, final GLUT glut) {
		glut.glutSolidSphere(radius, 25, 25);
	}

	@Override
	public void setSize(Vector3f size) {
		this.radius = size.x;
	}

}
