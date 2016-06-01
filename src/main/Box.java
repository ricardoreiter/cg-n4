package main;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

public class Box extends GraphicObject {

	@Override
	public void innerDraw(final GL gl, final GLUT glut) {
		glut.glutSolidCube(1.0f);
	}

}
