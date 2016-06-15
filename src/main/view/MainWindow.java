package main.view;

import java.awt.BorderLayout;

import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import main.World;
import main.controller.CameraController;
import main.controller.LoopController;
import main.controller.Updatable;
import main.controller.WorldController;

public class MainWindow extends JFrame implements Updatable {

	public static GLCanvas canvas;
	private static final long serialVersionUID = 1L;
	public static final MainWindow mainWindow = new MainWindow();
	
	private final Render render = new Render();
	private final World world = new World(render);
	private final WorldController controller = new WorldController(world);
	private final CameraController cameraController = new CameraController(render.getCamera(), world);
	private final LoopController loopController = new LoopController();

	public MainWindow() {
		super("CG-N3");
		setBounds(100, 100, 800, 822); // 400 + 22 da borda do titulo da janela
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());

		/*
		 * Cria um objeto GLCapabilities para especificar o numero de bits por
		 * pixel para RGBA
		 */
		GLCapabilities glCaps = new GLCapabilities();
		glCaps.setRedBits(8);
		glCaps.setBlueBits(8);
		glCaps.setGreenBits(8);
		glCaps.setAlphaBits(8);

		/*
		 * Cria um canvas, adiciona ao frame e objeto "ouvinte" para os eventos
		 * Gl, de mouse e teclado
		 */
		canvas = new GLCanvas(glCaps);
		add(canvas, BorderLayout.CENTER);
		canvas.addGLEventListener(render);
		canvas.addKeyListener(controller);
		canvas.addMouseListener(controller);
		canvas.addMouseMotionListener(controller);
		
		canvas.addKeyListener(cameraController);
		canvas.addMouseListener(cameraController);
		canvas.addMouseMotionListener(cameraController);
		canvas.requestFocus();
		
		Thread loopThread = new Thread(loopController);
		loopController.addUpdatable(world);
		loopController.addUpdatable(cameraController);
		loopController.addUpdatable(this);
		loopThread.start();
	}

	public static void main(String[] args) {
		mainWindow.setVisible(true);
	}

	@Override
	public void update(float deltaTime) {
		mainWindow.setTitle(String.format("FPS: %s", (int) Math.abs(loopController.getFrames())));
	}
}
