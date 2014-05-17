using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace BlindSignature_Console
{
    class Program
    {
        static void Main(string[] args)
        {
            Point p1 = new Point(1, 2);
            Point p2 = new Point();
            Console.WriteLine("p1=" + p1);
            Console.WriteLine("p2=" + p2);
            Console.WriteLine("p1.E = " + p1.E);
            Console.WriteLine("p2.E = " + p2.E);
            Console.WriteLine("8=" + Program.getBitString(8));
            Console.WriteLine("39=" + Program.getBitString(39));
            char[] c = Program.getBitString(39).ToArray();
            for (int i = 0; i < c.Length; i++)
            {
                if (c[i] == '1')
                {
                    Console.Write("x");
                }
                else
                {
                    Console.Write("o");
                }
            }
            for (int i = 1; i < 17; i++)
            {
                Console.WriteLine(i + ":" + Program.modInverse(i, 17));
            }
            User user = new User(0);
            user.process();
            string str;
            while ((str = Console.ReadLine()) != null)
            {
                int index = Int32.Parse(str);
                EllipticCurve ec = new EllipticCurve(index);
                Console.WriteLine(ec);
            }
            while (true) ;
        }


        public static long modInverse(long a, long n)
        {
            long i = n, v = 0, d = 1;
            while (a > 0)
            {
                long t = i / a, x = a;
                a = i % x;
                i = x;
                x = d;
                d = v - t * x;
                v = x;
            }
            v %= n;
            if (v < 0) v = (v + n) % n;
            return v;
        }


        public static string getBitString(long l)
        {
            string str = "";
            while (l / 2 > 0)
            {
                str = l % 2 + str;
                l /= 2;
            }
            str = l + str;
            return str;
        }
    }
}
