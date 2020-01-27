package com.tenserflow.therapist.Utils;

import android.support.v7.util.DiffUtil;

import com.tenserflow.therapist.fragment.BookingHistory;
import com.tenserflow.therapist.model.Booking_history.Booking_History;
import com.tenserflow.therapist.model.Booking_history.HistoryArray;

import java.util.ArrayList;
import java.util.List;

public class MyDiffCallback extends DiffUtil.Callback{

    ArrayList<HistoryArray> oldPersons;
    ArrayList<HistoryArray> newPersons;

    public MyDiffCallback(ArrayList<HistoryArray> newPersons, ArrayList<HistoryArray> oldPersons) {
        this.newPersons = newPersons;
        this.oldPersons = oldPersons;
    }

    @Override
    public int getOldListSize() {
        return oldPersons.size();
    }

    @Override
    public int getNewListSize() {
        return newPersons.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldPersons.get(oldItemPosition) == newPersons.get(newItemPosition);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldPersons.get(oldItemPosition).equals(newPersons.get(newItemPosition));
    }


    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        //you can return particular field for changed item.
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }


    //------------------------add in adapter and call in activity------------------
     /*public void updateEmployeeListItems(ArrayList<HistoryArray> employees) {
        final MyDiffCallback diffCallback = new MyDiffCallback(this.historyArray, employees);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        Log.e("rvedcedvc","vrcdrfedvc");
        Log.e("rvedcedvc",this.historyArray.size()+"  vrcdrfedvc");
        Log.e("rvedcedvc",employees.size()+"   vrcdrfedvc");
        this.historyArray.clear();
        this.historyArray.addAll(employees);
        diffResult.dispatchUpdatesTo(this);
    }*/

}
