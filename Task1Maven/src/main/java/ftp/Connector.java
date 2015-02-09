package ftp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;

public class Connector {
	private static class SingletonHolder {
		private final static Connector instance = new Connector();
	}	
	
	public static Connector getInstance() {
		return SingletonHolder.instance;
		
	}
	
	public FTPClient connectToFTPServer() {
		FTPClient ftpClient = new FTPClient();
		
		while (true) {
			String server = getFTPServerName();
			final int port = 21;
			String user = getUser();
			String pass = getPass();

			boolean success = false;
			try {
				System.out.println("Establishing connection...");
				ftpClient.connect(server, port);
				success = ftpClient.login(user, pass);
			} catch (SocketException e) {
			} catch (IOException e) {
			}
			
			if (success) {
				System.out.println("Connected...");
				System.out.println();
				
				return ftpClient;
			} else {
				System.out.println("Connection error...");
				System.out.println("Please check connection data...");
				System.out.println();
				continue;
			}
		}
	}
	
	public void exitFTPServer(FTPClient ftpClient) {
		try {
			ftpClient.logout();
			ftpClient.disconnect();
		} catch (IOException e) {
			System.out.println("Logout error");
		}

		System.out.println("Bye...");
	}
	
	private String getStringFromConsole() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String text = br.readLine();
		System.out.println();

		return text;
	}
	
	private String getFTPServerName() {
		String server = "ftp.mozilla.org";

		while (true) {
			System.out.println("Enter the FTP-Server address"
					+ "(or press Enter to access ftp.mozilla.org):");
			String choice = "";
			try {
				choice = getStringFromConsole();
			} catch (IOException e) {
				continue;
			}

			if (choice.equals("")) {
				System.out.println(server);
				System.out.println();
			} else {
				server = choice;
			}

			server.trim();

			return server;
		}
	}
	
	private String getUser() {
		String user = "anonymous";

		while (true) {
			System.out
					.println("Enter username(or press Enter to set username 'anonymous'):");
			String choice = "";
			try {
				choice = getStringFromConsole();
			} catch (IOException e) {
				continue;
			}

			if (choice.equals("")) {
				System.out.println(user);
				System.out.println();
			} else {
				user = choice;
			}

			return user;
		}
	}

	private String getPass() {
		String pass = "anonymous";

		while (true) {
			System.out
					.println("Enter password(or press Enter to set password 'anonymous'):");
			String choice = "";
			try {
				choice = getStringFromConsole();
			} catch (IOException e) {
				continue;
			}

			if (choice.equals("")) {
				System.out.println(pass);
				System.out.println();
			} else {
				pass = choice;
			}

			return pass;
		}
	}
}
