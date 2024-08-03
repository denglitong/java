package com.denglitong.apache_commons_demo;

import org.apache.commons.lang3.BitField;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/8/14
 */
public class BitFieldDemo {

    public static void main(String[] args) {
        BitField blue = new BitField(0xFF);
        BitField green = new BitField(0xFF00);
        BitField red = new BitField(0xFF0000);

        int paintInstruction = 0;
        paintInstruction = red.setValue(paintInstruction, 35);
        paintInstruction = green.setValue(paintInstruction, 100);
        paintInstruction = blue.setValue(paintInstruction, 255);

        BitField anyColor = new BitField(0xFFFFFF);
        System.out.println(anyColor.isSet(paintInstruction));
        System.out.println(anyColor.isAllSet(paintInstruction));

        System.out.println(red.getValue(paintInstruction)); // 35
        System.out.println(green.getValue(paintInstruction)); // 100
        System.out.println(blue.getValue(paintInstruction)); // 255

        BitField isMetallic = new BitField(0x1000000);
        System.out.println(isMetallic.isSet(paintInstruction)); // false
        
    }
}
