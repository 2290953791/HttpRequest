package com.mp5a5.www.library.net.interceptor;

import com.mp5a5.www.library.utils.ApiConfig;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.annotations.EverythingIsNonNull;

import java.io.IOException;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author ：mp5a5 on 2018/12/23 13：31
 * @describe：
 * @email：wwb199055@126.com
 */
@SuppressWarnings("ALL")
public class HttpHeaderInterceptor implements Interceptor {

    @Override
    @EverythingIsNonNull
    public Response intercept(Chain chain) throws IOException {

        Request originalRequest = chain.request();

        Map<String, String> heads = ApiConfig.getHeads();

        String token = ApiConfig.getToken();

        Request.Builder authorization = originalRequest.newBuilder()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .addHeader("Connection", "close")
                .addHeader("Accept-Encoding", "identity");

        //动态添加Header
        if (null != heads) {
            heads.forEach(new BiConsumer<String, String>() {
                @Override
                public void accept(String key, String value) {
                    authorization.addHeader(key, value);
                }
            });
        }

        Request build = authorization.build();

        return chain.proceed(build);
    }
}
