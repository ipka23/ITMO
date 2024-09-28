import java.util.Random;
public class Main
{
    static void printRes(double[][] z1)
    {
        for (int i = 0; i < z1.length; i++)
        {
            for (int j = 0; j < z1[i].length; j++)
            {
                System.out.printf("%7.2f", z1[i][j]);
            }
            System.out.println();
        }
    }
    public static void main(String[] args)
    {
        double[][] z1 = new double[7][17];
        Random randNum = new Random();
        float lowLimit = -9.0f;
        float[] x = new float[17];
        short[] z = new short[(17-5)/2+1];
        short s = 5;
        for (int i = 0; i < z.length; i++)
        {
            z[i] = s;
            s += 2;
        }
        for (short item: z)
        {
//            System.out.println(item);
        }
        for (int i = 0; i < x.length; i++)
        {
            x[i] = lowLimit + randNum.nextFloat(15.0f);
        }
        for (float item: x)
        {
//            System.out.println(item);
        }
        for (int i = 0; i < z1.length; i++)
        {
            for (int j = 0; j < z1[i].length; j++)
            {
                float x1 = x[j];
                if(z[i] == 5)
                {
                    z1[i][j] = Math.cbrt(Math.pow(Math.E, Math.asin((x1 + 3) / 24)));
                }
                else if(z[i] == 7 | z[i] == 9 | z[i] == 15)
                {
                    z1[i][j] = Math.cos(Math.pow((2*(Math.cbrt(x1))), 2));
                }
                else
                {
                    z1[i][j] = Math.log10(Math.sqrt((Math.acos((Math.cos(x1))))));
                }
            }
        }
        printRes(z1);
    }
}
