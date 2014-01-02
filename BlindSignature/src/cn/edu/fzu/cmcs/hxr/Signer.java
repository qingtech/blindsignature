package cn.edu.fzu.cmcs.hxr;

import java.math.BigInteger;

public class Signer {
	
	//初始私钥s0
	private BigInteger s0 = null;
	//i时段的私钥si
	private BigInteger si = null;
	private Point ri = null;
	//大素数q
	private int q = 0;
	private EllipticCurve ec = null;
	//i时段
	private int i;
	//
	private Point d1 = null;
	private Point d2 = null;
	public static void main(String[] args){
		Signer signer = new Signer(new EllipticCurve());
		signer.generatePrivateKey();
	}
	public Signer(EllipticCurve ec){
		s0 = new BigInteger("4");
		q = 7;
		this.ec = ec;
	}
	//产生私钥
	public void generatePrivateKey(){
		//S[i]=S[i-1]^q mod I
		i = (int)(24*Math.random());
		BigInteger I = ec.ordg;
		si = s0;
		for(int ii=1;ii<=i;ii++){
			si = si.pow(q).mod(I);
		}
		ri = ec.multiply(si, ec.g);
		if(DebugTool.DEBUG){
			System.out.println("s"+i+"="+si);
			System.out.println("r"+si+"="+ri);
			ec.showAllPoint();
		}
	}
	//盲签名
	public void blindSignature(Point c1, Point c2){
		//d1=(si+1)c1
		//d2=si*c2
		generatePrivateKey();
		d1 = ec.multiply(si.add(BigInteger.ONE), c1);
		d2 = ec.multiply(si, c2);
	}
	public BigInteger getS0() {
		return s0;
	}
	public void setS0(BigInteger s0) {
		this.s0 = s0;
	}
	public BigInteger getSi() {
		return si;
	}
	public void setSi(BigInteger si) {
		this.si = si;
	}
	public Point getRi() {
		return ri;
	}
	public void setRi(Point ri) {
		this.ri = ri;
	}
	public int getQ() {
		return q;
	}
	public void setQ(int q) {
		this.q = q;
	}
	public EllipticCurve getEc() {
		return ec;
	}
	public void setEc(EllipticCurve ec) {
		this.ec = ec;
	}
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	public Point getD1() {
		return d1;
	}
	public void setD1(Point d1) {
		this.d1 = d1;
	}
	public Point getD2() {
		return d2;
	}
	public void setD2(Point d2) {
		this.d2 = d2;
	}
}
