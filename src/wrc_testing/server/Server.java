package wrc_testing.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.black_mesa.webots_remote_control.remote_object_state.RemoteCameraState;

/**
 * @author Ilja Kroonen
 */
public class Server {
	// This value must only be assigned ; the object never gets modified
	private RemoteCameraState camera;

	public Server(int port, RemoteCameraState initialState) {
		camera = initialState;
		new Thread(new Runnable() {
			@Override
			public void run() {
				startServer();
			}
		}).start();
	}

	public RemoteCameraState getCamera() {
		return camera;
	}

	private void startServer() {
		try {
			ServerSocket serverSocket = new ServerSocket(42511);
			System.out.println("Server online on port " + serverSocket.getLocalPort());
			while (true) {
				try {
					serverRoutine(serverSocket);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void serverRoutine(ServerSocket serverSocket) throws Exception {
		Socket socket = serverSocket.accept();
		System.out.println("Incoming connection");
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		out.writeObject(1);
		System.out.println("Sending camera");
		out.writeObject(camera);
		System.out.println("Camera sent");
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		while (true) {
			System.out.println("Reading object from the in stream");
			camera = (RemoteCameraState) in.readObject();
			System.out.println(camera.toString());
		}
	}

	public static void main(String args[]) {
		double[] t = { 0, 0, 0 };
		double[] r = { 0, 0, 1, 0 };
		RemoteCameraState iState = new RemoteCameraState(0, t, r);
		new Server(42511, iState);
	}

}
