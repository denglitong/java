package com.denglitong.apache_commons_demo;

import org.apache.commons.lang3.Range;

import java.util.Comparator;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/20
 */
public class RangeDemo {

    public static void main(String[] args) {
        Range<Score> scoreRange = Range.between(new Score(1), new Score(10)); // [1, 10]

        System.out.println(scoreRange.getMaximum()); // Score{value=10}
        System.out.println(scoreRange.getMinimum()); // Score{value=1}

        Range<Score> scoreRange1 = Range.is(new Score(5)); // [5, 5]
        System.out.println(scoreRange1.getMaximum()); // Score{value=5}
        System.out.println(scoreRange1.getMinimum()); // Score{value=5}

        System.out.println(scoreRange1.contains(new Score(5))); // true
        System.out.println(scoreRange1.contains(new Score(4))); // false
        System.out.println(scoreRange1.contains(new Score(6))); // false

        System.out.println(scoreRange.containsRange(scoreRange1)); // true
        System.out.println(scoreRange1.containsRange(scoreRange)); // false

        Score scoreOne = new Score(1), scoreTen = new Score(10), scoreFive = new Score(5);
        System.out.println(scoreRange.elementCompareTo(scoreOne)); // 0
        System.out.println(scoreRange.elementCompareTo(scoreTen)); // 0
        System.out.println(scoreRange1.elementCompareTo(scoreOne)); // -1
        System.out.println(scoreRange1.elementCompareTo(scoreTen)); // 1
        System.out.println(scoreRange1.elementCompareTo(scoreFive)); // 0

        System.out.println(scoreRange.equals(scoreRange1)); // false
        System.out.println(scoreRange.equals(Range.between(scoreOne, scoreTen))); // true

        Comparator<Score> comparator = scoreRange.getComparator();
        System.out.println(comparator);
        System.out.println(comparator.compare(scoreFive, scoreTen)); // -5
        System.out.println(comparator.reversed().compare(scoreFive, scoreTen)); // 5

        System.out.println(scoreRange.hashCode());
        System.out.println(scoreRange1.hashCode());

        Range<Score> scoreRange2 = Range.between(new Score(6), new Score(12));
        Range<Score> intersection = scoreRange.intersectionWith(scoreRange2);
        System.out.println(intersection); // [Score{value=6}..Score{value=12}]
        System.out.println(intersection.getMinimum() + " " + intersection.getMaximum());

        System.out.println(scoreRange1.isAfter(scoreTen)); // false
        System.out.println(scoreRange2.isAfter(scoreOne)); // true
        System.out.println(intersection.isAfterRange(scoreRange1)); // true
        System.out.println(scoreRange.isAfterRange(intersection)); // false

        System.out.println(scoreRange1.isBefore(scoreTen)); // true
        System.out.println(scoreRange1.isBefore(scoreOne)); // false
        System.out.println(intersection.isBeforeRange(scoreRange1)); // false
        System.out.println(Range.between(scoreOne, scoreFive).isBeforeRange(intersection)); // true

        System.out.println(scoreRange.isEndedBy(scoreTen)); // true
        System.out.println(scoreRange1.isEndedBy(scoreFive)); // true
        System.out.println(intersection.isEndedBy(scoreTen)); // true

        System.out.println(scoreRange.isStartedBy(scoreOne)); // true

        System.out.println(scoreRange.isNaturalOrdering()); // true
        Range<Score> scoreRange3 = Range.between(scoreOne, scoreFive, new Comparator<Score>() {
            @Override
            public int compare(Score o1, Score o2) {
                return o1.value - o2.value;
            }
        });
        System.out.println(scoreRange3.isNaturalOrdering()); // false

        // 是否有交集
        System.out.println(scoreRange.isOverlappedBy(intersection)); // true
        System.out.println(scoreRange1.isOverlappedBy(intersection)); // false

        System.out.println(scoreRange.fit(scoreFive)); // Score{value=5}
        System.out.println(scoreRange.fit(new Score(-1))); // // Score{value=1}
        System.out.println(scoreRange.fit(new Score(12))); // // Score{value=10}

        // [Score{value=1}..Score{value=10}]
        System.out.println(scoreRange);
        // Range[Score{value=1}..Score{value=10}] Comparator[INSTANCE]
        System.out.println(scoreRange.toString("Range[%s..%s] Comparator[%s]"));
    }

    static class Score implements Comparable<Score> {
        int value;

        public Score(int value) {
            this.value = value;
        }

        @Override
        public int compareTo(Score right) {
            return value - right.value;
        }

        @Override
        public String toString() {
            return "Score{" +
                    "value=" + value +
                    '}';
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Score) {
                return ((Score)obj).value == value;
            }
            return super.equals(obj);
        }
    }
}
