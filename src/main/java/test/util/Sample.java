// This file is auto-generated, don't edit it. Thanks.
package test.util;

import com.aliyun.tea.*;
import com.aliyun.teaopenapi.Client;

import java.util.Map;

public class Sample {

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
        // Endpoint 请参考 https://api.aliyun.com/product/Dysmsapi
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new com.aliyun.teaopenapi.Client(config);
    }

    /**
    * 使用STS鉴权方式初始化账号Client，推荐此方式。
    * @param accessKeyId
    * @param accessKeySecret
    * @param securityToken
    * @return Client
    * @throws Exception
    */
    public static com.aliyun.teaopenapi.Client createClientWithSTS(String accessKeyId, String accessKeySecret, String securityToken) throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                // 必填，您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 必填，您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret)
                // 必填，您的 Security Token
                .setSecurityToken(securityToken)
                // 必填，表明使用 STS 方式
                .setType("sts");
        // Endpoint 请参考 https://api.aliyun.com/product/Dysmsapi
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new com.aliyun.teaopenapi.Client(config);
    }

    /**
     * API 相关
     * @param
     * @return OpenApi.Params
     */
    public static com.aliyun.teaopenapi.models.Params createApiInfo() throws Exception {
        com.aliyun.teaopenapi.models.Params params = new com.aliyun.teaopenapi.models.Params()
                // 接口名称
                .setAction("SendSms")
                // 接口版本
                .setVersion("2017-05-25")
                // 接口协议
                .setProtocol("HTTPS")
                // 接口 HTTP 方法
                .setMethod("POST")
                .setAuthType("AK")
                .setStyle("RPC")
                // 接口 PATH
                .setPathname("/")
                // 接口请求体内容格式
                .setReqBodyType("json")
                // 接口响应体内容格式
                .setBodyType("json");
        return params;
    }

    public static void main(String[] args_) throws Exception {
        // 请确保代码运行环境设置了环境变量 ALIBABA_CLOUD_ACCESS_KEY_ID 和 ALIBABA_CLOUD_ACCESS_KEY_SECRET。

        // 工程代码泄露可能会导致 AccessKey 泄露，并威胁账号下所有资源的安全性。以下代码示例仅供参考，建议使用更安全的 STS 方式，更多鉴权访问方式请参见：https://help.aliyun.com/document_detail/378657.html
/*
        com.aliyun.teaopenapi.Client client = Sample.createClient(System.getenv("LTAI5tL65HmdZFkgTqaMeTX8"), System.getenv("DhaaxuN6qe2h5yJDLIZqdzHFpC7vIb"));
*/
        Client client = Sample.createClient("LTAI5tL65HmdZFkgTqaMeTX8", "DhaaxuN6qe2h5yJDLIZqdzHFpC7vIb");
        com.aliyun.teaopenapi.models.Params params = Sample.createApiInfo();
        // query params
        java.util.Map<String, Object> queries = new java.util.HashMap<>();
        queries.put("PhoneNumbers", "18750851691");
        queries.put("SignName", "阿里云短信测试");
        queries.put("TemplateCode", "SMS_154950909");
        queries.put("TemplateParam", "{\"code\":\"1234\"}");
        // runtime options
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        com.aliyun.teaopenapi.models.OpenApiRequest request = new com.aliyun.teaopenapi.models.OpenApiRequest()
                .setQuery(com.aliyun.openapiutil.Client.query(queries));
        // 复制代码运行请自行打印 API 的返回值
        // 返回值为 Map 类型，可从 Map 中获得三类数据：响应体 body、响应头 headers、HTTP 返回的状态码 statusCode。
        Map<String, Object> resp = (Map<String, Object>) client.callApi(params, request, runtime);
        com.aliyun.teaconsole.Client.log(com.aliyun.teautil.Common.toJSONString(resp));
    }
}
