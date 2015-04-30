package temp;

import java.util.ArrayList;

public class TestTermTest {

	String exDomain = "fpdownload2.macromedia.com.031--ABCDqweU.com";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestTermTest ttt = new TestTermTest();
//		ttt.meachText();
//		ttt.character();
		ttt.domainSplit();
	}
	void testArray(){
		String str = "[no, ol, lo, og, go, o1, 10, 09, 93, 3., .c, co, om, o0, 00, 94, 4., .n, ne, et, 91, 1., .o, or, rg, re, ea, al, l-, -n, ew, ws, sl, li, if, fe, e., ms, sn, n., j., .m, ma, ax, xm, mi, in, nd, d., va, le, er, ri, ie, em, me, es, se, ed, da, ah, hl, l., rt, ti, ni, io, os, st, to, o., ww, w., .g, oo, gl, w4, 41, .l, ld, ar, rk, k., w2, 2., .x, x3, 3x, x4, .s, su, im, .w, wi, do, ow, s., po, rn, o-, -v, vi, id, de, eo, -f, fr, ee]";
		ArrayList<String> array = new ArrayList<String>();
	}
	
	void domainSplit(){
		String domain = "nologo1093.com";
		System.out.println(domain);
		String[] dl = domain.split("");
		for(int i=0;i<(dl.length-1);i++){
			System.out.println(dl[i]+"=>"+dl[i+1]);
		}
		System.out.println(dl.length);
		
	}
	
	void meachText(){
		String en = "[a-zA-Z]";
		String re = "[aeiouAEIOU]";
		String re2 = "[^aeiouAEIOU]";
		int all = this.exDomain.length();
		System.out.println("exDomain:"+exDomain);
		for(String exd:this.exDomain.split("")){
			if(exd.matches(re)){
				System.out.println("小寫："+exd);
			}
			if(exd.matches(re2) && exd.matches(en)){
				System.out.println("\t不含："+exd);
			}
		}
	}
	void character(){
		String str1 = "www.i-gamEr131.net";
		String str2 = "www.i-gamEr131.net";
		System.out.println(str1.toUpperCase());
		System.out.println(str2.toUpperCase());
//		System.out.println(str1 == str1);
//		System.out.println(str1.toUpperCase()==str2);
		System.out.println(str1.toUpperCase()+","+str2.toUpperCase()+"_\t_"+str1.toUpperCase().equals(str2.toUpperCase()));
	}
}
