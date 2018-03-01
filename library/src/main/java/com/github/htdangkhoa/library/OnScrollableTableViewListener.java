package com.github.htdangkhoa.library;

import android.view.ViewGroup;

/**
 * Created by dangkhoa on 2/28/18.
 */

public interface OnScrollableTableViewListener {
    public void onBindColumnHeaders(ViewGroup parent);
    public void onBindRowHeaders(ViewGroup parent);
    public void onBindCells(ViewGroup parent);
}
