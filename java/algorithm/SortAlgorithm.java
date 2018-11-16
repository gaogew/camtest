package com.gaoge.code;

import java.util.Arrays;

/**
 * @author gaoge
 * @date 2018/11/16 17:13
 * description:
 * 给定一个一维降序数组，将其转化为 最小值->最大值->次小值->次大值->三小值->三大值....
 *    例： [7,5,4,3,1] -> [1,7,3,5,4]
 */

public class SortAlgorithm {
    public void resetSortedArray(int []arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        int maxIndex = arr.length - 1;
        int []bit = new int[arr.length / 32 + 1];

        for (int i = 0; i <= maxIndex; i++) {
            if ((bit[i/32] & (1 << (i % 32))) == 0) {
                int currentIndex;
                int index = i;
                int tmp = arr[index];
                while ((bit[index/32] & (1 << (index % 32))) == 0) {
                    bit[index/32] = bit[index/32] | (1 << (index % 32));
                    index = (currentIndex = 2 * index + 1) > maxIndex ? 2 * (maxIndex - index) : currentIndex;
                    int tmpValue = arr[index];
                    arr[index] = tmp;
                    tmp = tmpValue;
                }
            }
        }

    }

    public static void main(String[] args) {
        int []arr;
        SortAlgorithm sort = new SortAlgorithm();
        for (int i = 1; i < 200; i++) {
            sort.resetSortedArray(arr = generateArray(i));
            System.out.println(Arrays.toString(arr));
        }

    }

    private static int[] generateArray(int len) {
        int []arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = len - i;
        }
        return arr;
    }
}

