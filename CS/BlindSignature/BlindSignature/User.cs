using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace BlindSignature
{
    class User
    {
        private EllipticCurve ec = null;
        private Point c1, c2;
        private Point s;
        private long r;
        private Point m = null;
        private Point mm = null;
        private Signer signer = null;

        public EllipticCurve EC { get { return ec; } set { ec = value; } }
        public Point C1 { get { return c1; } set { c1 = value; } }
        public Point C2 { get { return c2; } set { c2 = value; } }
        public Point S { get { return s; } set { s = value; } }
        public long R { get { return r; } set { r = value; } }
        public Point M { get { return m; } set { m = value; } }
        public Point MM { get { return mm; } set { mm = value; } }
        public Signer SIGNER { get{return signer;}}
        public User(int index)
        {
            ec = new EllipticCurve(index);
            m = ec.multiply(3, ec.G);
            signer = new Signer(ec);
        }
        public void process(long hash_value)
        {
            this.blindMessage(hash_value);
            signer.blindSignature(c1,c2);
            this.deblind();
            Boolean res = this.verify();
            if (res)
            {
                Console.WriteLine("signature successfully!!");
            }
            else
            {
                Console.WriteLine("signature unsuccessfully!!");
            }
        }
        //盲化消息
        public void blindMessage(long m_hash)
        {
            m = ec.multiply(m_hash, ec.G);
            int range = 1023;
            Random random = new Random();
            r = random.Next(range);
            //*************
            r = 100;
            //*************
            //c1 = rm
            //c2 = rg+m
            c1 = ec.multiply(r, m);
            c2 = ec.add(ec.multiply(r, ec.G), m);
        }
        //解盲变换
        public void deblind()
        {
            //s = d2 - ri*r
            s = ec.add(signer.D2, ec.getInverse(ec.multiply(r, signer.RI)));
        }
        //验证过程
        public bool verify()
        {
            // d1*r^-1 - s = m
            mm = ec.add(ec.multiply(ec.getMulInverse(r,ec.ORDG), signer.D1), ec.getInverse(s));
            return mm.same(m);
        }
    }
}
