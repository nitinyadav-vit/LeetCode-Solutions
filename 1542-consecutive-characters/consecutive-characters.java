class Solution {
    public int maxPower(String s) {
        int max=1;
        int count=1;

        for(int i=1;i<s.length();i++){
            char curr=s.charAt(i);
            char pre=s.charAt(i-1);
            if(curr==pre){
                count++;
            }else{
                max=Math.max(count ,max);
                count=1;
            }
        }
        max=Math.max(count ,max);
        return max;
    }
}