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
Project: MatchResult - requires Colt.jar"
*/
// MatchResult.java
// Sajan Joseph, sajanjoseph@gmail.com
// http://code.google.com/p/javafaces/
// Modified by Andrew Davison, April 2011, ad@fivedots.coe.psu.ac.th


public class MatchResult
{
    private String matchFnm;
    private double matchDist;


    public MatchResult(String fnm, double dist)
    { matchFnm = fnm;
        matchDist = dist;
    }

    public String getMatchFileName()
    { return matchFnm;  }

    public void setMatchFileName(String fnm)
    { matchFnm = fnm; }

    public double getMatchDistance()
    {  return matchDist;  }

    public void setMatchDistance(double dist)
    {  matchDist = dist;  }


    public String getName()
    // matchFnm is something like "trainingImages\andrew_0123.png"; return "andrew"
    {
        int slashPos = matchFnm.lastIndexOf('\\');
        int extPos = matchFnm.lastIndexOf(".png");
        String name = (slashPos == -1) ? matchFnm.substring(0, extPos) :
                matchFnm.substring(slashPos+1, extPos);

       // name = name.replaceAll("\\.[-_0-9]*$", "");   // remove trailing numbers, etc
        name = name.replaceAll("[^a-zA-Z]", "");
        return name;
    }  // end of getName()

}  // end of MatchResult class
