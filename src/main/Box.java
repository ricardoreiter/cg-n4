package main;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

public class Box extends GraphicObject {

	private float size;
	private float[] color;
	
	public Box(float size, float[] color) {
		this.size = size;
		this.color = color;
	}
	
	@Override
	public void innerDraw(final GL gl, final GLUT glut) {
		gl.glColor3f(color[0], color[1], color[2]);
		glut.glutSolidCube(size);
	}

}
