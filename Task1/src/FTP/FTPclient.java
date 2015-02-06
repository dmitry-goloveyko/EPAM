package FTP;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.SocketException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FTPclient {

	public static void main(String[] args) throws IOException {
		FTPClient ftpClient = connectToFTPServer();

		workWithDirectory(ftpClient);

		exitFTPServer(ftpClient);
	}

	private static FTPClient connectToFTPServer() {
		FTPClient ftpClient = new FTPClient();

		while (true) {
			String server = getFTPServerName();
			final int port = 21;
			String user = getUser();
			String pass = getPass();

			try {
				System.out.println("Establishing connection...");
				ftpClient.connect(server, port);
				boolean success = ftpClient.login(user, pass);
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
			} catch (SocketException e) {
			} catch (IOException e) {
			}
		}
	}

	// gets files and directories in directory
	private static void getDirectoryGuts(FTPClient ftpClient,
			String currentDirectory) {
		System.out.println("Current directory:  " + currentDirectory);
		System.out.println("List of files and directories([xxxx] is a directory):");

		// lists files and directories in the current working directory
		FTPFile[] files = null;
		try {
			files = ftpClient.listFiles(currentDirectory);
		} catch (IOException e) {
		}

		displayList(files);
	}

	private static void workWithDirectory(FTPClient ftpClient)
			throws IOException {
		String currentDirectory = "/";
		// walking through directory
		while (true) {
			// displaying what's in directory
			getDirectoryGuts(ftpClient, currentDirectory);

			// getting name of file or directory from console
			String choice = getUserChoice();

			final String EXIT_APP_STRING = "exit";
			final String TO_PARENT_DIRECTORY_STRING = "back";

			// terminating app if user types "exit"
			if (choice.equals(EXIT_APP_STRING)) {
				break;
			}
			// changing to parent directory if user types 'back'
			if (choice.equals(TO_PARENT_DIRECTORY_STRING)) {
				currentDirectory = changeToParentDirectory(currentDirectory);
				continue;
			}

			currentDirectory = respondOnUserChoice(ftpClient, currentDirectory,
					choice);
		}
	}

	private static void exitFTPServer(FTPClient ftpClient) {
		try {
			ftpClient.logout();
			ftpClient.disconnect();
		} catch (IOException e) {
			System.out.println("Logout error");
		}

		System.out.println("Bye...");
	}

	// displays list of files and directories in the directory
	private static void displayList(FTPFile[] files) {
		for (FTPFile file : files) {
			String details = file.getName();
			if (file.isDirectory()) {
				details = "[" + details + "]";
			}
			System.out.println(details);
		}
	}

	// checks the wanted file or directory exists on the FTP-server
	private static FTPFile existingFileOrDirectory(String s, FTPFile[] files) {
		for (FTPFile file : files) {
			if (file.getName().equals(s)) {
				return file;
			}
		}

		return null;
	}

	private static String getStringFromConsole() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String text = br.readLine();
		System.out.println();

		return text;
	}

	// gets user choice on file downloading or changing to child directory and then
	// downloads file or cd's
	private static String respondOnUserChoice(FTPClient ftpClient,
			String currentDirectory, String choice) {
		FTPFile[] files = null;
		try {
			files = ftpClient.listFiles(currentDirectory);
		} catch (IOException e) {
		}
		FTPFile file = existingFileOrDirectory(choice, files);

		// if there's no such signature in directory
		if (file == null) {
			System.out.println("No such file or directory");
		}
		// if signature exists
		else {
			// if user chose file -> downloading this file
			if (file.isFile()) {
				try {
					downloadFile(ftpClient, file, currentDirectory);
				} catch (IOException e) {

				}
			}

			// if user chose directory -> changing to this directory
			if (file.isDirectory()) {
				// changing to the chosen directory
				currentDirectory += file.getName() + "/";
			}
		}

		return currentDirectory;
	}

	// downloads the wanted file from FTP-server
	private static void downloadFile(FTPClient ftpClient, FTPFile file,
			String currentDirectory) throws IOException {

		// getting the directory the user wants to save file to
		System.out.println("Type the directory to save the file to:");
		File localDirectory = new File(getStringFromConsole());
		if (!localDirectory.isDirectory()) {
			System.out.println("No such directory on your computer");
			System.out.println();
			return;
		}

		// saving file
		System.out.println("Downloading file...");
		String remoteFile = currentDirectory + file.getName();
		File downloadFile = new File(localDirectory + "/" + file.getName());
		OutputStream outputStream = new BufferedOutputStream(
				new FileOutputStream(downloadFile));
		boolean success = ftpClient.retrieveFile(remoteFile, outputStream);
		outputStream.close();

		// report on successfully downloaded file
		if (success) {
			System.out.println("File has been downloaded successfully:");
			System.out.println(downloadFile.getAbsoluteFile());
			System.out.println("Press any key to proceed");
			getStringFromConsole();
			System.out.println();
		} else {
			System.out
					.println("Your file has not been downloaded. Please try once more");
			System.out.println();
		}
	}

	private static String changeToParentDirectory(String currentDirectory) {
		// checking for root directory
		if (currentDirectory.length() < 2) {
			System.out.println("This is a root directory!");
			System.out.println();
			return currentDirectory;
		}

		// if this is not a root directory
		// cutting last /(slash)
		currentDirectory = currentDirectory.substring(0,
				currentDirectory.length() - 1);

		// cutting name of last directory in absolute path
		String parentDirectory = cutLastDirectory(currentDirectory);

		return parentDirectory;
	}

	// cuts the name of last directory in absolute path on FTP
	private static String cutLastDirectory(String directory) {
		String parentDirectory = null;
		for (int i = directory.length() - 1; i >= 0; i--) {
			if (directory.charAt(i) == '/') {
				parentDirectory = directory.substring(0, i + 1);
				return parentDirectory;
			}
		}

		return parentDirectory;
	}

	private static String getUserChoice() throws IOException {
		System.out
				.println("Enter name of file to download it or enter name of "
						+ "directory to change to this directory"
						+ "\n(instead of this you can type 'exit' to exit the app "
						+ "or type 'back' to change to parent directory):");
		String choice = getStringFromConsole();

		return choice;
	}

	private static String getFTPServerName() {
		String server = "ftp.mozilla.org";

		while (true) {
			System.out.println("Enter the FTP-Server address(or press Enter to access ftp.mozilla.org):");
			String choice = "";
			try {
				choice = getStringFromConsole();
			} catch (IOException e) {
				continue;
			}
			
			if(choice.equals(""))
			{
				System.out.println(server);
				System.out.println();
			}
			else {
				server = choice;
			}
			
			server.trim();

			return server;
		}
	}

	private static String getUser() {
		String user = "anonymous";

		while (true) {
			System.out.println("Enter username(or press Enter to set username 'anonymous'):");
			String choice = "";
			try {
				choice = getStringFromConsole();
			} catch (IOException e) {
				continue;
			}
			
			if(choice.equals(""))
			{
				System.out.println(user);
				System.out.println();
			}
			else {
				user = choice;
			}

			return user;
		}
	}

	private static String getPass() {
		String pass = "anonymous";

		while (true) {
			System.out.println("Enter password(or press Enter to set password 'anonymous'):");
			String choice = "";
			try {
				choice = getStringFromConsole();
			} catch (IOException e) {
				continue;
			}
			
			if(choice.equals(""))
			{
				System.out.println(pass);
				System.out.println();
			}
			else {
				pass = choice;
			}

			return pass;
		}
	}
}
