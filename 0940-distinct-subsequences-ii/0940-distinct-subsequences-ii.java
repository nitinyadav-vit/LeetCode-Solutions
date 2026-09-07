class Solution {
    public int distinctSubseqII(String s) {
        int Mod = 1_000_000_007;

        long[]last=new long[26];

        long totalSum=0;
        for(char c:s.toCharArray()){
            int idx=c-'a';

            long added =(totalSum+1)%Mod;

            totalSum =(totalSum+added-last[idx]+Mod)%Mod;

            last[idx]=added;
        }
        return (int)totalSum;
        
    }
}