package com.zhouwei.anything.xxorm;

import java.io.InputStream;

/**
 * Created by zhouwei on 2017/12/22.
 */

public interface IConfigParser {
    void parserConfig(InputStream inputStream, String inputEncoding);
}
