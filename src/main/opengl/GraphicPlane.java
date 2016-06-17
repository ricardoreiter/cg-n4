package main.opengl;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

public class GraphicPlane extends GraphicObject {

	private float size;
	
	public GraphicPlane(float size, float[] color) {
		this.size = size;
		this.materialColor = color;
	}
	
	@Override
	public void innerDraw(final GL gl, final GLUT glut) {
		gl.glBegin(GL.GL_QUADS);
		
		gl.glNormal3f(0,1,0);
		gl.glVertex3f(-size,  0f, -size);
		gl.glVertex3f(-size,  0f,  size);
		gl.glVertex3f( size,  0f,  size);
		gl.glVertex3f( size,  0f, -size);
		
		gl.glEnd();
	}

	@Override
	public void setSize(float size) {
		this.size = size;
	}

}
