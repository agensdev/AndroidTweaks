//
//    Authors:
//    Blanka Kulik <blanka@agens.no>
//    Håvard Fossli <hfossli@gmail.com>
//
//    MIT License
//
//    Copyright (c) 2017 Agens AS (http://agens.no/)
//
//    Permission is hereby granted, free of charge, to any person obtaining a copy
//    of this software and associated documentation files (the "Software"), to deal
//    in the Software without restriction, including without limitation the rights
//    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//    copies of the Software, and to permit persons to whom the Software is
//    furnished to do so, subject to the following conditions:
//
//    The above copyright notice and this permission notice shall be included in all
//    copies or substantial portions of the Software.
//
//    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//    SOFTWARE.

package no.agens.androidtweakslibrary.models;

import android.support.annotation.NonNull;

public class Tweak implements Comparable<Tweak> {
    private String collectionName;
    private String groupName;
    private String tweakName;

    public Tweak(String collectionName, String groupName, String tweakName) {
        this.collectionName = collectionName;
        this.groupName = groupName;
        this.tweakName = tweakName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getName() {
        return tweakName;
    }

    @Override
    public int compareTo(@NonNull Tweak tweak) {
        return tweakName.compareToIgnoreCase(tweak.getName());
    }
}
