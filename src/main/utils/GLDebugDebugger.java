package main.utils;

import javax.media.opengl.GL;
import javax.vecmath.Vector3f;

import com.bulletphysics.linearmath.DebugDrawModes;
import com.bulletphysics.linearmath.IDebugDraw;

public class GLDebugDebugger extends IDebugDraw {
	
	private final GL gl;
	private int debugMode = DebugDrawModes.NO_DEBUG;
	
	public GLDebugDebugger(GL gl) {
		super();
		this.gl = gl;
	}

	@Override
	public void drawLine(Vector3f from, Vector3f to, Vector3f color) {
	  gl.glPushMatrix();
	  {         
	     gl.glColor4f(color.getX(), color.getY(), color.getZ(), 1.0f);

	     gl.glBegin(GL.GL_LINES);
			
			gl.glVertex3f(from.getX(),  from.getY(), from.getZ());
			gl.glVertex3f(to.getX(), to.getY(), to.getZ());
			
		 gl.glEnd();
		 
	     gl.glPointSize( 5.0f );
	     
	     gl.glBegin(GL.GL_POINTS);
			
			gl.glVertex3f(from.getX(),  from.getY(), from.getZ());
			gl.glVertex3f(to.getX(), to.getY(), to.getZ());
			
		 gl.glEnd();
	  }
	  gl.glPopMatrix();      
	}

	@Override
	public void drawContactPoint(Vector3f PointOnB, Vector3f normalOnB, float distance, int lifeTime, Vector3f color) {
		gl.glPointSize( 5.0f );
		gl.glBegin(GL.GL_POINTS);
		
		gl.glColor4f(color.getX(), color.getY(), color.getZ(), 1.0f);
		gl.glNormal3f(normalOnB.getX(), normalOnB.getY(), normalOnB.getZ());
		gl.glVertex3f(PointOnB.getX(), PointOnB.getY(), PointOnB.getZ());
		
		gl.glEnd();
	}

	@Override
	public void reportErrorWarning(String warningString) {
		System.out.println(String.format("WARNING: %s", warningString));
	}

	@Override
	public void draw3dText(Vector3f location, String textString) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDebugMode(int debugMode) {
		this.debugMode = debugMode;
	}

	@Override
	public int getDebugMode() {
		return debugMode;
	}

}
