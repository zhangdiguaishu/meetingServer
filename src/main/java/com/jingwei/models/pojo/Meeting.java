package com.jingwei.models.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Meeting {
    private int index;
    private String topic;
    private String date;
    private String list;
    private String brief;
    private String remark;
    private String username;
    private String location;
}
