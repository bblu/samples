package com.zoomway.patrol.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "zw_line")
public class Line {
    @Id private int id;
    private String name;
    private int voltage;
}
