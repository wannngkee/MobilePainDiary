package com.example.personalisedmobilepaindiary.repository;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.personalisedmobilepaindiary.dao.PainRecordDAO;
import com.example.personalisedmobilepaindiary.database.PainRecordDatabase;
import com.example.personalisedmobilepaindiary.entity.PainRecord;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class PainRecordRepository {
    private PainRecordDAO painRecordDAO;
    private LiveData<List<PainRecord>> allPainRecords;

    public PainRecordRepository(Application application){
        PainRecordDatabase db = PainRecordDatabase.getInstance(application);
        painRecordDAO = db.painRecordDAO();
        allPainRecords = painRecordDAO.getAll();
    }

    public LiveData<List<PainRecord>> getAllPainRecords(){
        return allPainRecords;
    }

    public void insert(final PainRecord painRecord){
        PainRecordDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                painRecordDAO.insert(painRecord);
            }
        });
    }

    public void delete(final PainRecord painRecord){
        PainRecordDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                painRecordDAO.delete(painRecord);
            }
        });
    }

//    public void isRecordExist(final String date, final String email){
//        PainRecordDatabase.databaseWriteExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                isExist = painRecordDAO.isRecordExist(date,email);
//            }
//        });
//    }

    public void updatePainRecord (final PainRecord painRecord){
        PainRecordDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {

                painRecordDAO.updatePainRecord(painRecord);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<PainRecord> findByDateFuture(final String date, final String email) {
        return CompletableFuture.supplyAsync(new Supplier<PainRecord>() {
            @Override
            public PainRecord get() {
                return painRecordDAO.findByDate(date,email);
            }
        }, PainRecordDatabase.databaseWriteExecutor);
    }

}
