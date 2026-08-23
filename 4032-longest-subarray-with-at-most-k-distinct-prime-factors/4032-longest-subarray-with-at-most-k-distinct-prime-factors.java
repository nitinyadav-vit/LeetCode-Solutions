class Solution {
    public int longestSubarray(int[] nums, int k) {
        int n=nums.length;
        int maxVal=1;
        for(int num:nums){
            if(num>maxVal){
                maxVal=num;
            }
        }
        List<Integer>[]primeFactors=new List[maxVal+1];
        for(int i=0;i<=maxVal;i++){
            primeFactors[i]=new ArrayList<>();
        }
        for(int i=2;i<=maxVal;i++){
            if(primeFactors[i].isEmpty()){
                for(int j=i;j<=maxVal;j+=i){
                    primeFactors[j].add(i);
                }
            }
        }
        Map<Integer,Integer> factorCounts=new HashMap<>();
        int maxLength=0;
        int left=0;

        for(int right=0;right<n;right++){
            for(int prime:primeFactors[nums[right]]){
                factorCounts.put(prime,factorCounts.getOrDefault(prime,0)+1);
                
            }
            while(factorCounts.size()>k){
                for(int prime:primeFactors[nums[left]]){
                    int count=factorCounts.get(prime);
                    if(count==1){
                        factorCounts.remove(prime);
                    }else{
                        factorCounts.put(prime,count-1);
                    }
                }
                left++;
            }
            maxLength=Math.max(maxLength,right-left+1);
        }
        return maxLength;
    }
}