package com.lg.jabcmysqldemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *轮播
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WheelPlanting {
    private String name;
    private String title;
    private String url;
    private String imgUrl;
}
