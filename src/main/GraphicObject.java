package main;

import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

public abstract class GraphicObject implements Drawable {

	private final List<GraphicObject> objects = new LinkedList<>();
	private float[] scale = new float[] {1.0f, 1.0f, 1.0f};
	private float[] translate = new float[] {0.0f, 0.0f, 0.0f};

	public void addGraphicObject(final GraphicObject object) {
		objects.add(object);
	}

	public List<GraphicObject> getGrapicObjects() {
		return objects;
	}
	
	public abstract void innerDraw(final GL gl, final GLUT glut);

	@Override
	public void draw(final GL gl, final GLUT glut) {
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, new float[] {0.0f, 1.0f, 1.0f, 1.0f}, 0);
		gl.glEnable(GL.GL_LIGHTING);
		gl.glPushMatrix();

		gl.glScalef(scale[0],scale[1],scale[2]);
		gl.glTranslated(translate[0], translate[1], translate[2]);
		innerDraw(gl, glut);

		objects.forEach(o -> o.draw(gl, glut));

		gl.glPopMatrix();
		gl.glDisable(GL.GL_LIGHTING);
	}

	public void translate(float x, float y, float z) {
		translate[0] += x;
		translate[1] += y;
		translate[2] += z;
	}

	public void scale(float x, float y, float z) {
		scale[0] += x;
		scale[1] += y;
		scale[2] += z;
	}

}
