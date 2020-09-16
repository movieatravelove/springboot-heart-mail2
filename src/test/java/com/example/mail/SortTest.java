package com.example.mail;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: zhang
 * @Date: 2019/2/22 17:31
 * @Description:
 */
public class SortTest {

    Logger logger = LoggerFactory.getLogger(SortTest.class);

    private int[] array = {23, 11, 7, 29, 33, 59, 8, 20, 9, 3, 2, 6, 10, 44, 83, 28, 5, 1, 0, 36};

    /**
     * 冒泡排序
     */
    @Test
    public void bubbleSort(){
        logger.info("aa:",array.toString());
        for (int i = 0; i < array.length-1; i++) {
            for (int j = 0; j < array.length-i-1; j++) {
                if (array[j] > array[j+1]){
                    int temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                }
            }
        }
        logger.info(array.toString());
    }



    // test
}
