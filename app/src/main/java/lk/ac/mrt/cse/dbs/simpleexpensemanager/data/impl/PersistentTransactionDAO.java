package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

/**
 * Created by Ak on 12/6/2015.
 */
public class PersistentTransactionDAO implements TransactionDAO {
    private SQLiteDatabase mydatabase = null;
    List<Transaction> transactionList = null;
    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        this.mydatabase = openOrCreateDatabase("130085P", Context.MODE_PRIVATE,null);
        String sqlQ ="INSERT INTO Transactions VALUES("+date+","+accountNo+","+expenseType+","+amount+");";
        mydatabase.execSQL(sqlQ);

    }
    public PersistentTransactionDAO(){
        if(SQLiteDatabase.openDatabase("130085P", null, Context.MODE_PRIVATE)==null){
            SQLiteDatabase.openOrCreateDatabase("130085P", null);
            String tempString = "Create table Transactions (date_in date ,accountNo varchar(20),expenseType varchar(20),amount double)";
            mydatabase.execSQL(tempString);
        }
        this.mydatabase = SQLiteDatabase.openOrCreateDatabase("130085P", Context.MODE_PRIVATE, null );
    }
    @Override
    public List<Transaction> getAllTransactionLogs() {
        this.transactionList = new ArrayList<Transaction>();
        String sqlQ ="Select * from Transactions;";
        this.mydatabase = openOrCreateDatabase("130085P",Context.MODE_PRIVATE,null);
        Cursor resultSet = this.mydatabase.rawQuery(sqlQ,null);
        resultSet.moveToFirst();
        Date date = new Date();
        date.setTime(Date.parse(resultSet.getString(1)));
        String accountNo = resultSet.getString(2);
        ExpenseType expenseType = (resultSet.getString(3) == ExpenseType.EXPENSE.toString())?ExpenseType.EXPENSE:ExpenseType.INCOME;
        double amount = Double.parseDouble(resultSet.getString(4));
        Transaction temp = new Transaction(date,accountNo,expenseType,amount);
        this.transactionList.add(temp);
        while (resultSet.moveToNext()){
            date = new Date();
            date.setTime(Date.parse(resultSet.getString(1)));
            accountNo = resultSet.getString(2);
            expenseType = (resultSet.getString(3) == ExpenseType.EXPENSE.toString())?ExpenseType.EXPENSE:ExpenseType.INCOME;
            amount = Double.parseDouble(resultSet.getString(4));
            temp = new Transaction(date,accountNo,expenseType,amount);
            this.transactionList.add(temp);
        }
        return this.transactionList;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        List<Transaction> tList = new ArrayList<Transaction>();
        String sqlQ ="Select * from (Select * from Transaction order by date_in desc) where ROWNUM <= "+limit+";";
        this.mydatabase = openOrCreateDatabase("130085P",Context.MODE_PRIVATE,null);
        Cursor resultSet = this.mydatabase.rawQuery(sqlQ,null);
        resultSet.moveToFirst();
        Date date = new Date();
        date.setTime(Date.parse(resultSet.getString(1)));
        String accountNo = resultSet.getString(2);
        ExpenseType expenseType = (resultSet.getString(3) == ExpenseType.EXPENSE.toString())?ExpenseType.EXPENSE:ExpenseType.INCOME;
        double amount = Double.parseDouble(resultSet.getString(4));
        Transaction temp = new Transaction(date,accountNo,expenseType,amount);
        tList.add(temp);
        while (resultSet.moveToNext()){
            date = new Date();
            date.setTime(Date.parse(resultSet.getString(1)));
            accountNo = resultSet.getString(2);
            expenseType = (resultSet.getString(3) == ExpenseType.EXPENSE.toString())?ExpenseType.EXPENSE:ExpenseType.INCOME;
            amount = Double.parseDouble(resultSet.getString(4));
            temp = new Transaction(date,accountNo,expenseType,amount);
            tList.add(temp);
        }
        return this.transactionList;
    }
}
