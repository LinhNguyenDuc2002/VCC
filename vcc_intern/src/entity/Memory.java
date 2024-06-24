package entity;

import java.util.HashMap;
import java.util.Map;

public class Memory {
    public Integer id;

    public Map<Integer, Student> studentMap;

    public static Memory instance;

    private Memory() {
        this.id = (Integer) 1;
        this.studentMap = new HashMap<>();
    }

    public static Memory getInstance() {
        if (instance == null) {
            instance = new Memory();
        }

        return instance;
    }

}
