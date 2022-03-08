/*
 *
 * Copyright 2018-present KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.cetuer.smartparkinglot.data.response;

/**
 * Create by Cetuer at 2022/03/06
 * 统一返回值
 */
public class ResultData<T> {
    /**
     * 状态码
     */
    private int status;
    /**
     * 消息提示
     */
    private String message;
    /**
     * 数据
     */
    private T data;

    /**
     * 调用接口时间
     */
    private long timestamp;

    public ResultData() {
    }

    public ResultData(int status, String message, T data, long timestamp) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ResultData{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", timestamp=" + timestamp +
                '}';
    }

    public interface Result<T> {
        /**
         * 数据返回回调
         * @param data 数据
         */
        void onResult(T data);
    }
}
