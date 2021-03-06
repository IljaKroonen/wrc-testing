package geometry;

public class Geometry {
	private static final double EPSILON = 0.00000000001;

	public static double[] invertAxisAngle(double[] r) {
		double[] ret = new double[4];

		ret[0] = r[0];
		ret[1] = r[1];
		ret[2] = r[2];
		ret[3] = -r[3];

		return ret;
	}

	public static double[] rotate3DVectorMatrix(double[] t, double[][] m) {
		double[] ret = new double[3];

		for (int i = 0; i < 3; i++) {
			ret[i] = 0;
			for (int j = 0; j < 3; j++) {
				ret[i] += m[i][j] * t[j];
			}
		}

		return ret;
	}

	public static double[][] axisAngleToMatrix(double[] r) {
		double[][] ret = new double[3][];
		for (int i = 0; i < 3; i++) {
			ret[i] = new double[3];
		}

		double c = Math.cos(r[3]);
		double s = Math.sin(r[3]);

		ret[0][0] = c + r[0] * r[0] * (1 - c);
		ret[0][1] = r[0] * r[1] * (1 - c) - r[2] * s;
		ret[0][2] = r[0] * r[2] * (1 - c) + r[1] * s;

		ret[1][0] = r[1] * r[0] * (1 - c) + r[2] * s;
		ret[1][1] = c + r[1] * r[1] * (1 - c);
		ret[1][2] = r[1] * r[2] * (1 - c) - r[0] * s;

		ret[2][0] = r[2] * r[0] * (1 - c) - r[1] * s;
		ret[2][1] = r[2] * r[1] * (1 - c) + r[0] * s;
		ret[2][2] = c + r[2] * r[2] * (1 - c);

		return ret;
	}

	public static double[] add3DVector3DVector(double[] t1, double[] t2) {
		double[] ret = new double[3];

		ret[0] = t1[0] + t2[0];
		ret[1] = t1[1] + t2[1];
		ret[2] = t1[2] + t2[2];

		return ret;
	}

	public static double[] composeAxisAngleAxisAngle(double[] r1, double[] r2) {
		double[] rot1Quaternion = axisAngleToQuaternion(r1);
		double[] rot2Quaternion = axisAngleToQuaternion(r2);

		double[] res = multiplyQuaternion(rot1Quaternion, rot2Quaternion);

		return quaternionToAxisAngle(res);
	}

	private static double[] axisAngleToQuaternion(double[] axisAngle) {
		double[] ret = new double[4];

		ret[0] = axisAngle[0] * Math.sin(axisAngle[3] / 2);
		ret[1] = axisAngle[1] * Math.sin(axisAngle[3] / 2);
		ret[2] = axisAngle[2] * Math.sin(axisAngle[3] / 2);
		ret[3] = Math.cos(axisAngle[3] / 2);

		return ret;
	}

	private static double[] quaternionToAxisAngle(double[] quaternion) {
		double[] ret = new double[4];

		ret[3] = 2 * Math.acos(quaternion[3]);
		if (Math.abs(ret[3]) < EPSILON) {
			ret[0] = 0;
			ret[1] = 0;
			ret[2] = 1;
		} else {
			ret[0] = quaternion[0] / Math.sqrt(1 - quaternion[3] * quaternion[3]);
			ret[1] = quaternion[1] / Math.sqrt(1 - quaternion[3] * quaternion[3]);
			ret[2] = quaternion[2] / Math.sqrt(1 - quaternion[3] * quaternion[3]);
		}

		return ret;
	}

	private static double[] multiplyQuaternion(double[] q1, double[] q2) {
		double[] ret = new double[4];

		ret[0] = q1[3] * q2[0] + q1[0] * q2[3] + q1[1] * q2[2] - q1[2] * q2[1];
		ret[1] = q1[3] * q2[1] + q1[1] * q2[3] + q1[2] * q2[0] - q1[0] * q2[2];
		ret[2] = q1[3] * q2[2] + q1[2] * q2[3] + q1[0] * q2[1] - q1[1] * q2[0];
		ret[3] = q1[3] * q2[3] - q1[1] * q2[1] - q1[2] * q2[2] - q1[0] * q2[0];

		return ret;
	}
}
