import java.util.*;

class Solution {
    // Structure to represent an Interval
    static class Interval {
        int l, r, weight, id;

        Interval(int l, int r, int weight, int id) {
            this.l = l;
            this.r = r;
            this.weight = weight;
            this.id = id;
        }
    }

    // Structure to store DP state
    static class State implements Comparable<State> {
        long weight;
        List<Integer> indices;

        State(long weight, List<Integer> indices) {
            this.weight = weight;
            this.indices = new ArrayList<>(indices);
            Collections.sort(this.indices); // Ensure indices are sorted for comparison
        }

        @Override
        public int compareTo(State other) {
            if (this.weight != other.weight) {
                return Long.compare(other.weight, this.weight); // Higher weight preferred
            }
            // Lexicographically smaller array preferred
            int minLen = Math.min(this.indices.size(), other.indices.size());
            for (int i = 0; i < minLen; i++) {
                int cmp = Integer.compare(this.indices.get(i), other.indices.get(i));
                if (cmp != 0) return cmp;
            }
            return Integer.compare(this.indices.size(), other.indices.size());
        }
    }

    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();
        Interval[] arr = new Interval[n];
        for (int i = 0; i < n; i++) {
            List<Integer> interval = intervals.get(i);
            arr[i] = new Interval(interval.get(0), interval.get(1), interval.get(2), i);
        }

        // Sort by end time
        Arrays.sort(arr, (a, b) -> Integer.compare(a.r, b.r));

        // Find predecessor index for each interval using Binary Search
        int[] pred = new int[n];
        for (int i = 0; i < n; i++) {
            int low = 0, high = i - 1, best = -1;
            while (low <= high) {
                int mid = (low + high) / 2;
                if (arr[mid].r < arr[i].l) { // Non-overlapping condition
                    best = mid;
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
            pred[i] = best;
        }

        // DP table: dp[i][k] for first i intervals, using at most k intervals
        State[][] dp = new State[n + 1][5];
        for (int i = 0; i <= n; i++) {
            for (int k = 0; k <= 4; k++) {
                dp[i][k] = new State(0, new ArrayList<>());
            }
        }

        for (int i = 1; i <= n; i++) {
            Interval curr = arr[i - 1];
            int p = pred[i - 1]; // 0-based predecessor index

            for (int k = 1; k <= 4; k++) {
                // Option 1: Do not include current interval
                State bestState = dp[i - 1][k];

                // Option 2: Include current interval
                State prev = dp[p + 1][k - 1];
                List<Integer> newIndices = new ArrayList<>(prev.indices);
                newIndices.add(curr.id);
                State candidateState = new State(prev.weight + curr.weight, newIndices);

                // Choose the best state between Option 1 & Option 2
                if (candidateState.compareTo(bestState) < 0) {
                    bestState = candidateState;
                }

                dp[i][k] = bestState;
            }
        }

        // Output best indices array
        State resultState = dp[n][4];
        int[] result = new int[resultState.indices.size()];
        for (int i = 0; i < resultState.indices.size(); i++) {
            result[i] = resultState.indices.get(i);
        }
        return result;
    }
}