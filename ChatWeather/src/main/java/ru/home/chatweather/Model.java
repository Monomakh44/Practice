package ru.home.chatweather;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Model {
        String name;
        Double temp;
        Double temp_max;
        Double temp_min;
        Double humidity;
        Double speed;
        Double visibility;
        String icon;
        String description;
        String main;
        public String toString(){return "faf";}
}
