package cn.edu.fzu.cmcs.hxr;

import java.math.BigDecimal;
import java.math.BigInteger;

public class User {
	private EllipticCurve ec = null;
	private Point c1,c2;
	private Point s;
	BigInteger r;
	Point m = null;
	Signer signer;
	public static void main(String[] args){
		User user = new User();
		user.process();
	}
	public User(){
		ec = new EllipticCurve();
		m = ec.multiply(new BigInteger("3"), ec.g);
		signer = new Signer();
	}
	public void process(){
		//消息盲化
		this.blindMessage();
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
	public void blindMessage(){
		BigDecimal range = new BigDecimal("1023");
		BigDecimal random = new BigDecimal(Math.random());
		r = range.multiply(random).toBigInteger();
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
		Point temp = ec.add(ec.multiply(r.modInverse(ec.ordg), signer.getD1()), ec.getReverse(s));
		if(!temp.same(m)) return false;
		return true;
	}

}
