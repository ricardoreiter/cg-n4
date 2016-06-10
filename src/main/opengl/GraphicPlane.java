package main.opengl;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

public class GraphicPlane extends GraphicObject {

	public GraphicPlane(float[] color) {
		this.materialColor = color;
	}
	
	@Override
	public void innerDraw(final GL gl, final GLUT glut) {
		gl.glBegin(GL.GL_QUADS);
		
		gl.glNormal3f(0,1,0);
		gl.glVertex3f(-30.0f,  0f, -30.0f);
		gl.glVertex3f(-30.0f,  0f,  30.0f);
		gl.glVertex3f( 30.0f,  0f,  30.0f);
		gl.glVertex3f( 30.0f,  0f, -30.0f);
		
		gl.glEnd();
	}

}
