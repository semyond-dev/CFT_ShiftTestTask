package org.test.tools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StringIteratorMerger implements Iterator<String> {

    private final List<Iterator<String>> iteratorList = new ArrayList<>();
    private int iteratorLastCheckedIndex = 0;
    private Iterator<String> currentIterator = null;

    public void addIterator(Iterator<String> source) {
        iteratorList.add(source);
    }

    @Override
    public boolean hasNext() {
        if (currentIterator == null) {
            currentIterator = iteratorList.get(iteratorLastCheckedIndex);
        }

        if (currentIterator.hasNext()) {
            return true;
        } else {
            if (iteratorLastCheckedIndex != iteratorList.size() - 1) {
                iteratorLastCheckedIndex++;
                currentIterator = iteratorList.get(iteratorLastCheckedIndex);
                return currentIterator.hasNext();
            }
        }
        return false;
    }

    @Override
    public String next() {
        if (currentIterator == null) {
            currentIterator = iteratorList.get(iteratorLastCheckedIndex);
        }

        if (currentIterator.hasNext()) {
            return currentIterator.next();
        } else {
            if (iteratorLastCheckedIndex != iteratorList.size() - 1) {
                iteratorLastCheckedIndex++;
                currentIterator = iteratorList.get(iteratorLastCheckedIndex);
                if (currentIterator.hasNext()) {
                    return currentIterator.next();
                }
            }
        }
        return "";
    }

}
