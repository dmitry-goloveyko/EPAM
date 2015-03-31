package com.epam.wt.controller;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.joda.time.DateTime;

import com.epam.wt.command.Command;
import com.epam.wt.command.CommandClassNotFoundException;
import com.epam.wt.command.CommandDOMParserConfigurationException;
import com.epam.wt.command.CommandHelper;
import com.epam.wt.command.CommandName;
import com.epam.wt.command.CommandSQLException;
import com.epam.wt.command.CommandSourceRecordNotFoundException;
import com.epam.wt.command.CommandSourceNotFoundException;
import com.epam.wt.command.CommandIOException;
import com.epam.wt.datatempstorage.Request;
import com.epam.wt.datatempstorage.Responce;
import com.epam.wt.entity.AlertNote;
import com.epam.wt.entity.ColorNote;
import com.epam.wt.entity.Note;

public class Controller {
	private static final Logger logger = Logger.getLogger(Controller.class);
	private static final Controller instance = new Controller();
	
	public static Controller getInstance() {
		return instance;
	}
	

	private CommandHelper helper = new CommandHelper();

	public <T> Responce doRequest(CommandName commandName, T req) {

		Command command = helper.getCommand(commandName);

		Request request = null;
		if (req != null) {
			request = prepareParams(req);
		}

		Responce response = null;

		try {
			response = command.execute(request);
		} catch (CommandIOException e) {
			response = new Responce();
			response.setResponse("ioExceptionNotification");
			logger.log(Priority.WARN, e.getStackTrace());
		} catch (CommandSourceNotFoundException e) {
			response = new Responce();
			response.setResponse("fileNotFoundNotification");
			logger.log(Priority.WARN, e.getStackTrace());
		} catch (CommandSourceRecordNotFoundException e) {
			response = new Responce();
			response.setResponse("sourceRecordNotFoundNotification");
			logger.log(Priority.WARN, e.getStackTrace());
		} catch (CommandDOMParserConfigurationException e) {
			response = new Responce();
			response.setResponse("xmlFileStructureError");
			logger.log(Priority.WARN, e.getStackTrace());
		} catch (CommandSQLException e) {
			response = new Responce();
			response.setResponse("sqlException");
			logger.log(Priority.WARN, e.getStackTrace());
		} catch (CommandClassNotFoundException e) {
			response = new Responce();
			response.setResponse("classNotFoundException");
			logger.log(Priority.WARN, e.getStackTrace());
		}
		
		return response;
	}


	private <T> Request prepareParams(T param) {
		Request request = new Request();

		Object requestParam = param;
		if (param instanceof Map) {
			requestParam = makeNote((Map<String, Object>) param);
		}

		request.setValue(requestParam);

		return request;
	}

	private Note makeNote(Map<String, Object> params) {
		Note note = null;
		String text = (String) params.get("text");

		if (params.containsKey("alertDate")) {
			DateTime alertDate = (DateTime) params.get("alertDate");
			note = new AlertNote(text, alertDate);
		} else if (params.containsKey("color")) {
			String color = (String) params.get("color");
			note = new ColorNote(text, color);
		} else {
			note = new Note(text);
		}
		
		params.clear();

		return note;
	}
}
