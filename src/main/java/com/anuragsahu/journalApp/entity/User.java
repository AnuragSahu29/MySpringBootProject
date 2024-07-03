package com.anuragsahu.journalApp.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexOptions;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "Users")
@Data
public class User {

    @Id
    private ObjectId id;
    @Indexed(unique = true)
    @com.mongodb.lang.NonNull
    private String username;
    @com.mongodb.lang.NonNull
    private String password;
    @DBRef
    private List<JournalEntity> JournalEntries = new ArrayList<>();
    private List<String> roles;




}
