package algorithm;

public class Dichotomy {
    public static void main(String[] args) {

        int[] arr0 = null;
        System.out.println(searchRightTarget(arr0, 3));

        int[] arr1 = {};
        System.out.println(searchRightTarget(arr1, 3));

        int[] arr2 = {1,2,5};
        System.out.println(searchRightTarget(arr2, 3));

        int[] arr21 = {4,5,5};
        System.out.println(searchRightTarget(arr21, 3));

        int[] arr22 = {1,2};
        System.out.println(searchRightTarget(arr22, 3));

        int[] arr3 = {1,2,3,3,3,5};
        System.out.println(searchRightTarget(arr3, 3));

        int[] arr4 = {3,3,3,5};
        System.out.println(searchRightTarget(arr4, 3));

        int[] arr5 = {1,2,3,3,3};
        System.out.println(searchRightTarget(arr5, 3));

        int[] arr6 = {1,2,3,3,3,4,5,5,5,5,5,5,5,5,5,5,5,5};
        System.out.println(searchRightTarget(arr6, 3));

        int[] arr7 = {1,1,1,1,1,1,1,1,1,2,3,3,3,4};
        System.out.println(searchRightTarget(arr7, 3));
        System.out.println("binarySearchRight");
        System.out.println(binarySearchRight(arr3, 3));
        System.out.println(binarySearchRight(arr4, 3));
        System.out.println(binarySearchRight(arr5, 3));
        System.out.println(binarySearchRight(arr6, 3));
        System.out.println(binarySearchRight(arr7, 3));
    }


    /**
     * 二分查找最右目标数值的数组下标
     *
     * 返回 -1 为异常或未找到，大于等于0为数组下标
     * @param arr 数组
     * @param target 目标值
     * @return 下标
     */
    public static int searchRightTarget(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int low = 0;
        int middle = 0;
        int high = arr.length -1;
        int index = -1;

        if (target < arr[0] || target > arr[high] || low > high) {
            return -1;
        }

        while (low < high) {
            middle = (low + high) / 2;
            if (arr[middle] > target) {
                high = middle -1;
            } else if (arr[middle] < target) {
                low = middle + 1;
            } else {
                index = middle;
                break;
            }
        }

        if (index == -1) {
            //没找到目标值
            return -1;
        }
        if (arr[high] == target) {
            //碰巧最高位就是最右
            return high;
        }
        //找到最右
        middle = 0;
        low = index;

        while (low <= high) {
            middle = (low + high) / 2;
            if (arr[middle] > target) {
                if (arr[middle - 1] == target) {
                    index = middle - 1;
                    break;
                }
                high = middle - 1;
            } else {
                low = middle + 1;
            }
        }
        return index;
    }

    /**
     * 二分查找最右目标数值的数组下标
     *
     * 返回 -1 为异常或未找到，大于等于0为数组下标
     * @param arr 数组
     * @param target 目标值
     * @return 下标
     */
    public static int binarySearchRight(int[] arr,int target){
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int low = 0;
        int middle = 0;
        int high = arr.length -1;
        int index = -1;

        if (target < arr[0] || target > arr[high] || low > high) {
            return -1;
        }

        //找到等于目标值下标
        while (low <= high) {
            middle = (low + high) / 2;
            if (arr[middle] > target) {
                high = middle -1;
            } else if (arr[middle] < target) {
                low = middle + 1;
            } else {
                if (middle == arr.length -1 || arr[middle + 1] > target) {
                    index = middle;
                    break;
                }
                low = middle + 1;
            }
        }
        return index;
    }

}
