/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */

class Solution {
    public List<List<String>> printTree(TreeNode root) {
        int h = height(root);
        int m = h + 1;
        int n = (1 << m) - 1;
        List<List<String>> res = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            List<String> row = new ArrayList<>();

            for (int j = 0; j < n; j++) {
                row.add("");
            }

            res.add(row);
        }

        fill(res, root, 0, 0, n - 1);

        return res;
    }
    private int height(TreeNode root) {
        if (root == null) {
            return -1;
        }

        return 1 + Math.max(height(root.left), height(root.right));
    }
    private void fill(List<List<String>> res, TreeNode root,
                      int row, int left, int right) {

        if (root == null) {
            return;
        }

        int mid = (left + right) / 2;

        res.get(row).set(mid, String.valueOf(root.val));

        fill(res, root.left, row + 1, left, mid - 1);

        fill(res, root.right, row + 1, mid + 1, right);
    }
}
