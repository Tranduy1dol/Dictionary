//package Controller;
//
//import java.util.List;
//
//public class QuickSortSearch {
//    public static int quickSortSearch(List<WordSimple> words, String target) {
//        return quickSortSearch(words, target, 0, words.size() - 1);
//    }
//
//    private static int quickSortSearch(List<WordSimple> words, String target, int left, int right) {
//        if (left > right) {
//            return -1;
//        }
//
//        int pivotIndex = partition(words, left, right);
//
//        if (words.get(pivotIndex).getWordTarget().equals(target)) {
//            return pivotIndex;
//        } else if (words.get(pivotIndex).getWordTarget().compareTo(target) > 0) {
//            return quickSortSearch(words, target, left, pivotIndex - 1);
//        } else {
//            return quickSortSearch(words, target, pivotIndex + 1, right);
//        }
//    }
//
//    private static int partition(List<WordSimple> words, int left, int right) {
//        String pivot = words.get(right).getWordTarget();
//        int i = left - 1;
//
//        for (int j = left; j < right; j++) {
//            if (words.get(j).getWordTarget().compareTo(pivot) < 0) {
//                i++;
//
//                // Swap words at i and j
//                WordSimple temp = words.get(i);
//                words.set(i, words.get(j));
//                words.set(j, temp);
//            }
//        }
//
//        // Swap words at i + 1 and right
//        WordSimple temp = words.get(i + 1);
//        words.set(i + 1, words.get(right));
//        words.set(right, temp);
//
//        return i + 1;
//    }
//}
