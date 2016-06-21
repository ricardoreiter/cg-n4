package main.opengl;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

public class GraphicRamp extends GraphicObject {

	private float size;
	
	public GraphicRamp(float size, float[] color) {
		this.size = size;
		this.materialColor = color;
	}
	
	@Override
	public void innerDraw(final GL gl, final GLUT glut) {
		double halfSize = size/2;
		gl.glBegin(GL.GL_QUADS);
		gl.glNormal3d(0, 0, 1);
		gl.glVertex3d(  halfSize, -0.25, halfSize );
		gl.glVertex3d(  halfSize,  0.25, halfSize );
		gl.glVertex3d( -halfSize,  0.25, halfSize );
		gl.glVertex3d( -halfSize, -0.25, halfSize );
		gl.glEnd();
		
		gl.glBegin(GL.GL_QUADS);
		gl.glNormal3d(0, 0, -1);
		gl.glVertex3d(  -halfSize, -0.25, -halfSize );
		gl.glVertex3d(  -halfSize,  0.25, -halfSize );
		gl.glVertex3d( halfSize,  0.25, -halfSize );
		gl.glVertex3d( halfSize, -0.25, -halfSize );
		gl.glEnd();
		 
		// Lado roxo - DIREITA
		gl.glBegin(GL.GL_QUADS);
		gl.glNormal3d(1, 0, 0);
		gl.glVertex3d( halfSize, -0.25, -halfSize );
		gl.glVertex3d( halfSize,  0.25, -halfSize );
		gl.glVertex3d( halfSize,  0.25,  halfSize );
		gl.glVertex3d( halfSize, -0.25,  halfSize );
		gl.glEnd();
		 
		// Lado verde - ESQUERDA
		gl.glBegin(GL.GL_QUADS);
		gl.glNormal3d(-1, 0, 0);
		gl.glVertex3d( -halfSize, -0.25,  halfSize );
		gl.glVertex3d( -halfSize,  0.25,  halfSize );
		gl.glVertex3d( -halfSize,  0.25, -halfSize );
		gl.glVertex3d( -halfSize, -0.25, -halfSize );
		gl.glEnd();
		 
		// Lado azul - TOPO
		gl.glBegin(GL.GL_QUADS);
		gl.glNormal3d(0, 1, 0);
		gl.glVertex3d(  halfSize,  0.25,  halfSize );
		gl.glVertex3d(  halfSize,  0.25, -halfSize );
		gl.glVertex3d( -halfSize,  0.25, -halfSize );
		gl.glVertex3d( -halfSize,  0.25,  halfSize );
		gl.glEnd();
		 
		// Lado vermelho - BASE
		gl.glBegin(GL.GL_QUADS);
		gl.glNormal3d(0, -1, 0);
		gl.glVertex3d(  halfSize, -0.25, -halfSize );
		gl.glVertex3d(  halfSize, -0.25,  halfSize );
		gl.glVertex3d( -halfSize, -0.25,  halfSize );
		gl.glVertex3d( -halfSize, -0.25, -halfSize );
		gl.glEnd();
		
		gl.glFlush();
	}

	@Override
	public void setSize(float size) {
		this.size = size;
	}

}
