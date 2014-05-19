using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BlindSignature_Console
{
    class EllipticCurve
    {
        long a, b, p, ord;
        long xg, yg, ordg;
        Point g;

        public long A { get { return a; } set { a = value; } }
        public long B { get { return b; } set { b = value; } }
        public long P { get { return p; } set { p = value; } }
        public long ORD { get { return ord; } set { ord = value; } }
        public Point G { get { return g; } set { g = value; } }
        public long ORDG { get { return ordg; } set { ordg = value; } }
        
        //默认构造方法
        public EllipticCurve()
        {
            a = 2;
            b = 2;
            p = 17;
            ord = 19;
            xg = 5;
            yg = 1;
            ordg = 19;
            g = new Point(xg, yg);
        }
        //根据index选取某一条椭圆曲线
        public EllipticCurve(int index)
        {
            if (index < 0 || index > 8)
            {
                index = 0;
            }
            //9
            int[] aa = {10,11,29,16,13,14,2,5,7};
            int[] bb = {3,26,29,24,26,20,5,1,10};
            int[] pp = {577,1009,5297,23929,1009,3889,577,577,577};
            int[] xxg = {159,45,5,2,2,1,7,64,8};
            int[] yyg = {500,182,915,8,64,423,311,63,1};
            int[] ordd = { 541, 1019, 5297, 24077, 1009, 3907, 607, 577, 601 };
            a = aa[index];
            b = bb[index];
            p = pp[index];
            ord = ordd[index];
            xg = xxg[index];
            yg = yyg[index];
            ordg = ordd[index];
            g = new Point(xg, yg);
        }
        //判断某一点是否在椭圆曲线上
        public bool onLine(Point point)
        {
            if (point.E) return true;
            //y^2 = x^3+ax+b mod p
            long yy = this.pow(point.Y,2) % this.p;
            long xx = (this.pow(point.X , 3) + this.a * point.X + this.b) % this.p;
            return xx == yy;
        }
        public long pow(long a, long n)
        {
            long res = 1;
            for (int i = 0; i < n; i++)
            {
                res *= a;
            }
            return res;
        }
        public override string ToString()
        {
            // TODO Auto-generated method stub
            //曲线:y^2=x^3+ax+b mod 17 a=2,b=2
            //曲线阶为：19，基点G(5,1),基点的阶I：19
            String str = "曲线:y^2=x^3+ax+b mod " + p + " a=" + a + ", b=" + b + "\n";
            str += " 阶：" + ord + "，基点G(" + xg + "," + yg + "),阶I：" + ordg + "\n";
            str = String.Format("y^2=x^3+{0}x+{1} mod {2}, G{3},阶:{4}",a,b,p,g,ordg);
            // for(int i=1;i<ordg.intValue()+2;i++){
            // Point tp = this.multiply(new BigInteger(i+""), point);
            // str += String.format("%02dG=%-7s ",i,tp);
            // if(i%7==0) str+="\n";
            // }
            return str;
        }

        public Point multiply(long n, Point point)
        {
            if (n == 0)
            {
                return new Point();
            }
            if (n < 0)
            {
                //do something
            }
            Point npoint = new Point(point);
            char[] bit_n = this.getBitCharArray(n);
            for (int i = 1; i < bit_n.Length; i++)
            {
                npoint = this.add(npoint, npoint);
                if (bit_n[i] == '1')
                {
                    npoint = add(npoint, point);
                }
            }
                return npoint;
        }
        public Point add(Point p1, Point p2)
        {
            if (p1.E) return p2;
            if (p2.E) return p1;
            Point p3 = new Point();
            p3.E = true;
            long s;
            if (p1.X == p2.X)
            {
                //p1与p2互逆
                if (p1.Y == this.getAddInverse(p2.Y))
                {
                    return p3;
                }
                //p1与p2不互逆
                //s = 3*x1^2 + a
                s = 3 * this.pow(p1.X , 2) + this.a;
                //s = s/(2*y1) % p
                s = s * this.getMulInverse(2 * p1.Y) % p;
            }
            else
            {
                //s = y2 - y1
                s = p2.Y + this.getAddInverse(p1.Y);
                // t = 1/(x2 - x1)
                long t = this.getMulInverse(p2.X + this.getAddInverse(p1.X));
                //s = s/(x2-x1) mod p = (y2 - y1)/(x2-x1) mod p
                s = (s*t) % p;
            }
            p3.E = false;
            //x3 = s*s - x1 - x2 mod p
            p3.X = (s * s + this.getAddInverse(p1.X) + this.getAddInverse(p2.X)) % p;
            //y3 = s*(x1-x3) -y1 mod p
            p3.Y = (s * (p1.X + this.getAddInverse(p3.X)) + this.getAddInverse(p1.Y)) % p;
            return p3;
        }
        public Point getInverse(Point point)
        {
            if (point.E) return point;
            return new Point(point.X, this.getAddInverse(point.Y));
        }
        public long getAddInverse(long r)
        {
            return (this.p - r % this.p) % this.p; //当r = p 或 r = 0时
        }
        public long getMulInverse(long a)
        {
            long i = this.p, v = 0, d = 1;
            while (a > 0)
            {
                long t = i / a, x = a;
                a = i % x;
                i = x;
                x = d;
                d = v - t * x;
                v = x;
            }
            v %= this.p;
            if (v < 0) v = (v + this.p) % this.p;
            return v;
        }
        private char[] getBitCharArray(long l)
        {
            string str = "";
            while (l / 2 > 0)
            {
                str = l % 2 + str;
                l /= 2;
            }
            str = l + str;
            return str.ToArray();
        }
    }

    class Point
    {
        private bool e;
        private long x, y;
        public bool E { get { return e; } set { e = value; } }
        public long X { get { return x; } set { x = value; } }
        public long Y { get { return y; } set { y = value; } }
        public Point()
        {
            this.e = true;
        }
        public Point(long x, long y)
        {
            this.x = x;
            this.y = y;
            this.e = false;
        }
        public Point(Point point)
        {
            if (point.E)
            {
                this.e = true;
                return;
            }
            this.x = point.X;
            this.y = point.Y;
        }
        public override string ToString()
        {
            string str = "(O)";
            if (!this.e)
            {
                str = String.Format("({0},{1})", this.x, this.y);
            }
            return str;
        }
        public bool same(Point point)
        {
            if (this.E && point.E) return true;
            return (this.X == point.Y) && (this.Y == point.Y);
        }
    }
}
