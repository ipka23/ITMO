public class Main
{
    static float randomNum()
    {
        return (float) (Math.random() * 24 - 9);
    }
    static void printRes(double[][] z1)
    {
        for (int line = 0; line < z1.length; line++)
        {
            for (int row = 0; row < z1[line].length; row++)
            {
                System.out.printf("%7.2f", z1[line][row]);
            }
            System.out.println();
        }
    }
    public static void main(String[] args)
    {
        double[][] z1 = new double[7][17];
        float[] x = new float[17];
        short[] z = new short[(17-5)/2+1];
        short s = 5;
        int i = 0;
        int j = 0;
        while (i < z.length)
        {
            z[i] = s;
//            System.out.println(z[i]);
            s += 2;
            i++;
        }
        i = 0;
        while (i < x.length)
        {
            x[i] = randomNum();
//            System.out.println(x[i]);
            i++;
        }
        i = 0;
        do
        {
            j = 0;
            do
            {
                float x1 = x[j];
                z1[i][j] = switch ((short) z[i])
                {
                    case 5:
                        yield Math.cbrt(Math.pow(Math.E, Math.asin((x1 + 3) / 24)));
                    case 7, 9, 15:
                        yield Math.cos(Math.pow((2*(Math.cbrt(x1))), 2));
                    default:
                        yield Math.log10(Math.sqrt((Math.acos((Math.cos(x1))))));
                };
//                switch (z[i])
//                {
//                    case 5 -> z1[i][j] = Math.cbrt(Math.pow(Math.E, Math.asin((x1 + 3) / 24)));
//                    case 7, 9, 15 -> z1[i][j] = Math.cos(Math.pow((2*(Math.cbrt(x1))), 2));
//                    default -> z1[i][j] = Math.log10(Math.sqrt((Math.acos((Math.cos(x1))))));
//                }
                j++;
            } while (j < z1[i].length);
            i++;
        } while ((i < z1.length));
        printRes(z1);
    }
}
