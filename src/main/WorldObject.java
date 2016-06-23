package main;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;
import com.sun.opengl.util.GLUT;

import main.controller.Updatable;
import main.opengl.Drawable;
import main.opengl.GraphicObject;
import main.physics.MotionProvider;
import main.physics.PhysicsUpdatable;

public abstract class WorldObject implements Drawable, Updatable, PhysicsUpdatable {

	protected final GraphicObject graphicObject;
	protected final CollisionShape collisionShape;
	private final RigidBody rigidBody;
	private final MotionState motionState;
	private World world;
	
	public WorldObject(GraphicObject graphicObject, CollisionShape collisionShape, Vector3f initPos) {
		super();
		this.graphicObject = graphicObject;
		this.collisionShape = collisionShape;
		
		Matrix4f matrix = new Matrix4f(new Quat4f(0, 0, 0, 1), initPos, 1);
		Transform startTrans = new Transform(matrix);
		this.motionState = new MotionProvider(startTrans, this);
		
		startTrans.getOpenGLMatrix(graphicObject.getMatrix());
		
		RigidBodyConstructionInfo rigidBodyInfo = new RigidBodyConstructionInfo(0, this.motionState, this.collisionShape, new Vector3f(0, 0, 0));
		this.rigidBody = new RigidBody(rigidBodyInfo);
	}
	
	public WorldObject(GraphicObject graphicObject, Vector3f initPos) {
		super();
		this.graphicObject = graphicObject;
		this.collisionShape = null;
		this.rigidBody = null;
		
		Matrix4f matrix = new Matrix4f(new Quat4f(0, 0, 0, 1), initPos, 1);
		Transform startTrans = new Transform(matrix);
		this.motionState = new MotionProvider(startTrans, this);
		
		startTrans.getOpenGLMatrix(graphicObject.getMatrix());
	}
	
	public void setMass(float mass) {
		Vector3f localInertia = new Vector3f();
		collisionShape.calculateLocalInertia(mass, localInertia);
		getRigidBody().setMassProps(mass, localInertia);
	}
	
	public void setWorld(World world) {
		this.world = world;
	}

	public RigidBody getRigidBody() {
		return this.rigidBody;
	}

	public MotionState getMotionState() {
		return motionState;
	}

	@Override
	public void initDraw(GLAutoDrawable drawable, GL gl) {
	}
	
	@Override
	public void draw(GL gl, final GLU glu, GLUT glut) {
		graphicObject.draw(gl, glu, glut);
	}
	
	@Override
	public void updatePhysics(Transform updated) {
		updated.getOpenGLMatrix(graphicObject.getMatrix());
		if (world != null) {
			world.requestRender();
		}
	}

	public void setSize(Vector3f size) {
		if (rigidBody != null) {
			throw new UnsupportedOperationException("Mudar o tamanho de um objeto com corpo rígido depois de criado por enquanto não é possível");
		}
		graphicObject.setSize(size);
	}
	
}
