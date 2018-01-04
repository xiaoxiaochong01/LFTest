package com.longfor.core.net;

import com.longfor.core.net.request.RequestParams;
import com.longfor.core.utils.log.LogUtils;

import java.io.File;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author: tongzhenhua
 * @date: 2018/1/3
 * @function:
 */
public  class RestParamsUtils {
    //params 转 json 作为参数传递
    public static  final String paramsTransferJson(Map<String, String> params) {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append("\"").append(entry.getKey()).append("\""+":"+"\"").append(entry.getValue()).append("\"").append(",");
            }
            sb.delete(sb.length()-1,sb.length());
        }
        sb.append("}");
        return sb.toString();
    }

    //params 转  body 错位参数传递
    public static final RequestBody paramsTranferBody( RequestParams params) {
        MultipartBody.Builder requestBody = new MultipartBody.Builder();
        requestBody.setType(MultipartBody.FORM);
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.fileParams.entrySet()) {
                if (entry.getValue() instanceof File) {
                    File file = (File) entry.getValue();
                    RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
                    // 参数分别为， 请求key ，文件名称 ， RequestBody
                    requestBody.addFormDataPart(entry.getKey() , ((File) entry.getValue()).getName(), body);
                } else if (entry.getValue() instanceof String) {
                    requestBody.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""),
                            RequestBody.create(null, (String) entry.getValue()));
                }
            }
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                requestBody.addFormDataPart(entry.getKey(), entry.getValue());
                LogUtils.e("HttpReq","params---"+entry.getKey()+":"+entry.getValue());
            }
        }
        return requestBody.build();
    }
}
