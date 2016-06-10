package main.opengl;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import com.sun.opengl.util.GLUT;

/**
 * Essa interface indica que qualquer classe que à implemente possui um objeto
 * que pode ser desenhado no {@link GL}.
 */
public interface Drawable {

	/**
	 * Desenha o objeto no {@link GL}.
	 * 
	 * @param gl
	 *            {@link GL} a receber o desenho.
	 */
	public void draw(final GL gl, final GLUT glut);

	public void initDraw(GLAutoDrawable drawable, GL gl);
}
