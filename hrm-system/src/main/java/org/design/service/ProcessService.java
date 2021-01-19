package org.design.service;

import java.io.InputStream;

public interface ProcessService {

    /**
     * 部署流程
     * @param inputStream
     * @param processName
     */
    void saveProcess(InputStream inputStream, String processName);

    void pushProcess(Integer id, String username);
}
