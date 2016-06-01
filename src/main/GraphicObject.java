package main;

import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

public abstract class GraphicObject implements Drawable {

	private final List<GraphicObject> objects = new LinkedList<>();

	public void addGraphicObject(final GraphicObject object) {
		objects.add(object);
	}

	public List<GraphicObject> getGrapicObjects() {
		return objects;
	}
	
	public abstract void innerDraw(final GL gl, final GLUT glut);

	@Override
	public void draw(final GL gl, final GLUT glut) {
		gl.glPushMatrix();

		innerDraw(gl, glut);

		objects.forEach(o -> o.draw(gl, glut));

		gl.glPopMatrix();
	}

	public void translate(int x, int y) {
	}

	public void rotateZ(double radians) {
	}

	public void scaleXY(double scale) {
	}

}
