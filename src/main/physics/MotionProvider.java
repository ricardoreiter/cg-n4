package main.physics;

import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;

public class MotionProvider extends MotionState {

	/** Current interpolated world transform, used to draw object. */
	public final Transform graphicsWorldTrans = new Transform();
	
	/** Center of mass offset transform, used to adjust graphics world transform. */
	public final Transform centerOfMassOffset = new Transform();
	
	private PhysicsUpdatable listener;
	
	public MotionProvider(PhysicsUpdatable listener) {
		graphicsWorldTrans.setIdentity();
		centerOfMassOffset.setIdentity();
		this.listener = listener;
	}

	public MotionProvider(Transform startTrans) {
		this.graphicsWorldTrans.set(startTrans);
		centerOfMassOffset.setIdentity();
	}
	
	public MotionProvider(Transform startTrans, Transform centerOfMassOffset) {
		this.graphicsWorldTrans.set(startTrans);
		this.centerOfMassOffset.set(centerOfMassOffset);
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
