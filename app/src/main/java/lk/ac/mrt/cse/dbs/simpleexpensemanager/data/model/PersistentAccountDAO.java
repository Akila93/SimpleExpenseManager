package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model;

import java.util.List;
import android.database.*;

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
        boolean myBool = true;
        for(Account i:acc_list){
            if(i.getAccountNo() == account.getAccountNo()){
                myBool = false;
            }
        }
        if(myBool)
            this.acc_list.add(account);
        this.mydatabase = openOrCreateDatabase("130085P",MODE_PRIVATE,null);
        String sqlQ ="INSERT INTO Accounts VALUES("+account.getAccountNo()+","+account.getBankName()+","+account.getAccountHolderName()+","+account.getBalance()+");";
        mydatabase.execSQL(sqlQ);
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        if(accountNo == null){return;}
        //
        this.mydatabase = openOrCreateDatabase("130085P",MODE_PRIVATE,null);
        String sqlQ ="DELETE * from Accounts where accountNo="+account.getAccountNo()+"&bankName="+account.getBankName()+"&accountHolderName="+account.getAccountHolderName()+"&balance="+account.getBalance()+";";
        mydatabase.execSQL(sqlQ);
        for(Account i:acc_list){
            if(i.getAccountNo() == accountNo){
                this.acc_list.remove(i);
            }
        }
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {

    }
}
