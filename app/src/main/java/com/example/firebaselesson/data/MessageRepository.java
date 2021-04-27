package com.example.firebaselesson.data;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MessageRepository {
    private static MessageRepository instance;
    private DatabaseReference myRef;
    private MessageLiveData message;

    private MessageRepository(){}

    public static synchronized MessageRepository getInstance() {
        if(instance == null)
            instance = new MessageRepository();
        return instance;
    }

    public void init(String userId) {
        myRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        message = new MessageLiveData(myRef);
    }

    public void saveMessage(String message) {
        myRef.setValue(new Message(message)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println("Data sent successfully");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Unable to send data :(");
                System.out.println(e);
            }
        });
    }

    public MessageLiveData getMessage() {
        return message;
    }
}
