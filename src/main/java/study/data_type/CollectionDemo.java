package study.data_type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CollectionDemo {

    public static void main(String[] args) {
//        listDemo();
        mapDemo();
    }

    private static void listDemo() {
        List<Integer> empty = new ArrayList<Integer>();
        //
        System.out.println(empty.size());
//        empty.set(0,1);
//        System.out.println(empty.size());
        empty.add(1);
        // 1
        System.out.println(empty.size() + " ");
        empty.set(0,2);
        // 2
        System.out.println(empty);
        // IndexOutOfBoundsException
//        empty.set(1,2);
//        System.out.println(empty);

        for (int i = 0; i <= 10; i++) {
            empty.add(i);
        }
    }

    private static void mapDemo() {
        Map<Integer,Integer> map = new HashMap<Integer, Integer>();
        System.out.println(map.size());

        map.put(1,1);
        System.out.println(map.size());
        for (int i = 0; i <= 17; i++) {
            //
            map.put(i,i);
        }

        Map<Integer, Integer> conMap = new ConcurrentHashMap<>(1);
        //时间比较紧，后面补上demo
    }
}
