package com.epam.tests.abstractdao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.epam.wt.abstractdao.AbstractDAOFactory;
import com.epam.wt.abstractdao.dao.NoteBookMemoryDaoFactory;
import com.epam.wt.abstractdao.dao.implementation.user.NoteBookMemoryImpl;

public class AbstractDAOTests {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetDAOFactory() {
		assertTrue(AbstractDAOFactory.getDAOFactory() instanceof NoteBookMemoryDaoFactory);
	}

	@Test
	public void testGetDAOImpl() {
		NoteBookMemoryDaoFactory dao = (NoteBookMemoryDaoFactory) AbstractDAOFactory.getDAOFactory();
		
		assertTrue(dao.getNoteBookDao() instanceof NoteBookMemoryImpl);
	}
}
