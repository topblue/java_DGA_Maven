package temp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TestSortListMap {

	public static void main(String[] args) {

		List<StringIntPair> list = new ArrayList<StringIntPair>();

		list.add(new StringIntPair("20150703", 5));
		list.add(new StringIntPair("2015070", 3));
		list.add(new StringIntPair("20150702", 2));
		list.add(new StringIntPair("20150701", 4));
		list.add(new StringIntPair("2015070", 1));

		System.out.println("排序前");
		for (Object o:list) {
			System.out.println(o);
		}

		// 依string排序
		Collections.sort(list,new Comparator<StringIntPair>() {
			public int compare(StringIntPair o1, StringIntPair o2) {
				return o1.getString().compareTo(o2.getString());
			}});
		System.out.println();
		System.out.println("依string排序");
		for (Object o:list) {
			System.out.println(o);
		}

		// 依Integer排序
		Collections.sort(list,
		new Comparator<StringIntPair>() {
			public int compare(StringIntPair o1, StringIntPair o2) {
			return o2.getInteger()-o1.getInteger();
		}});
		System.out.println();
		System.out.println("依Integer排序");
		for (Object o:list) {
			System.out.println(o);
		}
	}

}
class StringIntPair{

	private String string;
	private int integer;

	public StringIntPair(String s, int i) {
		string = s;
		integer = i;
	}

	protected String getString() {
		return string;
	}

	protected int getInteger() {
		return integer;
	}

	public String toString() {
		return string + "\t" + integer;
	}
}
