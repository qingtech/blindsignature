package cn.edu.fzu.cmcs.hxr;

import java.math.BigDecimal;
import java.math.BigInteger;

public class User {
	private EllipticCurve ec = null;
	private Point c1,c2;
	private Point s;
	private BigInteger r;
	private Point m = null;
	private Point mm = null;
	private Signer signer;
	public static void main(String[] args){
		User user = new User(0);
		user.process();
	}
	public User(int index){
		ec = new EllipticCurve(index);
		m = ec.multiply(new BigInteger("3"), ec.g);
		signer = new Signer(ec);
	}
	public void process(){
		//消息盲化
		this.blindMessage(new BigInteger("3"));
		//产生私钥，盲签名
		signer.generatePrivateKey();
		signer.blindSignature(c1, c2);
		//阶盲变换
		this.deblind();
		//验证
		boolean res = this.verify();
		if(res){
			System.out.println("signature successfully!");
		}else{
			System.out.println("signature unsuccessfully!!");
		}
	}
	//盲化消息
	public void blindMessage(BigInteger m_hash){
		
		m = ec.multiply(m_hash, ec.g);
		BigDecimal range = new BigDecimal("1023");
		BigDecimal random = new BigDecimal(Math.random());
		r = range.multiply(random).toBigInteger();
		//r = new BigInteger("100");
//		System.out.println("range="+range);
//		System.out.println("random="+random);
//		System.out.println("r="+r);
		 //c1 =rm                             
		 //c2=rg+m
		c1 = ec.multiply(r, m);
		c2 = ec.add(ec.multiply(r, ec.g), m);
		if(DebugTool.DEBUG){
			System.out.println((r.multiply(new BigInteger("3")).mod(ec.ordg))+"..c1="+c1);
			System.out.println((r.add(new BigInteger("3")).mod(ec.ordg))+"..c2="+c2);
			ec.showAllPoint();
		}
	}
	//解盲变换
	public void deblind(){
		// s=d2—ri*r
		s = ec.add(signer.getD2(), ec.getReverse(ec.multiply(r,signer.getRi())));
	}
	//验证过程
	public boolean verify(){
		//d1*r^-1—s = m
		mm = ec.add(ec.multiply(r.modInverse(ec.ordg), signer.getD1()), ec.getReverse(s));
		return mm.same(m);
	}
	public EllipticCurve getEc() {
		return ec;
	}
	public void setEc(EllipticCurve ec) {
		this.ec = ec;
	}
	public Point getC1() {
		return c1;
	}
	public void setC1(Point c1) {
		this.c1 = c1;
	}
	public Point getC2() {
		return c2;
	}
	public void setC2(Point c2) {
		this.c2 = c2;
	}
	public Point getS() {
		return s;
	}
	public void setS(Point s) {
		this.s = s;
	}
	public BigInteger getR() {
		return r;
	}
	public void setR(BigInteger r) {
		this.r = r;
	}
	public Point getM() {
		return m;
	}
	public void setM(Point m) {
		this.m = m;
	}
	public Point getMm() {
		return mm;
	}
	public void setMm(Point mm) {
		this.mm = mm;
	}
	public Signer getSigner() {
		return signer;
	}
	public void setSigner(Signer signer) {
		this.signer = signer;
	}

}
