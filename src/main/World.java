package main;

import java.util.LinkedList;
import java.util.List;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

/**
 * Mundo que agrupa objetos gr�ficos.
 */
public class World implements Drawable {

	private final Camera camera = new Camera();
	private final List<GraphicObject> objects = new LinkedList<>();

	/**
	 * Obt�m a camera associada ao mundo.
	 * 
	 * @return {@link Camera} do mundo.
	 */
	public Camera getCamera() {
		return camera;
	}

	/**
	 * Adiciona um novo objeto gr�fico ao mundo.
	 * 
	 * @param graphicObject
	 *            Objeto a ser adicionado.
	 */
	public void add(GraphicObject graphicObject) {
		objects.add(graphicObject);
	}

	/**
	 * Remove um objeto gr�fico do mundo.
	 * 
	 * @param graphicObject
	 *            objeto a ser removido.
	 */
	public void remove(GraphicObject graphicObject) {
		objects.remove(graphicObject);
	}

	@Override
	public void draw(GL gl, GLUT glut) {
		objects.forEach(o -> o.draw(gl, glut));
	}

}
