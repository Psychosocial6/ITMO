import static java.lang.Math.*;
public class Lab {

    public static void main(String[] args) {
        int[] z = new int[8];
        float[] x = new float[11];
        double[][] z1 = new double[8][11];
        // 1
        int f = 18;
        for (int i = 0; i < 8; i++) {
            z[i] = f;
            f -= 2;
        }

        //2
        for (int i = 0; i < 11; i++) {
            double r = Math.random();
            x[i] = (float) (r * 6 - 2);
        }

        // 3
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 11; j++) {
                z1[i][j] = count(z[i], x[j]);
            }
        }

        print(z1);
    }

    public static double count(int z, float x) {
        double ret;
        if (z == 18) {
            ret = pow(pow(2.0D / 3 + tan(x), 3), (pow(pow((1.0D / 3) / (x + 1), 2), asin((x - 1) / 6)) - 1.0D / 3) / atan(cos(x)));
        }
        else if (z == 4 || z == 10 || z == 14 || z == 16) {
            ret = pow(pow(cos(x), pow((1.0D / 4) / (x - 1), 3) * (2 + pow(x, x / PI))), pow(2.0D / exp(x), tan(x)) / (cbrt(sin(x)) + 1.0D / 2));
        }
        else {
            ret = sin(exp(pow((4.0D / 3) / (4.0D / x), 3)));
        }
        return ret;
    }

    public static void print(double[][] z) {
        /*
        int maxLength = 0;
        for (int i = 0; i < z.length; i++) {
            for (int j = 0; j < z[0].length; j++) {
                String s = String.format("%.4f", z[i][j]);
                maxLength = max(s.length(), maxLength);
            }
        }

        for (int i = 0; i < z.length; i++) {
            System.out.print("| ");
            for (int j = 0; j < z[0].length; j++) {
                System.out.printf("%" + maxLength + ".4f", z[i][j]);
                System.out.print(" | ");
            }
            System.out.println();
            System.out.println(("-".repeat(maxLength) + "---").repeat(z[0].length));
        }
        */
        for (int i = 0; i < z.length; i++) {
            for (int j = 0; j < z[0].length; j++) {
                System.out.printf("%.4f", z[i][j]);
                System.out.print("	");
            }
            System.out.println();
        }
    }
}