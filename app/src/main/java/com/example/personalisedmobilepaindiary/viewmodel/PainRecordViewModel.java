package com.example.personalisedmobilepaindiary.viewmodel;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.personalisedmobilepaindiary.LocationCount;
import com.example.personalisedmobilepaindiary.entity.PainRecord;
import com.example.personalisedmobilepaindiary.repository.PainRecordRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PainRecordViewModel extends AndroidViewModel {
    private PainRecordRepository painRecordRepository;
    private LiveData<List<PainRecord>> allPainRecords;
    public PainRecordViewModel (Application application) {
        super(application);
        painRecordRepository = new PainRecordRepository(application);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<PainRecord> findByDateFuture(final String date, final String email) {
        return painRecordRepository.findByDateFuture(date, email);
    }
    public LiveData<List<PainRecord>> getAllPainRecords(String email) {
        return painRecordRepository.getAllPainRecords(email);
    }

    public LiveData<List<LocationCount>> getLocationCount(String email){
        return painRecordRepository.getLocationCount(email);
    }
    public void insert(PainRecord painRecord) {
        painRecordRepository.insert(painRecord);
    }
    public void delete(PainRecord painRecord) {
        painRecordRepository.delete(painRecord);
    }
    public void update(PainRecord painRecord) {
        painRecordRepository.updatePainRecord(painRecord);
    }
}
