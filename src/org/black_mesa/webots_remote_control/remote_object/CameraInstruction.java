package org.black_mesa.webots_remote_control.remote_object;

import geometry.Geometry;

public class CameraInstruction implements Instruction {
	private static final long serialVersionUID = -1401919642170517372L;
	private final Type type = null;
	private final double[] args = null;

	private enum Type {
		MOVE, TURN, PITCH
	}

	@Override
	public Object execute(Object initialState) {
		ViewPointState vps = (ViewPointState) initialState;
		switch (type) {
		case MOVE:
			System.out.println(type + "; (" + args[0] + "," + args[1] + "," + args[2] + ")");
			return move(vps);
		case TURN:
			System.out.println(type + "; (" + args[0] + ")");
			return turn(vps);
		case PITCH:
			System.out.println(type + "; (" + args[0] + ")");
			return pitch(vps);
		}
		return null;
	}
	
	@Override
	public String toString() {
		switch (type) {
		case MOVE:
			return type + "(" + args[0] + "," + args[1] + "," + args[2] + ")";
		case TURN:
			return type + "(" + args[0] + ")";
		case PITCH:
			return type + "(" + args[0] + ")";
		}
		return null;
	}

	private ViewPointState move(ViewPointState vps) {
		ViewPointState ret = new ViewPointState();
		ret.r = vps.r.clone();
		args[2] = -args[2];
		double[][] mat = Geometry.axisAngleToMatrix(vps.r);
		double[] newT = Geometry.rotate3DVectorMatrix(args, mat);
		ret.t = Geometry.add3DVector3DVector(vps.t, newT);
		return ret;
	}

	private ViewPointState turn(ViewPointState vps) {
		ViewPointState ret = new ViewPointState();
		double[] rotation = new double[4];
		rotation[0] = 0;
		rotation[1] = 1;
		rotation[2] = 0;
		rotation[3] = -args[0];
		ret.r = Geometry.composeAxisAngleAxisAngle(rotation, vps.r);
		ret.t = vps.t.clone();
		return ret;
	}

	private ViewPointState pitch(ViewPointState vps) {
		ViewPointState ret = new ViewPointState();
		double[] rotation = new double[4];
		rotation[0] = 1;
		rotation[1] = 0;
		rotation[2] = 0;
		rotation[3] = -args[0];
		ret.r = Geometry.composeAxisAngleAxisAngle(vps.r, rotation);
		ret.t = vps.t.clone();
		return ret;
	}
}