using System.Numerics;
using System.Runtime.InteropServices;

namespace CollatzLib;

public static class CollatzGenerator
{
    [UnmanagedCallersOnly(EntryPoint = "GenerateCollatzData")]
    public static unsafe void GenerateCollatzData(
        int* buffer,
        int width,
        int height,
        double xMin,
        double xMax,
        double yMin,
        double yMax,
        int maxIter)
    {
        double dx = (xMax - xMin) / width;
        double dy = (yMax - yMin) / height;

        Parallel.For(0, height, y =>
        {
            double im = yMin + y * dy;

            for (int x = 0; x < width; x++)
            {
                double re = xMin + x * dx;
                Complex z = new Complex(re, im);
                int iterations = 0;

                while (iterations < maxIter)
                {
                    if (z.Magnitude > 1000)
                        break;

                    Complex cosPart = Complex.Cos(Math.PI * z);
                    Complex term1 = 2.0 + 7.0 * z;
                    Complex term2 = (2.0 + 5.0 * z) * cosPart;

                    z = 0.25 * (term1 - term2);

                    iterations++;
                }

                buffer[y * width + x] = iterations;
            }
        });
    }
}