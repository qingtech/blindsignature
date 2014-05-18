using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Numerics;

namespace BlindSignature
{
    class Signer
    {
        //初始私钥 s0
        private long s0;
        //i 时段
        private int i;
        //i时的私钥 si
        private long si;
        //
        private Point ri = null;
        //大素数 q
        private long q;
        //
        private Point d1 = null;
        private Point d2 = null;
        private EllipticCurve ec = null;
        public long S0 { get { return s0; } set { s0 = value; } }
        public long I { get { return i; } }
        public long SI { get { return si; } set { si = value; } }
        public Point RI { get { return ri; } set { ri = value; } }
        public long Q { get { return q; } set { q = value; } }
        public Point D1 { get { return d1; } set { d1 = value; } }
        public Point D2 { get { return d2; } set { d2 = value; } }
        public Signer(EllipticCurve ec)
        {
            s0 = 4;
            q = 23;
            this.ec = ec;
        }
        //产生私钥
        public void generatePrivateKey()
        {
            Random random = new Random();
            //S[i]=S[i-1]^q mod I
            s0 = random.Next((int)ec.ORD);
            i = random.Next(24);
            long I = ec.ORDG;
            si = s0;
            BigInteger tmp_si = new BigInteger(si);
            for(int ii=1;ii<=i;ii++)
            {
                tmp_si = BigInteger.ModPow(tmp_si, q, I);
            }
            si = Int64.Parse(tmp_si.ToString());
            ri = ec.multiply(si, ec.G);
        }
        //盲签名
        public void blindSignature(Point c1, Point c2)
        {
            //d1=(si+1)*c1
            //d2=si*c2
            generatePrivateKey();
            d1 = ec.multiply(si + 1, c1);
            d2 = ec.multiply(si, c2);
        }
    }
}
