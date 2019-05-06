package com.cunjunwang.shanghai.transportation.service.dataService.flight;

import com.aliyun.oss.common.utils.IOUtils;
import com.eclipsesource.v8.V8;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 处理网站反爬虫机制
 * Created by CunjunWang on 2019-05-03.
 */
@Component
public class AntiCrawlHandler {

    private static final Logger logger = LoggerFactory.getLogger(AntiCrawlHandler.class);

    /**
     * 传入请求的URL, 解析返回数据
     *
     * @param url url地址
     * @return pdf文件对象
     */
    public String handleGetRequest(String url) {
        try {
            HttpClient client = HttpClients.createDefault();
            HttpGet getRequest = new HttpGet(url);
            setHeader(getRequest);
            HttpResponse response = client.execute(getRequest);

            String __jsluid = getJsluid(response);
            String body = getResponseBodyAsString(response);
            logger.info("Body: {}", body);
            String __jsl_clearance = getJslClearance(body);
            getRequest = new HttpGet(url);
            getRequest.setHeader("cookie", __jsluid + "; " + __jsl_clearance);
            setHeader(getRequest);
            response = client.execute(getRequest);
            return getResponseBodyAsString(response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 传入请求的URL, 解析返回数据
     *
     * @param url url地址
     * @return pdf文件对象
     */
    public String handlePostRequest(String url) {
        try {
            HttpClient client = HttpClients.createDefault();
            HttpPost postRequest = new HttpPost(url);
            setHeader(postRequest);
            HttpResponse response = client.execute(postRequest);

            String __jsluid = getJsluid(response);
            String body = getResponseBodyAsString(response);
            logger.info("Body: {}", body);
            String __jsl_clearance = getJslClearance(body);
            postRequest = new HttpPost(url);
            postRequest.setHeader("cookie", __jsluid + "; " + __jsl_clearance);
            setHeader(postRequest);
            response = client.execute(postRequest);
            return getResponseBodyAsString(response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 给HttpGet设置一些必要的header
     *
     * @param request 通过get方法访问pdf资源
     */
    private static void setHeader(HttpUriRequest request) {
        request.setHeader("Upgrade-Insecure-Requests", "1");
        request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
    }

    /**
     * 通过破解动态JavaScript脚本,
     * 获取cookie名为 __jsl_clearance的值
     *
     * @param body 相应内容(一般为第一次请求获取到的动态js字符串)
     * @return cookie名为 __jsl_clearance的值
     */
    private static String getJslClearance(String body) {
        //V8:谷歌开源的运行JavaScript脚本的库. 参数:globalAlias=window, 表示window为全局别名,
        // 告诉V8在运行JavaScript代码时, 不要从代码里找window的定义.
        V8 runtime = V8.createV8Runtime("window");
        //将第一次请求pdf资源时获取到的字符串提取成V8可执行的JavaScript代码
        body = body.trim()
                .replace("<script>", "")
                .replace("</script>", "")
                .replace("eval(y.replace(/\\b\\w+\\b/g, function(y){return x[f(y,z)-1]||(\"_\"+y)}))",
                        "y.replace(/\\b\\w+\\b/g, function(y){return x[f(y,z)-1]||(\"_\"+y)})");
        //用V8执行该段代码获取新的动态JavaScript脚本
        String result = runtime.executeStringScript(body);

        //获取 jsl_clearance 的第一段, 格式形如: 1543915851.312|0|
        String startStr = "document.cookie='";
        int i1 = result.indexOf(startStr) + startStr.length();
        int i2 = result.indexOf("|0|");
        String cookie1 = result.substring(i1, i2 + 3);
        /*
        获取 jsl_clearance 的第二段,格式形如: DW2jqgJO5Bo45yYRKLlFbnqQuD0%3D。
        主要原理是: 新的动态JavaScript脚本是为浏览器设置cookie, 且cookie名为__jsl_clearance
        其中第一段值(格式形如:1543915851.312|0|)已经明文写好, 用字符串处理方法即可获取.
        第二段则是一段JavaScript函数, 需要有V8运行返回,
        该函数代码需要通过一些字符串定位, 提取出来, 交给V8运行.
         */
        startStr = "|0|'+(function(){";
        int i3 = result.indexOf(startStr) + startStr.length();
        int i4 = result.indexOf("})()+';Expires");
        String code = result.substring(i3, i4).replace(";return", ";");
        String cookie2 = runtime.executeStringScript(code);

        /*
        拼接两段字符串, 返回jsl_clearance的完整的值.
        格式形如: 1543915851.312|0|DW2jqgJO5Bo45yYRKLlFbnqQuD0%3D
        */
        return cookie1 + cookie2;
    }

    /**
     * 将HTTP响应体转换为字符串返回
     *
     * @param response HTTP响应
     * @return 响应体的字符串形式
     * @throws IOException IO异常
     */
    private static String getResponseBodyAsString(HttpResponse response) throws IOException {
        return IOUtils.readStreamAsString(response.getEntity().getContent(), "UTF-8");
    }

    /**
     * 将HTTP响应体转换为byte数组返回
     *
     * @param response HTTP响应
     * @return 响应体的byte数组形式
     * @throws IOException IO异常
     */
    private static byte[] getResponseBodyAsBytes(HttpResponse response) throws IOException {
        return IOUtils.readStreamAsByteArray(response.getEntity().getContent());
    }

    /**
     * 通过响应头的set-cookie
     * 获取cookie名称为__jsluid的值
     *
     * @param response HttpResponse
     * @return __jsluid的值
     */
    private static String getJsluid(HttpResponse response) {
        Header header = response.getFirstHeader("set-cookie");
        String[] split = header.getValue().split(";");
        for (String s : split) {
            if (s.contains("__jsluid")) {
                return s.trim();
            }
        }
        return "";
    }

}
