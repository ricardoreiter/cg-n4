package main.physics;

import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;

public class MotionProvider extends MotionState {

	private PhysicsUpdatable listener;
	
	/** Current interpolated world transform, used to draw object. */
	public final Transform graphicsWorldTrans = new Transform();
	
	/** Center of mass offset transform, used to adjust graphics world transform. */
	public final Transform centerOfMassOffset = new Transform();
	
	/** Initial world transform. */
	public final Transform startWorldTrans = new Transform();
	
	/**
	 * Creates a new MotionProvider with all transforms set to identity.
	 */
	public MotionProvider() {
		graphicsWorldTrans.setIdentity();
		centerOfMassOffset.setIdentity();
		startWorldTrans.setIdentity();
	}

	/**
	 * Creates a new MotionProvider with initial world transform and center
	 * of mass offset transform set to identity.
	 */
	public MotionProvider(Transform startTrans, PhysicsUpdatable listener) {
		this.graphicsWorldTrans.set(startTrans);
		centerOfMassOffset.setIdentity();
		this.startWorldTrans.set(startTrans);
		this.listener = listener;
	}
	
	/**
	 * Creates a new MotionProvider with initial world transform and center
	 * of mass offset transform.
	 */
	public MotionProvider(Transform startTrans, Transform centerOfMassOffset) {
		this.graphicsWorldTrans.set(startTrans);
		this.centerOfMassOffset.set(centerOfMassOffset);
		this.startWorldTrans.set(startTrans);
	}
	
	public Transform getWorldTransform(Transform out) {
		out.inverse(centerOfMassOffset);
		out.mul(graphicsWorldTrans);
		return out;
	}

	public void setWorldTransform(Transform centerOfMassWorldTrans) {
		graphicsWorldTrans.set(centerOfMassWorldTrans);
		graphicsWorldTrans.mul(centerOfMassOffset);
		listener.updatePhysics(graphicsWorldTrans);
	}
	
}
