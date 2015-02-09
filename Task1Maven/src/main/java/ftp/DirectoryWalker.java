package ftp;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class DirectoryWalker {
	
	private void workWithDirectory(FTPClient ftpClient)
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
	
	// gets files and directories in directory
	private void getDirectoryGuts(FTPClient ftpClient,
			String currentDirectory) {
		System.out.println("Current directory:  " + currentDirectory);
		System.out
				.println("List of files and directories([xxxx] is a directory):");

		// lists files and directories in the current working directory
		FTPFile[] files = null;
		try {
			files = ftpClient.listFiles(currentDirectory);
		} catch (IOException e) {
		}

		displayList(files);
	}
	
	// displays list of files and directories in the directory
	private void displayList(FTPFile[] files) {
		for (FTPFile file : files) {
			String details = file.getName();
			if (file.isDirectory()) {
				details = "[" + details + "]";
			}
			System.out.println(details);
		}
	}
	
	
}
