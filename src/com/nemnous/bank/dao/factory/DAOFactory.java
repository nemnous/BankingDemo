package com.nemnous.bank.dao.factory;

import com.nemnous.bank.dao.AccountDAO;
import com.nemnous.bank.dao.AccountTypeDAO;
import com.nemnous.bank.dao.BankDAO;
import com.nemnous.bank.dao.CustomerDAO;
import com.nemnous.bank.dao.TransactionDAO;
import com.nemnous.bank.dao.TransactionTypeDAO;

/**
 * Gives the required DAO objects.
 * like if we want MySql DAO's 
 * all we need to do is DAOFactory.getDAOFactory(DAOFactory.MYSQL);
 * @author nemnous
 *
 */
public abstract class DAOFactory {
	
	public static final int MYSQL = 1;
	public static final int INMEMORY = 2;
	
	public abstract CustomerDAO getCustomerDAO();
	public abstract AccountDAO getAccountDAO();
	public abstract BankDAO getBankDAO();
	public abstract AccountTypeDAO getAccountTypeDAO();
	public abstract TransactionDAO getTransactionDAO();
	public abstract TransactionTypeDAO getTransactionTypeDAO();
	
	public static DAOFactory getDAOFactory(int whichFactory) {
 
	    switch (whichFactory) {
	      case MYSQL:
	          return new MYSQLDbDAOFactory();
	      case INMEMORY:
	    	  return new InMemoryDAOFactory();
	      default: 
	    	  return null;
	    }
	}
}


//https://gerardnico.com/lang/java/dao