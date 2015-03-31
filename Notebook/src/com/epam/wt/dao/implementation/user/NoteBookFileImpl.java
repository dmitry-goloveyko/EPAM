package com.epam.wt.dao.implementation.user;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ResourceBundle;

import com.epam.wt.dao.implementation.ImplSourceRecordNotFoundException;
import com.epam.wt.dao.implementation.ImplSourceNotFoundException;
import com.epam.wt.dao.implementation.ImplIOException;
import com.epam.wt.dao.INoteBookDAO;
import com.epam.wt.entity.Note;
import com.epam.wt.fileutil.FileUtils;

public class NoteBookFileImpl implements INoteBookDAO {
	private static final NoteBookFileImpl instance = new NoteBookFileImpl();
	
	
	private static final ResourceBundle resource = ResourceBundle.getBundle("resources.Notebook");
	private static String fileName = resource.getString("NotebookFile");
	private static final File noteBookFile = FileUtils.createOrOpenFile(fileName);
	
	public static NoteBookFileImpl getInstance() {
		return instance;
	}
	
	@Override
	public void addNote(Note note) throws ImplIOException{
		FileWriter fw = null;
		try {
			fw = new FileWriter(noteBookFile, true);
		} catch (IOException e) {
			throw new ImplIOException(e.getMessage());
		}
		PrintWriter writer = new PrintWriter(fw);

		writer.append(note.toString() + "\r\n");
		writer.close();
	}

	@Override
	public Note getNoteAtIndex(int index) throws ImplSourceNotFoundException, ImplIOException, ImplSourceRecordNotFoundException {
		int linesInFile = 0;
		try {
			linesInFile = FileUtils.countLines(noteBookFile.getAbsolutePath());
		} catch (IOException e) {
			throw new ImplIOException(e.getMessage());
		}
		
		if(linesInFile <= index) {
			throw new ImplSourceRecordNotFoundException("Note with such index does not exist");
		}
		
		
		FileInputStream file = null;
		try {
			file = new FileInputStream(noteBookFile.getAbsolutePath());
		} catch (FileNotFoundException e) {
			throw new ImplSourceNotFoundException(e.getMessage());
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(file));
		String lineIWant = null;
		try {
			for(int i = 0; i < index; i++) {
				br.readLine();
			}
			lineIWant = br.readLine();
			br.close();
		} catch (IOException e) {
			throw new ImplIOException(e.getMessage());
		}
		
		Note note = FileUtils.makeNoteFromFileLine(lineIWant);
		
		return note;
	}

	@Override
	public void removeNoteAtIndex(int index) throws ImplSourceNotFoundException, ImplIOException, ImplSourceRecordNotFoundException {
		int linesInFile = 0;
		try {
			linesInFile = FileUtils.countLines(noteBookFile.getAbsolutePath());
		} catch (IOException e) {
			throw new ImplIOException(e.getMessage());
		}
		
		if(linesInFile <= index) {
			throw new ImplSourceRecordNotFoundException("Note with such index does not exist");
		}
		
		
		File oldFile = noteBookFile;
	    File tmp = null;
		try {
			tmp = File.createTempFile("tmp", "");
		} catch (IOException e) {
			throw new ImplIOException(e.getMessage());
		}

		
	    BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(noteBookFile));
		} catch (FileNotFoundException e) {
			throw new ImplSourceNotFoundException(e.getMessage());
		}
		
		
	    BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(tmp));
			
			for (int i = 0; i < index; i++) {
				String l = br.readLine();
		        bw.write(String.format("%s%n", l));
		    }
			
			br.readLine();
			
			String line;
		    while (null != (line = br.readLine())) {
		    	if(line != null) {
		    		bw.write(String.format("%s%n", line));
		    	}
		    }

		    br.close();
		    bw.close();
		} catch (IOException e) {
			throw new ImplIOException(e.getMessage());
		}
		
		
	    if (oldFile.delete()) {
	        tmp.renameTo(oldFile);
	    }
	}
	
	@Override
	public void clearNotebook() throws ImplIOException {
		File oldFile = noteBookFile;
	    File tmp = null;
		try {
			tmp = File.createTempFile("tmp", "");
		} catch (IOException e) {
			throw new ImplIOException(e.getMessage());
		}
	    
	    if (oldFile.delete()) {
	        tmp.renameTo(oldFile);
	    }
	}
	
}