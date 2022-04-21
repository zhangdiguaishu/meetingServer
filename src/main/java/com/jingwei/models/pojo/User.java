package com.jingwei.models.pojo;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String name;
    private String password;
    private String phoneNumber;
    private String emailAddress;
}
