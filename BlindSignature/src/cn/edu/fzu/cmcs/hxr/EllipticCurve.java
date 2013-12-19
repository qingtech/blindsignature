package cn.edu.fzu.cmcs.hxr;

public class EllipticCurve {
	
	int a,b,p,ord;
	int xg,yg,ordg;
	public static void main(String[] args){
		EllipticCurve ec = new EllipticCurve();
		Point point = new Point(5,1);
		for(int i=1;i<30;i++){
			Point tp = ec.multiply(i, point);
			System.out.println(i+"G = "+tp);
		}
	}
	public EllipticCurve(){
		a = 2;
		b = 2;
		p = 17;
		ord = 19;
		xg = 5;
		yg = 1;
		ordg = 19;
	}
	public Point multiply(int n, Point px){
		Point tp = px;
		for(int i=1;i<n;i++){
			tp = add(tp,px);
		}
		return tp;
	}
	public Point add(Point p1, Point p2){
		Point px = new Point(true);
		//当p1或p2为本原元时
		if(p1.isE()) return p2;
		if(p2.isE()) return p1;
		int s;
		if(p1.getX() == p2.getX()){
			//p1与p2互逆
			if(p1.getY() == (p - p2.getY()) % p) return px;
			//p1与p2不互逆
			//System.out.println("y1="+p1.getY()+",y2="+p2.getY());
			s = ((3*p1.getX()*p1.getX()+a)*getReverse(2*p1.getY()))%p;
			//s = ((3*p1.getX()*p1.getX()+a)/(2*p1.getY()))+100000*p;
		}else{
			s = (p2.getY()-p1.getY())*getReverse(p2.getX()-p1.getX()+p)%p;
			//s = (p2.getY()-p1.getY())/(p2.getX()-p1.getX())+100000*p;
		}
		px.setE(false);
		px.setX((s*s-p1.getX()-p2.getX()+100000*p)%p);
		px.setY((s*(p1.getX()-px.getX())-p1.getY()+100000*p)%p);
		return px;
	}
	public int getReverse(int x){
		int xx = x % p;
		int i;
		for(i=1;i<p;i++){
			if(xx*i%p == 1) break;
		}
		return i;
	}

}

class Point{
	private boolean isE;
	private int x;
	private int y;
	public Point(int x, int y){
		this.setX(x);
		this.setY(y);
	}
	public Point (boolean isE){
		this.setE(isE);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String str = "O";
		if(!this.isE){
			str = "("+this.getX()+","+this.getY()+")";
		}
		return str;
	}
	public boolean isE() {
		return isE;
	}
	public void setE(boolean isE) {
		this.isE = isE;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
}
