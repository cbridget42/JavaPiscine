package Day00.Ex00;

public class Program {
    public static void main(String[] args) {
        int x = 479598;
        int res = 0;
        res += x % 10;
        x /= 10;
        res += x % 10;
        x /= 10;
        res += x % 10;
        x /= 10;
        res += x % 10;
        x /= 10;
        res += x % 10;
        x /= 10;
        res += x % 10;
        System.out.println(res);
    }
}
