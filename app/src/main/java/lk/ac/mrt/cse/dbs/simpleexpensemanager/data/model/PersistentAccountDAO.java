package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model;

import java.util.List;
//import android.database.sqlite;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Ak on 12/6/2015.
 */
public class PersistentAccountDAO implements AccountDAO{
    List<Account> acc_list;
    private SQLiteDatabse mydatabase;
    @Override
    public List<String> getAccountNumbersList() {
        return null;
    }

    @Override
    public List<Account> getAccountsList() {
        return null;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        return null;
    }

    @Override
    public void addAccount(Account account) {
        if(account == null){return;}
        this.acc_list.add(account);


    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {

    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {

    }
}
