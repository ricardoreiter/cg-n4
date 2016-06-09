package main.opengl;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

public class GraphicBox extends GraphicObject {

	private float size;
	
	public GraphicBox(float size, float[] color) {
		this.size = size;
		this.materialColor = color;
	}
	
	@Override
	public void innerDraw(final GL gl, final GLUT glut) {
		glut.glutSolidCube(size);
	}

}
