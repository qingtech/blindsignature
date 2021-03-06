package cn.edu.fzu.cmcs.hxr;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Vector;

public class EllipticCurve {
	
	BigInteger a,b,p,ord;
	BigInteger xg,yg,ordg;
	Point g = null;
	//Point[] points = null;
	public static void main(String[] args){
//		EllipticCurve ec = new EllipticCurve();
//		Point point = new Point(new BigInteger("5"),new BigInteger("1"));
//		for(int i=1;i<30;i++){
//			Point tp = ec.multiply(new BigInteger(i+""), point);
//			System.out.println(i+"G = "+tp);
//		}
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
		//EllipticCurve ec = new EllipticCurve(2,2,179);
		
		int index;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str;
		try{
			while((str = br.readLine())!=null){
				index = Integer.parseInt(str);
				EllipticCurve ec = new EllipticCurve(index);
				System.out.println(ec);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
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
		Point point = new Point(new BigInteger("5"),new BigInteger("1"));
//		points = new Point[ord.intValue()];
//		points[0] = point;
//		for(int i=1;i<ord.intValue();i++){
//			points[i] = this.multiply(new BigInteger((i+1)+""), point);
//		}
	}
	public EllipticCurve(int index){
//		（1）10,3,577，(159,500),541
//		（2）11,26,1009，(45,182),1019
//		（3）29,29,5297，(5,915),5297
//		（4）16,24,23929,(2,8),24077
//		（5）13,26,1009,(2,64),1009
//		（6）14,20,3889,(1,423),3907
//		（7）2,5,577,(7,311),607
//		（8）5,1,577,(64,63),577
//		（9）7,10,577,(8,1),601
		int aa[] = {10,11,29,16,13,14,2,5,7};
		int bb[] = {3,26,29,24,26,20,5,1,10};
		int pp[] = {577,1009,5297,23929,1009,3889,577,577,577};
		int xxg[] = {159,45,5,2,2,1,7,64,8};
		int yyg[] = {500,182,915,8,64,423,311,63,1};
		int ordd[] = {541,1019,5297,24077,1009,3907,607,577,601};
		a = new BigInteger(aa[index]+"");
		b = new BigInteger(bb[index]+"");
		p = new BigInteger(pp[index]+"");
		ord = new BigInteger(ordd[index]+"");
		xg = new BigInteger(xxg[index]+"");
		yg = new BigInteger(yyg[index]+"");
		ordg = new BigInteger(ordd[index]+"");
		g = new Point(xg,yg);
		//Point point = g;
//		points = new Point[ord.intValue()];
//		points[0] = point;
//		for(int i=1;i<ord.intValue();i++){
//			points[i] = this.multiply(new BigInteger((i+1)+""), point);
//		}
	}
	public EllipticCurve(int a, int b, int p){
		this.a = new BigInteger(a+"");
		this.b = new BigInteger(b+"");
		this.p = new BigInteger(p+"");
		ord = new BigInteger("19");
		xg = new BigInteger("0");
		yg = new BigInteger("0");
		ordg = new BigInteger("19");
		
		Vector<Point> v = new Vector<Point>();
		while(xg.compareTo(this.p)<0){
			yg = BigInteger.ZERO;
			while(yg.compareTo(this.p)<0){
				if(onLine(xg,yg)){
					g = new Point(xg,yg);
					v.add(g);
				}
				yg = yg.add(BigInteger.ONE);
			}
			xg = xg.add(BigInteger.ONE);
		}
		this.ord = new BigInteger(v.size()+"");
		for(int i=0;i<v.size();i++){
			System.out.printf("%3d:%8s  ",i,v.get(i));
			if((i+1)%10==0) System.out.println();
		}
		for(int i=0;i<v.size();i++){
			g = v.get(i);
			Point t = multiply(ord, g);
			//System.out.println(i+":"+g+":"+t);
			if(t.isE()){
				System.out.println(i+":"+g);
			}
		}
	}
	public boolean onLine(BigInteger x, BigInteger y){
		BigInteger yy = y.multiply(y).mod(p);
		BigInteger xx = x.pow(3).add(x.multiply(a)).add(b).mod(p);
		return yy.equals(xx);
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		//曲线:y^2=x^3+ax+b mod 17  a=2,b=2
		//曲线阶为：19，基点G(5,1),基点的阶I：19
		Point point = g;
		String str = "曲线:y^2=x^3+ax+b mod "+p+"  a="+a+", b="+b+"\n";
		str += " 阶："+ord+"，基点G("+xg+","+yg+"),阶I："+ordg+"\n";
//		for(int i=1;i<ordg.intValue()+2;i++){
//			Point tp = this.multiply(new BigInteger(i+""), point);
//			str += String.format("%02dG=%-7s  ",i,tp);
//			if(i%7==0) str+="\n";
//		}
		return str;
	}
	public String showAllPoint(){
		Point point = new Point(new BigInteger("5"),new BigInteger("1"));
		String str = "";
		for(int i=1;i<21;i++){
			Point tp = this.multiply(new BigInteger(i+""), point);
			str += String.format("%02dG=%-7s  ",i,tp);
			if(i%7==0) str+="\n";
		}
		System.out.println(str);
		return str;
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
		String str = "(O)";
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
