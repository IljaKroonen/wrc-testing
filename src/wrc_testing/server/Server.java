package wrc_testing.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.black_mesa.webots_remote_control.Camera;

/**
 * @author Ilja Kroonen
 */
public class Server {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(42511);
			System.out.println("Testing server online on port " + serverSocket.getLocalPort());
			while (true) {
				try {
					Socket socket = serverSocket.accept();
					System.out.println("Incoming connection");
					ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
					Camera camera = new Camera(0, 0, 0, 0, 0, 1, 0);
					System.out.println("Sending camera");
					out.writeObject(camera);
					System.out.println("Camera sent");
					ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
					while (true) {
						System.out.println("Reading object from the in stream");
						camera = (Camera) in.readObject();
						System.out.println(camera.toString());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
