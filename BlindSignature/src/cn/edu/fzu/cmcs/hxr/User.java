package cn.edu.fzu.cmcs.hxr;

import java.math.BigDecimal;
import java.math.BigInteger;

public class User {
	private EllipticCurve ec = null;
	private Point c1,c2;
	public static void main(String[] args){
		User user = new User();
		Point m = user.ec.multiply(new BigInteger("3"), user.ec.g);
		user.messageBlind(m);
	}
	public User(){
		ec = new EllipticCurve();
	}
	public void messageBlind(Point m){
		BigDecimal range = new BigDecimal("1023");
		BigDecimal random = new BigDecimal(Math.random());
		BigInteger r = range.multiply(random).toBigInteger();
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

}
