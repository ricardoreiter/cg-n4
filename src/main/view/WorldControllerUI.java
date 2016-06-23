package main.view;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import javax.vecmath.Vector3d;

import main.opengl.Drawable;

import com.sun.opengl.util.GLUT;

/**
 * Imprime informações do objeto do controller
 */
public class WorldControllerUI implements Drawable {

	private float currentObjectMass = 0;
	private Vector3d currentInfoPos = new Vector3d();
	
	
	
	public void setCurrentInfoPos(Vector3d currentInfoPos) {
		this.currentInfoPos = currentInfoPos;
	}

	public void setCurrentObjectMass(float currentObjectMass) {
		this.currentObjectMass = currentObjectMass;
	}

	@Override
	public void draw(GL gl, final GLU glu, GLUT glut) {
		gl.glRasterPos3d(currentInfoPos.x, currentInfoPos.y, currentInfoPos.z);
//		glu.gluOrtho2D(-400, -400, 400, 400);
//		gl.glMatrixMode(GL.GL_MODELVIEW);
		glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, String.format("Massa: %s", currentObjectMass));
	}

	@Override
	public void initDraw(GLAutoDrawable drawable, GL gl) {
		
	}

	
	
}
