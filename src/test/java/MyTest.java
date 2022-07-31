import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MyTest {
    public static void main(String[] args) {
        int[] nums = {1,1,1,2,2,2,3,3};
        int i = Solution.minimumOperations(nums);
        System.out.println(i);
    }
}
class Solution {
    public static int minimumOperations(int[] nums) {
        //首先对数组进行排序操作，这样每次小的数字都是靠前面的

        Arrays.sort(nums);
        //记录运行步数
        int times = 0;
        //因为是排序过后的数组，所以只需要检查最后一个数字是否为0就可以知道是否整个数组都为0
        int index1 = nums.length-1;
        while(nums[index1] != 0){
            //选出最小的那个非零数
            int temp = 0;
            int num = 0;
            for(int i = temp ; i < nums.length ; i++){

                if(nums[i] != 0){
                    temp = i;
                    num = nums[temp];
                    times++;
                }else {
                    continue;
                }

                for(int j = temp; j < nums.length; j++){

                    if(nums[j]-num >=0){
                        nums[j] = nums[j]-num;
                    }else{
                        nums[j] = 0;
                    }
                }
            }
            //拿到最小的数字以后各自往后面的数字
        }
        return times;
    }
}
