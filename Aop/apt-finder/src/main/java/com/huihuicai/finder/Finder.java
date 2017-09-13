package com.huihuicai.finder;

import com.huihuicai.finder.provider.Provider;

/**
 * Created by ybm on 2017/9/8.
 */

public interface Finder<T> {

    void finder(T host, Object source, Provider provider);
}
