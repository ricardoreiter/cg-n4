package main.view;

import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.vecmath.Vector3d;

import com.sun.opengl.util.GLUT;

import main.opengl.Camera;
import main.opengl.Drawable;

public class Render implements GLEventListener {

	private final List<Drawable> drawings = new LinkedList<>();

	private GL gl;
	private GLU glu;
	private GLUT glut;
	private GLAutoDrawable glDrawable;
	
	private final Camera camera = new Camera(new Vector3d(50, 10, 50), new Vector3d(0, 10, 0), new Vector3d(0, 1, 0));

	public Camera getCamera() {
		return camera;
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glut = new GLUT();
		glDrawable.setGL(new DebugGL(gl));

		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		turnOnLight();
		
	    gl.glEnable(GL.GL_CULL_FACE);
		
	    gl.glEnable(GL.GL_DEPTH_TEST);
	    
	    for (Drawable d : drawings) {
			d.initDraw(drawable, gl);
		}
	}
	
	private void turnOnLight() {
		float posLight[] = { 5.0f, 5.0f, 10.0f, 0.0f };
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, posLight, 0);
		gl.glEnable(GL.GL_LIGHT0);
	}

	@Override
	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
		gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
		glu.gluLookAt(camera.getEye().getX(), camera.getEye().getY(), camera.getEye().getZ(), // 
					  camera.getCenter().getX(), camera.getCenter().getY(), camera.getCenter().getZ(), // 
					  camera.getUp().getX(), camera.getUp().getY(), camera.getUp().getZ());

		SRU(gl);
		for (Drawable d : drawings) {
			d.draw(gl, glut);
		}
		gl.glFlush();
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glViewport(0, 0, width, height);
		
	    glu.gluPerspective(60, width/height, 0.1, 1000);				// projecao Perpectiva 1 pto fuga 3D    
	}

	@Override
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
	}

	public void addDrawable(final Drawable drawable) {
		if (drawable != null) {
			drawings.add(drawable);
		}
	}

	/**
	 * Desenha os eixos X e Y.
	 * 
	 * Ambos com tamanho -200 e 200.
	 */
	public void SRU(GL gl) {
		// eixo x
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
			gl.glVertex3f(0.0f, 0.0f, 0.0f);
			gl.glVertex3f(10.0f, 0.0f, 0.0f);
		gl.glEnd();
		// eixo Y - Green
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
			gl.glVertex3f(0.0f, 0.0f, 0.0f);
			gl.glVertex3f(0.0f, 10.0f, 0.0f);
		gl.glEnd();
		// eixo Z - Blue
		gl.glColor3f(0.0f, 0.0f, 1.0f);
		gl.glBegin(GL.GL_LINES);
			gl.glVertex3f(0.0f, 0.0f, 0.0f);
			gl.glVertex3f(0.0f, 0.0f, 10.0f);
		gl.glEnd();
	}

	public void render() {
		if (glDrawable == null) {
			return;
		}
		glDrawable.display();
	}

}
