package test.util;

import com.aliyun.teaopenapi.Client;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;

import java.util.Map;

/**
 * 短信发送工具类
 */
public class SMSUtils {
 

	public static void sendMessage(String param,String phone) throws Exception {
		Client client = SMSUtils.createClient("LTAI5tL65HmdZFkgTqaMeTX8", "DhaaxuN6qe2h5yJDLIZqdzHFpC7vIb");
		com.aliyun.teaopenapi.models.Params params = Sample.createApiInfo();
		// query params
		java.util.Map<String, Object> queries = new java.util.HashMap<>();
		queries.put("PhoneNumbers", phone);
		queries.put("SignName", "小y通报");
		queries.put("TemplateCode", "SMS_463740422");
//		queries.put("TemplateParam", "{\"code\":\"1234\"}");
		queries.put("TemplateParam", "{\"code\":\""+param+"\"}");
		// runtime options
		com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
		com.aliyun.teaopenapi.models.OpenApiRequest request = new com.aliyun.teaopenapi.models.OpenApiRequest()
				.setQuery(com.aliyun.openapiutil.Client.query(queries));
		// 复制代码运行请自行打印 API 的返回值
		// 返回值为 Map 类型，可从 Map 中获得三类数据：响应体 body、响应头 headers、HTTP 返回的状态码 statusCode。
		Map<String, Object> resp = (Map<String, Object>) client.callApi(params, request, runtime);
		com.aliyun.teaconsole.Client.log(com.aliyun.teautil.Common.toJSONString(resp));
	}

	public static Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
		com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
				// 必填，您的 AccessKey ID
				.setAccessKeyId(accessKeyId)
				// 必填，您的 AccessKey Secret
				.setAccessKeySecret(accessKeySecret);
		// Endpoint 请参考 https://api.aliyun.com/product/Dysmsapi
		config.endpoint = "dysmsapi.aliyuncs.com";
		return new com.aliyun.teaopenapi.Client(config);
	}
}