package com.nemnous.bank.dao.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.nemnous.bank.dao.AccountDAO;
import com.nemnous.bank.dao.AccountTypeDAO;
import com.nemnous.bank.dao.BankDAO;
import com.nemnous.bank.dao.CustomerDAO;
import com.nemnous.bank.dao.TransactionDAO;
import com.nemnous.bank.dao.TransactionTypeDAO;
import com.nemnous.bank.dao.jdbc.impl.AccountDAOImpl;
import com.nemnous.bank.dao.jdbc.impl.AccountTypeDAOImpl;
import com.nemnous.bank.dao.jdbc.impl.BankDAOImpl;
import com.nemnous.bank.dao.jdbc.impl.CustomerDAOImpl;
import com.nemnous.bank.dao.jdbc.impl.TransactionDAOImpl;
import com.nemnous.bank.dao.jdbc.impl.TransactionTypeDAOImpl;

//https://stackoverflow.com/questions/14757973/is-it-bad-programming-practice-to-place-abstract-classes-inside-the-same-package


public class MYSQLDbDAOFactory extends DAOFactory {
	private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static Connection connection = null;
	
	  
    static
    { 
        String url = "jdbc:mysql:// localhost:3306/banking"; 
        String user = "root"; 
        String password = "test"; 
        try { 
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            connection = DriverManager.getConnection(url, user, password); 
        } catch (ClassNotFoundException e) {
        	logger.log(Level.WARNING, "Driver class Not found");
        } catch (SQLException  e) {
			logger.log(Level.WARNING,"Cant connect to SQL server");
		}
    }
    
    public static Connection getConnection() 
    { 
        return connection; 
    }


	@Override
	public CustomerDAO getCustomerDAO() {
		
		return new CustomerDAOImpl();
	}

	@Override
	public AccountDAO getAccountDAO() {
		
		return new AccountDAOImpl();
	}

	@Override
	public BankDAO getBankDAO() {
		return  new BankDAOImpl();
	}


	@Override
	public AccountTypeDAO getAccountTypeDAO() {
		return new AccountTypeDAOImpl();
	}


	@Override
	public TransactionDAO getTransactionDAO() {
		return new TransactionDAOImpl();
	}


	@Override
	public TransactionTypeDAO getTransactionTypeDAO() {
		return new TransactionTypeDAOImpl();
	}

}
