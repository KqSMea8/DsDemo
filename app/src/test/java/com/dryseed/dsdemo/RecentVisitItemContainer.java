package com.dryseed.dsdemo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.ListIterator;
import java.util.Map;

/**
 * @author caiminming
 */
public class RecentVisitItemContainer extends LinkedHashMap<String, Object> {
    public RecentVisitItemContainer() {
        super(16, (float) 0.75, true);
    }

    @Override
    protected boolean removeEldestEntry(Entry<String, Object> eldest) {
        if (size() > 3)
            return true;
        else
            return false;
    }

    @Test
    public void test() {
        RecentVisitItemContainer container = new RecentVisitItemContainer();
        container.put("1", "1");
        container.put("2", "2");
        container.put("3", "3");
        container.put("4", "4");
        container.put("1", "1");

        ListIterator<Entry<String, Object>> i = new ArrayList<Entry<String, Object>>(container.entrySet()).listIterator(container.size());
        while (i.hasPrevious()) {
            Map.Entry<String, Object> entry = i.previous();
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

        /**
         * 3:3
         * 2:2
         * 1:1
         */
    }

}
