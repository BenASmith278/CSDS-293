import java.util.*;

class AssignmentOne {
    static <T extends Comparable<? super T>> List<T> longestHigherSuffix(List<T> a, List<T> b, Comparator<T> cmp) {
        List<T> result = new ArrayList<T>();
        ListIterator<T> aIter = a.listIterator(a.size());
        ListIterator<T> bIter = b.listIterator(b.size());

        while (aIter.hasPrevious() && bIter.hasPrevious()) {
            T aVal = aIter.previous();
            T bVal = bIter.previous();

            if (cmp.compare(aVal, bVal) >= 0) {
                result.add(0, aVal);
            } else {
                break;
            }
        }

        return result;
    }
}