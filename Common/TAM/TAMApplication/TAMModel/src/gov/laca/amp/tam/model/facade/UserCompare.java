package gov.laca.amp.tam.model.facade;

import java.util.Comparator;

class UserCompare implements Comparator<String> {

    @Override
    public int compare(String u1, String u2) {
        // write comparison logic here like below , it's just a sample
        String name1 = u1.split("-")[1];
        String name2 = u2.split("-")[1];
        return name1.compareTo(name2);
    }
}
