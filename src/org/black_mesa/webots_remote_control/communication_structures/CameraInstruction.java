package org.black_mesa.webots_remote_control.communication_structures;

import java.io.Serializable;

import geometry.Geometry;

public class CameraInstruction implements Serializable {
	private static final long serialVersionUID = -1401919642170517372L;
	private final Type mType = null;
	private final double[] mArgs = null;

	private enum Type {
		MOVE, TURN, PITCH
	}

	public ViewPointState execute(ViewPointState initialState) {
		switch (mType) {
		case MOVE:
			System.out.println(mType + "(" + mArgs[0] + "," + mArgs[1] + "," + mArgs[2] + ")");
			return move(initialState);
		case TURN:
			System.out.println(mType + "(" + mArgs[0] + ")");
			return turn(initialState);
		case PITCH:
			System.out.println(mType + "(" + mArgs[0] + ")");
			return pitch(initialState);
		}
		return null;
	}

	private ViewPointState move(ViewPointState vps) {
		ViewPointState ret = new ViewPointState();
		ret.r = vps.r.clone();
		mArgs[2] = -mArgs[2];
		double[][] mat = Geometry.axisAngleToMatrix(vps.r);
		double[] newT = Geometry.rotate3DVectorMatrix(mArgs, mat);
		ret.t = Geometry.add3DVector3DVector(vps.t, newT);
		return ret;
	}

	private ViewPointState turn(ViewPointState vps) {
		ViewPointState ret = new ViewPointState();
		double[] rotation = new double[4];
		rotation[0] = 0;
		rotation[1] = 1;
		rotation[2] = 0;
		rotation[3] = -mArgs[0];
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
		rotation[3] = -mArgs[0];
		ret.r = Geometry.composeAxisAngleAxisAngle(vps.r, rotation);
		ret.t = vps.t.clone();
		return ret;
	}
	
	@Override
	public String toString() {
		switch (mType) {
		case MOVE:
			return mType + "(" + mArgs[0] + "," + mArgs[1] + "," + mArgs[2] + ")";
		case TURN:
			return mType + "(" + mArgs[0] + ")";
		case PITCH:
			return mType + "(" + mArgs[0] + ")";
		}
		return null;
	}
}