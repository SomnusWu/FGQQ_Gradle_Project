package com.finddreams.sortedcontact;

import java.util.Comparator;

import com.finddreams.sortedcontact.sortlist.PersonSortMode;
import com.finddreams.sortedcontact.sortlist.SortModel;

public class PersonPinyinComarator implements Comparator<PersonSortMode>{

	@Override
	public int compare(PersonSortMode o1, PersonSortMode o2) {
		// TODO Auto-generated method stub
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
