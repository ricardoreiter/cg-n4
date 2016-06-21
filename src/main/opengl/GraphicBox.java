package main.opengl;

import javax.media.opengl.GL;
import javax.vecmath.Vector3f;

import com.sun.opengl.util.GLUT;

public class GraphicBox extends GraphicObject {

	private Vector3f size;
	
	public GraphicBox(Vector3f size, float[] color) {
		this.size = (Vector3f) size.clone();
		this.materialColor = color;
	}
	
	@Override
	public void innerDraw(final GL gl, final GLUT glut) {
		double halfXSize = size.x/2;
		double halfYSize = size.y/2;
		double halfZSize = size.z/2;
		
		gl.glBegin(GL.GL_QUADS);
		gl.glNormal3d(0, 0, 1);
		gl.glVertex3d(  halfXSize, -halfYSize, halfZSize );
		gl.glVertex3d(  halfXSize,  halfYSize, halfZSize );
		gl.glVertex3d( -halfXSize,  halfYSize, halfZSize );
		gl.glVertex3d( -halfXSize, -halfYSize, halfZSize );
		gl.glEnd();
		
		gl.glBegin(GL.GL_QUADS);
		gl.glNormal3d(0, 0, -1);
		gl.glVertex3d(  -halfXSize, -halfYSize, -halfZSize );
		gl.glVertex3d(  -halfXSize,  halfYSize, -halfZSize );
		gl.glVertex3d( halfXSize,  halfYSize, -halfZSize );
		gl.glVertex3d( halfXSize, -halfYSize, -halfZSize );
		gl.glEnd();
		 
		// Lado roxo - DIREITA
		gl.glBegin(GL.GL_QUADS);
		gl.glNormal3d(1, 0, 0);
		gl.glVertex3d( halfXSize, -halfYSize, -halfZSize );
		gl.glVertex3d( halfXSize,  halfYSize, -halfZSize );
		gl.glVertex3d( halfXSize,  halfYSize,  halfZSize );
		gl.glVertex3d( halfXSize, -halfYSize,  halfZSize );
		gl.glEnd();
		 
		// Lado verde - ESQUERDA
		gl.glBegin(GL.GL_QUADS);
		gl.glNormal3d(-1, 0, 0);
		gl.glVertex3d( -halfXSize, -halfYSize,  halfZSize );
		gl.glVertex3d( -halfXSize,  halfYSize,  halfZSize );
		gl.glVertex3d( -halfXSize,  halfYSize, -halfZSize );
		gl.glVertex3d( -halfXSize, -halfYSize, -halfZSize );
		gl.glEnd();
		 
		// Lado azul - TOPO
		gl.glBegin(GL.GL_QUADS);
		gl.glNormal3d(0, 1, 0);
		gl.glVertex3d(  halfXSize,  halfYSize,  halfZSize );
		gl.glVertex3d(  halfXSize,  halfYSize, -halfZSize );
		gl.glVertex3d( -halfXSize,  halfYSize, -halfZSize );
		gl.glVertex3d( -halfXSize,  halfYSize,  halfZSize );
		gl.glEnd();
		 
		// Lado vermelho - BASE
		gl.glBegin(GL.GL_QUADS);
		gl.glNormal3d(0, -1, 0);
		gl.glVertex3d(  halfXSize, -halfYSize, -halfZSize );
		gl.glVertex3d(  halfXSize, -halfYSize,  halfZSize );
		gl.glVertex3d( -halfXSize, -halfYSize,  halfZSize );
		gl.glVertex3d( -halfXSize, -halfYSize, -halfZSize );
		gl.glEnd();
		
		gl.glFlush();
	}

	@Override
	public void setSize(Vector3f size) {
		this.size = (Vector3f) size.clone();
	}

}
