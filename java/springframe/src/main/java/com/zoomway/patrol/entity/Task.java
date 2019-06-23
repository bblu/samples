package com.zoomway.patrol.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "zw_task")
public class Task {
    @Id private int id;
    private String name;
    private int userId;
}
