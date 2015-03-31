package com.epam.wt.command;

import com.epam.wt.dao.INoteBookDAO;
import com.epam.wt.dao.implementation.ImplDOMParserConfigurationException;
import com.epam.wt.dao.implementation.ImplJDBCClassNotFoundException;
import com.epam.wt.dao.implementation.ImplSQLException;
import com.epam.wt.dao.implementation.ImplSourceNotFoundException;
import com.epam.wt.dao.implementation.ImplSourceRecordNotFoundException;
import com.epam.wt.dao.implementation.ImplIOException;
import com.epam.wt.datatempstorage.Request;
import com.epam.wt.datatempstorage.Responce;
import com.epam.wt.abstractdao.AbstractDAOFactory;
import com.epam.wt.command.CommandSourceRecordNotFoundException;
import com.epam.wt.command.CommandSourceNotFoundException;
import com.epam.wt.command.CommandIOException;

public class DeleteCommand implements Command {

	@Override
	public Responce execute(Request request) throws CommandIOException,
			CommandSourceNotFoundException,
			CommandSourceRecordNotFoundException,
			CommandDOMParserConfigurationException, CommandSQLException,
			CommandClassNotFoundException {

		INoteBookDAO dao = AbstractDAOFactory.getDAOFactory().getNoteBookDao();

		try {
			dao.removeNoteAtIndex((int) request.getValue("index"));
		} catch (ImplSourceNotFoundException e) {
			throw new CommandSourceNotFoundException(e.getMessage());
		} catch (ImplIOException e) {
			throw new CommandIOException(e.getMessage());
		} catch (ImplSourceRecordNotFoundException e) {
			throw new CommandSourceRecordNotFoundException(e.getMessage());
		} catch (ImplDOMParserConfigurationException e) {
			throw new CommandDOMParserConfigurationException(e.getMessage());
		} catch (ImplSQLException e) {
			throw new CommandSQLException(e.getMessage());
		} catch (ImplJDBCClassNotFoundException e) {
			// TODO Auto-generated catch block
			throw new CommandClassNotFoundException(e.getMessage());
		}

		return null;
	}
}
