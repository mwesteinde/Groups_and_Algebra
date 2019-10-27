package groups;

import java.util.*;

public class ProbableGroup {
    Set<String> ourElements = new HashSet<>();
    Map<Pair<String>, String> ourOpTable = new HashMap<>();

    /**
     * <p>Create an instance of ProbableGroup</p>
     *
     * @param elements is not null, not empty and does not contain "" or null
     * @param opTable  is not null and not empty,
     *                 and all pairs of `String`s in elements are keys in opTable
     */
    public ProbableGroup(Set<String> elements, Map<Pair<String>, String> opTable) {
        ourElements = Set.copyOf(elements);
        ourOpTable = Map.copyOf(opTable);
    }


    /**
     * Obtain the elements in an instance of ProbableGroup
     *
     * @return the set of elements in this instance of ProbableGroup
     */
    public Set<String> getElements() {
        Set<String> returnedStringSet = new HashSet<>();
        for (String s: ourElements) {
            returnedStringSet.add(s);
        }
        return returnedStringSet;

    }

    /**
     * Obtain the complete operation table for an instance of ProbableGroup
     *
     * @return the complete operation table for this instance of ProbableGroup
     */
    public Map<Pair<String>, String> getOpTable() {
        Map<Pair<String>, String> returnedMap = new HashMap<>();
        Iterator<Map.Entry<Pair<String>, String>> it = ourOpTable.entrySet().iterator();
        while (it.hasNext()) {
            returnedMap.put(it.next().getKey(), it.next().getValue());
        }
        return returnedMap;
    }

    /**
     * Return a * b
     *
     * @param a is an element of this ProbableGroup
     * @param b is an element of this ProbableGroup
     * @return a * b
     */
    public String product(String a, String b) {
        Pair<String> pairInstance = new Pair<String>(a, b);
        String s = ourOpTable.get(pairInstance);
        return s;
    }

    /**
     * Obtain the identity element of this ProbableGroup
     *
     * @return the identity of the ProbableGroup if it exists, otherwise returns the empty String ("")
     */
    public String getIdentity() {
        String s = "";
        int count = 0;
        for (String str: ourElements) {
            for (String str2 : ourElements) {
                Pair<String> pairInstance = new Pair<String>(str2, str);
                Pair<String> pairInstance1 = new Pair<String>(str, str2);
                if (ourOpTable.get(pairInstance).equals(str2) && ourOpTable.get(pairInstance1).equals(str2)) {
                    count ++;
                    if (count == ourElements.size()) {
                        s = str;
                    }
                } else {
                    break;
                }
            }
        }
        return s;
    }

    /**
     * Obtain the inverse of element a
     *
     * @param a is an element of this ProbableGroup
     * @return the inverse of a, a', such that a * a' = e if it exists, otherwise returns the empty String ("")
     */
    public String getInverse(String a) {
        String s = "";
        for (String str: ourElements) {
            Pair<String> pairInstance = new Pair<String>(a, str);
            if (ourOpTable.get(pairInstance).equals(this.getIdentity())) {
                s = str;
            }
        }

        return s;
    }

    /**
     * Obtain a^n = a * a^(n-1) as defined by the * operation for this ProbableGroup
     *
     * @param a is an element of this ProbableGroup
     * @param n n > 0 and n is the exponent that we would like to raise a to
     * @return a^n
     */
    public String power(String a, int n) {
        String s;
        String nextn = a;
        String next2n;
        for (int i = 1; i < n; i++) {
            Pair<String> pairInstance = new Pair<String>(a, nextn);
            nextn = ourOpTable.get(pairInstance);
        }
        s = nextn;
        return s;
    }

    /**
     * Obtain the order of element a
     * It is provable that for a finite Group the order is always finite
     * // requires: this.isGroup() returns true
     *
     * @param a is an element of this ProbableGroup
     * @return the order of element a in this ProbableGroup
     */
    public int order(String a) {
        int m = 1;
        while (!this.power(a,m).equals(this.getIdentity())) {
            m++;
        }
        return m;
    }

    /**
     * Check if this ProbableGroup is a group
     *
     * @return true if the ProbableGroup is a group, otherwise return false
     */
    public boolean isGroup() {
        for (String s : ourElements) {
            for (String t: ourElements) {
                for (String u: ourElements) {
                    Pair<String> pair1 = new Pair(t, u);
                    String result = ourOpTable.get(pair1);
                    Pair<String> pair2 = new Pair(s, result);
                    String result2 = ourOpTable.get(pair2);
                    Pair<String> pair3 = new Pair(s, t);
                    String result3 = ourOpTable.get(pair3);
                    Pair<String> pair4 = new Pair(result3, u);
                    String result4 = ourOpTable.get(pair4);
                    if (!result2.equals(result4)) {
                        return false;
                    }
                }
            }
        }
        for (String s: ourElements) {
            if (getInverse(s).equals("")) {
                return false;
            }
            for (String t: ourElements) {
                if (!ourElements.contains(product(s,t))) {
                    return false;
                }
            }
        }
        String identityCheck = getIdentity();
        if (identityCheck.equals("")) {
            return false;
        }

        return true;
    }

    /**
     * Check if this ProbableGroup is commutative
     *
     * @return true if the Probable Group is commutative, otherwise return false
     */
    public boolean isCommutative() {
        for (String s: ourElements) {
            for (String t: ourElements) {
                Pair<String> pair1 = new Pair(s,t);
                Pair<String> pair2 = new Pair(t,s);
                if (!ourOpTable.get(pair1).equals(ourOpTable.get(pair2))) {
                    return false;
                }
                if (!ourElements.contains(ourOpTable.get(pair1)) || !ourElements.contains(ourOpTable.get(pair2))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check if a set h is a subgroup of this instance
     *
     * @param h is a set of elements
     * @return true if h is a subgroup of this instance, otherwise return false
     */
    public boolean isSubgroup(Set<String> h) {
        for (String s: h) {
            if (!h.contains(getInverse(s))) {
                return false;
            }
            for (String t: h) {
                Pair<String> thisPair = new Pair (s, t);
                if (!h.contains(ourOpTable.get(thisPair))) {
                    return false;
                }
            }
        }
        return true;
    }
}