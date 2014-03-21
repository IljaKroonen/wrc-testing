package wrc_testing.server;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.black_mesa.webots_remote_control.remote_object.InstructionQueue;

/**
 * @author Ilja Kroonen
 */
public class Server {
	// This value must only be assigned ; the object never gets modified
	private InstructionQueue iQueue;

	public Server(int port, InstructionQueue initialState) {
		iQueue = initialState;
		new Thread(new Runnable() {
			@Override
			public void run() {
				startServer();
			}
		}).start();
	}

	public InstructionQueue getCamera() {
		return iQueue;
	}

	private void startServer() {
		try {
			ServerSocket serverSocket = new ServerSocket(42511);
			System.out.println("Server online on port " + serverSocket.getLocalPort());
			while (true) {
				try {
					serverRoutine(serverSocket);
				} catch (EOFException e) {
					System.out.println("Client disconnected");
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
		out.writeObject(iQueue);
		System.out.println("Camera sent");
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		while (true) {
			System.out.println("Reading object from the in stream");
			iQueue = (InstructionQueue) in.readObject();
			System.out.println(iQueue.toString());
		}
	}

	public static void main(String args[]) {
		new Server(42511, new InstructionQueue(0));
		new Server(42512, new InstructionQueue(0));
		new Server(42513, new InstructionQueue(0));
		new Server(42514, new InstructionQueue(0));
	}

}
