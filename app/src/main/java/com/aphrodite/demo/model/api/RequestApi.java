package com.aphrodite.demo.model.api;

import com.aphrodite.demo.model.network.BeautyResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * <p>描述：通用的的api接口</p>
 * <p>
 * 1.加入基础API，减少Api冗余<br>
 * 2.支持多种方式访问网络（get,put,post,delete），包含了常用的情况<br>
 * 3.传统的Retrofit用法，服务器每增加一个接口，就要对应一个api，非常繁琐<br>
 * 4.如果返回ResponseBody在返回的结果中去获取T,又会报错，这是因为在运行过程中,通过泛型传入的类型T丢失了,所以无法转换,这叫做泛型擦除。
 * 《泛型擦除》不知道的童鞋自己百度哦！！<br>
 * </p>
 * <p>
 * 注意事项：
 * <br>
 * 1.使用@url,而不是@Path注解,后者放到方法体上,会强制先urlencode,然后与baseurl拼接,请求无法成功<br>
 * 2.注意事项： map不能为null,否则该请求不会执行,但可以size为空.
 * <br>
 * </p>
 * Created by Aphrodite on 2019/4/23.
 */
public interface RequestApi {
    @GET("https://gank.io/api/v2/data/category/Girl/type/Girl/page/{page}/count/{count}")
    Observable<BeautyResponse> queryBeauty(
            @Path("count") int count,
            @Path("page") int page
    );

    @GET()
    Observable<String> queryMore(@Url() String url, @Query("page") int pagerOffset);
}
