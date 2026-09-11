class Solution {
    public int totalNumbers(int[] digits) {
        int [] freq=new int [10];
        for(int d: digits){
            freq[d]++;
        }

        int count =0;
        for(int i=100;i<=998;i+=2){
            int d1=i/100;
            int d2= (i/10)%10;
            int d3=i%10;
        


            int []currFreq=new int [10];
            currFreq[d1]++;
            currFreq[d2]++;
            currFreq[d3]++;

            if (currFreq[d1]<=freq[d1] && currFreq[d2]<=freq[d2] && currFreq[d3]<=freq[d3]){
                count ++;
            }

        }
        return count;

    }
}