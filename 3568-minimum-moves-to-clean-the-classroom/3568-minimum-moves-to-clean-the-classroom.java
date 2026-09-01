import java.util.*;

class Solution {
    public int minMoves(String[] classroom, int maxEnergy) {
        int m = classroom.length;
        int n = classroom[0].length();
        
        int startR = -1; 
        int startC = -1;
        List<int[]> litterList = new ArrayList<>();
        
        // Find start position and map all litter locations
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char ch = classroom[i].charAt(j);
                if (ch == 'S') {
                    startR = i;
                    startC = j;
                } else if (ch == 'L') {
                    litterList.add(new int[]{i, j});
                }
            }
        }
        
        int numLitter = litterList.size();
        int targetMask = (1 << numLitter) - 1;
        
        // Map (r, c) of each litter to its bit index
        int[][] litterIdx = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(litterIdx[i], -1);
        }
        for (int i = 0; i < numLitter; i++) {
            int[] pos = litterList.get(i);
            litterIdx[pos[0]][pos[1]] = i;
        }
        
        // maxEnergySeen[r][c][mask] stores the maximum remaining energy for a given state
        int[][][] maxEnergySeen = new int[m][n][1 << numLitter];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(maxEnergySeen[i][j], -1);
            }
        }
        
        // Queue stores: {row, col, mask, remaining_energy}
        Queue<int[]> queue = new LinkedList<>();
        
        // Initial state at 'S'
        int initialMask = 0;
        // Check if start position itself has litter (in case 'S' overlaps with 'L', though usually distinct)
        if (litterIdx[startR][startC] != -1) {
            initialMask |= (1 << litterIdx[startR][startC]);
        }
        
        queue.offer(new int[]{startR, startC, initialMask, maxEnergy});
        maxEnergySeen[startR][startC][initialMask] = maxEnergy;
        
        int moves = 0;
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int k = 0; k < size; k++) {
                int[] curr = queue.poll();
                int r = curr[0];
                int c = curr[1];
                int mask = curr[2];
                int e = curr[3];
                
                // If all litter items are collected
                if (mask == targetMask) {
                    return moves;
                }
                
                // If energy is 0, we can't move anywhere from here
                if (e == 0) continue;
                
                for (int[] d : dirs) {
                    int nr = r + d[0];
                    int nc = c + d[1];
                    
                    // Boundary check and wall check
                    if (nr < 0 || nr >= m || nc < 0 || nc >= n || classroom[nr].charAt(nc) == 'X') {
                        continue;
                    }
                    
                    int nextEnergy = e - 1;
                    char nextCell = classroom[nr].charAt(nc);
                    
                    // Restore energy if reset point 'R' is reached
                    if (nextCell == 'R') {
                        nextEnergy = maxEnergy;
                    }
                    
                    int nextMask = mask;
                    // Collect litter if on an 'L' cell
                    if (litterIdx[nr][nc] != -1) {
                        nextMask |= (1 << litterIdx[nr][nc]);
                    }
                    
                    // State prune: only add to queue if we have strictly more energy than before for this state
                    if (nextEnergy > maxEnergySeen[nr][nc][nextMask]) {
                        maxEnergySeen[nr][nc][nextMask] = nextEnergy;
                        queue.offer(new int[]{nr, nc, nextMask, nextEnergy});
                    }
                }
            }
            moves++;
        }
        
        return -1;
    }
}