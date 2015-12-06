package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import android.database.*;
//import android.database.sqlite.SQLiteDatabase;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
//import static android.database.sqlite.SQLiteDatabase;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;
//import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;
/**
 * Created by Ak on 12/6/2015.
 */
public class PersistentAccountDAO implements AccountDAO{
    List<Account> acc_list;
    List<String> acc_num;
    private SQLiteDatabse mydatabase;
    @Override
    public List<String> getAccountNumbersList() {
        String sqlQ ="Select accountNo from Accounts;";
        this.acc_num = new ArrayList<String>();
        Cursor resultSet = this.mydatabase.rawQuery(sqlQ,null);
        resultSet.moveToFirst();
        String acc_no = resultSet.getString(1);
        acc_num.add(acc_no);
        if(resultSet.moveToNext()){
            acc_no = resultSet.getString(1);
            acc_num.add(acc_no);
        }

        return acc_num;
    }

    @Override
    public List<Account> getAccountsList() {
        List<String> acc_number = this.getAccountNumbersList();
        this.acc_list = new ArrayList<Account>();
        Iterator<String> i =acc_number.iterator();
        while(i.hasNext()){
            try {
                acc_list.add(this.getAccount(i.next()));
            } catch (InvalidAccountException e) {
                e.printStackTrace();
            }
        };
        return acc_list;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        if(accountNo == null){throw new InvalidAccountException("no AccountNo given!");}

        String sqlQ ="Select * from Accounts where accountNo="+accountNo+";";

        Cursor resultSet = this.mydatabase.rawQuery(sqlQ,null);

        if(resultSet.getCount() == 0){throw new InvalidAccountException("Account does not exist!");}
        resultSet.moveToFirst();
        String acc_No = resultSet.getString(1);
        String b_Name = resultSet.getString(2);
        String acc_H_Name = resultSet.getString(3);
        double balance = Double.parseDouble(resultSet.getString(4));
        return  new Account(acc_No,b_Name,acc_H_Name,balance);
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

        String sqlQ ="Select * from Accounts where accountNo="+accountNo+";";

        this.mydatabase = openOrCreateDatabase("130085P",MODE_PRIVATE,null);

        Cursor resultSet = this.mydatabase.rawQuery(sqlQ,null);

        if(resultSet.getCount() == 0){throw new InvalidAccountException("Account does not exist!");}

        sqlQ ="DELETE * from Accounts where accountNo="+accountNo+";";


        mydatabase.execSQL(sqlQ);

        for(Account i:acc_list){
            if(i.getAccountNo() == accountNo){
                this.acc_list.remove(i);
            }
        }
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        if(accountNo == null){return;}

        String sqlQ ="Select accountBalance from Accounts where accountNo="+accountNo+";";

        Cursor resultSet = this.mydatabase.rawQuery(sqlQ,null);

        if(resultSet.getCount() == 0){throw new InvalidAccountException("Account does not exist!");}
        resultSet.moveToFirst();
        Double balance = Double.parseDouble(resultSet.getString(1));

        sqlQ ="UPDATE * Accounts set accountBalance="+(balance+amount)+" where accountNo="+accountNo+";";



        mydatabase.execSQL(sqlQ);

    }
}
