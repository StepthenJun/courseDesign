package com.foodhealth.utils;// This file is auto-generated, don't edit it. Thanks.

import com.google.gson.Gson;

import java.util.Map;

public class TranslateUtil {

    /**
     * 使用AK&SK初始化账号Client
     * @param accessKeyId
     * @param accessKeySecret
     * @return Client
     * @throws Exception
     */
    public static com.aliyun.teaopenapi.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                // 必填，您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 必填，您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // Endpoint 请参考 https://api.aliyun.com/product/alimt
        config.endpoint = "mt.aliyuncs.com";
        return new com.aliyun.teaopenapi.Client(config);
    }

    /**
     * API 相关
     * @return OpenApi.Params
     */
    public static com.aliyun.teaopenapi.models.Params createApiInfo() throws Exception {
        com.aliyun.teaopenapi.models.Params params = new com.aliyun.teaopenapi.models.Params()
                // 接口名称
                .setAction("TranslateGeneral")
                // 接口版本
                .setVersion("2018-10-12")
                // 接口协议
                .setProtocol("HTTPS")
                // 接口 HTTP 方法
                .setMethod("POST")
                .setAuthType("AK")
                .setStyle("RPC")
                // 接口 PATH
                .setPathname("/")
                // 接口请求体内容格式
                .setReqBodyType("formData")
                // 接口响应体内容格式
                .setBodyType("json");
        return params;
    }

    public static String translate(String foodname) throws Exception {
        // 请确保代码运行环境设置了环境变量 ALIBABA_CLOUD_ACCESS_KEY_ID 和 ALIBABA_CLOUD_ACCESS_KEY_SECRET。
        // 工程代码泄露可能会导致 AccessKey 泄露，并威胁账号下所有资源的安全性。以下代码示例使用环境变量获取 AccessKey 的方式进行调用，仅供参考，建议使用更安全的 STS 方式，更多鉴权访问方式请参见：https://help.aliyun.com/document_detail/378657.html
        com.aliyun.teaopenapi.Client client = TranslateUtil.createClient("LTAI5tL65HmdZFkgTqaMeTX8", "DhaaxuN6qe2h5yJDLIZqdzHFpC7vIb");
        com.aliyun.teaopenapi.models.Params params = TranslateUtil.createApiInfo();
        // body params
        java.util.Map<String, Object> body = new java.util.HashMap<>();
        body.put("FormatType", "text");
        body.put("SourceLanguage", "zh");
        body.put("TargetLanguage", "en");
        body.put("SourceText", foodname);
        body.put("Scene", "general");
        // runtime options
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        com.aliyun.teaopenapi.models.OpenApiRequest request = new com.aliyun.teaopenapi.models.OpenApiRequest()
                .setBody(body);
        // 复制代码运行请自行打印 API 的返回值
        // 调用API
        Map<String, ?> responseMap = client.callApi(params, request, runtime);
        // 获取body部分
        Map<?, ?> responsebody = (Map<?, ?>) responseMap.get("body");

        // 获取Data部分
        Map<?, ?> data = (Map<?, ?>) responsebody.get("Data");

        // 获取Translated字段的值
        String translated = (String) data.get("Translated");
        return translated;
    }
}
