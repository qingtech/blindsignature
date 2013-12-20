package cn.edu.fzu.cmcs.hxr;

import java.math.BigInteger;

public class EllipticCurve {
	
	BigInteger a,b,p,ord;
	BigInteger xg,yg,ordg;
	Point g = null;
	public static void main(String[] args){
		EllipticCurve ec = new EllipticCurve();
		Point point = new Point(new BigInteger("5"),new BigInteger("1"));
		for(int i=1;i<30;i++){
			Point tp = ec.multiply(new BigInteger(i+""), point);
			System.out.println(i+"G = "+tp);
		}
//		BigInteger bi1 = new BigInteger("512");
//		BigInteger bi2 = new BigInteger("7");
//		System.out.println("length:"+ bi1.bitLength());
//		System.out.println("count:"+ bi1.bitCount());
//		for(int i=bi1.bitLength()-1; i>=0;i--){
//			if(bi1.testBit(i))
//				System.out.print("1");
//			else
//				System.out.print("0");
//		}
		//System.out.println(bi1.mod(bi2));
		//System.out.println(bi1.modInverse(bi2));
	}
	public EllipticCurve(){
		a = new BigInteger("2");
		b = new BigInteger("2");
		p = new BigInteger("17");
		ord = new BigInteger("19");
		xg = new BigInteger("5");
		yg = new BigInteger("1");
		ordg = new BigInteger("19");
		g = new Point(xg,yg);
	}
	public void showAllPoint(){
		Point point = new Point(new BigInteger("5"),new BigInteger("1"));
		for(int i=1;i<21;i++){
			Point tp = this.multiply(new BigInteger(i+""), point);
			System.out.printf("%02dG=%-7s  ",i,tp);
			if(i%7==0) System.out.println();
		}
		System.out.println();
	}
	public Point multiply(BigInteger n, Point px){
		Point tp = px;
		//System.out.println(n+":"+new String(nc));
		for(int i=n.bitLength()-2;i >= 0;i--){
			tp = add(tp,tp);
			if(n.testBit(i)){
				tp = add(tp,px);
			}
		}
		return tp;
	}
	public Point add(Point p1, Point p2){
		Point p3 = new Point(true);
		//当p1或p2为本原元时
		if(p1.isE()) return p2;
		if(p2.isE()) return p1;
		BigInteger s;
		BigInteger bi2 = new BigInteger("2");
		BigInteger bi3 = new BigInteger("3");
		if(p1.getX().equals(p2.getX())){
			//p1与p2互逆
			
			if(p1.getY().equals(getAddReverse(p2.getY(), p))) return p3;
			//p1与p2不互逆
			s = bi3.multiply(p1.getX().pow(2)).add(a);
			s = s.multiply(bi2.multiply(p1.getY()).modInverse(p)).mod(p);
		}else{
			s = p2.getY().add(getAddReverse(p1.getY(),p));
			BigInteger t = p2.getX().add(getAddReverse(p1.getX(),p));
			//System.out.println(t);
			t = t.modInverse(p);
			s = s.multiply(t).mod(p);
		}
		p3.setE(false);
		//x3 = s*s-x1-x2 mod p
		//ss = s*s
		BigInteger ss = s.pow(2);
		// x12 = -x1-x2
		BigInteger x12 = getAddReverse(p1.getX(),p).add(getAddReverse(p2.getX(),p));
		// x3 = ss+x12 mod p
		p3.setX(ss.add(x12).mod(p));
		//px.setY((s*((p1.getX()-px.getX())+p)-p1.getY())%p);
		//y3 = s*(x1-x3)-y1 mod p
		//x13 = x1-x3
		BigInteger x13 = p1.getX().add(getAddReverse(p3.getX(),p));
		//ry1 = -y1
		BigInteger ry1 = getAddReverse(p1.getY(),p);
		//y3 = s*x13+ry1 mod p
		p3.setY(s.multiply(x13).add(ry1).mod(p));
		return p3;
	}
	public Point getReverse(Point point){
		if(point.isE()) return point;
		
		return new Point(point.getX(),getAddReverse(point.getY(),p));
	}
	public BigInteger getAddReverse(BigInteger x, BigInteger p){
		return p.subtract(x.mod(p)).mod(p);
	}

}

class Point{
	private boolean isE;
	private BigInteger x;
	private BigInteger y;
	public Point(BigInteger x, BigInteger y){
		this.setX(x);
		this.setY(y);
		this.setE(false);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String str = "O";
		if(!this.isE()){
			str = "("+x.toString()+","+y.toString()+")";
		}
		return str;
	}
	public boolean same(Point point){
		if(this.isE && point.isE) return true;
		return this.x.equals(point.getX())&&this.y.equals(point.getY());
	}
	public Point (boolean isE){
		this.setE(isE);
	}
	public boolean isE() {
		return isE;
	}
	public void setE(boolean isE) {
		this.isE = isE;
	}
	public BigInteger getX() {
		return x;
	}
	public void setX(BigInteger x) {
		this.x = x;
	}
	public BigInteger getY() {
		return y;
	}
	public void setY(BigInteger y) {
		this.y = y;
	}
	
	
}
