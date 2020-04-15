package study.data_type;

public class BasicData {

    public static void main(String[] args) {
        // byte
//        byteDemo();
//        floatDemo();
        referenceOrValue();
    }

    public static void byteDemo() {
        byte i = 127;
        byte j = -128;
        //01111111
        System.out.println(Integer.toBinaryString(i));


    }

    public static void floatDemo() {
        float i = 0.09f;
        //1035489772
        //0 01111011 01110000101000111101100
        //符号位  e    尾数
        System.out.println(Float.floatToRawIntBits(i));
        //0.09 非精确值默认会四舍五入
        System.out.println(i);
        double j = i;
        //转换后原近似值是可以除尽的不四舍五入 0.09000000357627869
        System.out.println(j);
        double e = 0.09;
        //0.09 非精确值默认会四舍五入
        System.out.println(e);

        float k = 0.5f;
        //0.5 1/2 可以精确表达所以不会转成近似值
        System.out.println(k);
        double r = k;
        //0.5 1/2 可以精确表达所以不会转成近似值
        System.out.println(r);
    }

    public static void referenceOrValue() {

    }

}
