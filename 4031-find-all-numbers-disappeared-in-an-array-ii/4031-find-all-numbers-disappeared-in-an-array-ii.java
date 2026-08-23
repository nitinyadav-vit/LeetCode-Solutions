class Solution {
    public List<List<Integer>> findDisappearedNumbers(int[] nums, int lower, int upper) {
        List<List<Integer>> result=new ArrayList<>();
        Arrays.sort(nums);
        long expected=lower;
        for(int num: nums){
            if(num<lower){
                continue;
            }
            if(num> upper){
                break;
            }
            if(num<expected){
                continue;
            }
            if(num>expected){
                result.add(Arrays.asList((int)expected,num-1));
            }
            expected=(long)num+1;

            
        }
        if(expected<=upper){
            result.add(Arrays.asList((int)expected,upper));
        }
        return result;
    }
}