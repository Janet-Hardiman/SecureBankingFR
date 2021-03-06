package com.company;
/*
.------..------.
|J.--. ||H.--. |
| :(): || :/\: |
| ()() || (__) |
| '--'J|| '--'H|
`------'`------'
Name: Janet Hardiman
Date: 07/10/2016
Project: BuildEigenFaces - requires Colt.jar"
*/
// ImageDistanceInfo.java
// Sajan Joseph, sajanjoseph@gmail.com
// http://code.google.com/p/javafaces/
// Modified by Andrew Davison, April 2011, ad@fivedots.coe.psu.ac.th


public class ImageDistanceInfo
{
    private int index;
    private double value;

    public ImageDistanceInfo(double val, int idx)
    { value = val;
        index = idx;
    }

    public int getIndex()
    { return index; }

    public double getValue()
    { return value;  }

} // end of ImageDistanceInfo class
