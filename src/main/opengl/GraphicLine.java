package main.opengl;

import javax.media.opengl.GL;
import javax.vecmath.Vector3f;

import com.sun.opengl.util.GLUT;

public class GraphicLine extends GraphicObject {

	private float size;
	
	public GraphicLine(float size, float[] color) {
		this.size = size;
		this.materialColor = color;
	}
	
	public float getSize() {
		return size;
	}

	public void setSize(Vector3f size) {
		this.size = size.x;
	}

	@Override
	public void innerDraw(final GL gl, final GLUT glut) {
		gl.glColor4f(materialColor[0], materialColor[1], materialColor[2], 1.0f);

		gl.glBegin(GL.GL_LINES);
		gl.glVertex3f(0, 0, 0);
		gl.glVertex3f(0, size, 0);
			
		gl.glEnd();
	}

}
