package com.epam.wt.command;

import com.epam.wt.dao.INoteBookDAO;
import com.epam.wt.dao.implementation.ImplDOMParserConfigurationException;
import com.epam.wt.dao.implementation.ImplIOException;
import com.epam.wt.dao.implementation.ImplJDBCClassNotFoundException;
import com.epam.wt.dao.implementation.ImplSQLException;
import com.epam.wt.dao.implementation.ImplSourceNotFoundException;
import com.epam.wt.datatempstorage.Request;
import com.epam.wt.datatempstorage.Responce;
import com.epam.wt.abstractdao.AbstractDAOFactory;
import com.epam.wt.command.CommandIOException;
import com.epam.wt.entity.Note;

public class AddCommand implements Command {

	@Override
	public Responce execute(Request request) throws CommandIOException,
			CommandSourceNotFoundException,
			CommandDOMParserConfigurationException, CommandSQLException,
			CommandClassNotFoundException {

		INoteBookDAO dao = AbstractDAOFactory.getDAOFactory().getNoteBookDao();

		try {
			dao.addNote((Note) request.getValue("note"));
		} catch (ImplIOException e) {
			throw new CommandIOException(e.getMessage());
		} catch (ImplSourceNotFoundException e) {
			throw new CommandSourceNotFoundException(e.getMessage());
		} catch (ImplDOMParserConfigurationException e) {
			throw new CommandDOMParserConfigurationException(e.getMessage());
		} catch (ImplSQLException e) {
			throw new CommandSQLException(e.getMessage());
		} catch (ImplJDBCClassNotFoundException e) {
			throw new CommandClassNotFoundException(e.getMessage());
		}

		return null;
	}
}
