package com.nemnous.bank.dao.factory;


import com.nemnous.bank.dao.AccountDAO;
import com.nemnous.bank.dao.AccountTypeDAO;
import com.nemnous.bank.dao.BankDAO;
import com.nemnous.bank.dao.CustomerDAO;
import com.nemnous.bank.dao.TransactionDAO;
import com.nemnous.bank.dao.TransactionTypeDAO;
import com.nemnous.bank.dao.inmemory.impl.InMemoryAccountDAOImpl;
import com.nemnous.bank.dao.inmemory.impl.InMemoryAccountTypeDAOImpl;
import com.nemnous.bank.dao.inmemory.impl.InMemoryBankDAOImpl;
import com.nemnous.bank.dao.inmemory.impl.InMemoryCustomerDAOImpl;

/**
 * This is Inmemory DAO Factory extending the abstract class
 * DAOFactory.
 * InMemory is used by Console reader FileReader and PreopertyFileReader.
 * @author nemnous
 *
 */
public class InMemoryDAOFactory extends DAOFactory {
	
	@Override
	public CustomerDAO getCustomerDAO() {
		return new InMemoryCustomerDAOImpl();
	}

	@Override
	public AccountDAO getAccountDAO() {
		return new InMemoryAccountDAOImpl();
	}

	@Override
	public BankDAO getBankDAO() {
		return new InMemoryBankDAOImpl();
	}

	@Override
	public AccountTypeDAO getAccountTypeDAO() {
		return new InMemoryAccountTypeDAOImpl();
	}

	@Override
	public TransactionDAO getTransactionDAO() {
		return null;
	}

	@Override
	public TransactionTypeDAO getTransactionTypeDAO() {
		return null;
	}
	
	
}
