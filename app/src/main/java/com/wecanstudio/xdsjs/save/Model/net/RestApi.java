package com.wecanstudio.xdsjs.save.Model.net;

import com.wecanstudio.xdsjs.save.Model.bean.BillTypeList;
import com.wecanstudio.xdsjs.save.Model.bean.MaxTypeResponse;
import com.wecanstudio.xdsjs.save.Model.bean.MyBillType;
import com.wecanstudio.xdsjs.save.Model.bean.RegistResponse;
import com.wecanstudio.xdsjs.save.Model.bean.Register;
import com.wecanstudio.xdsjs.save.Model.bean.LoginResponse;
import com.wecanstudio.xdsjs.save.Model.bean.User;
import com.wecanstudio.xdsjs.save.Model.bean.UserInfo;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import rx.Observable;

/**
 * RestApi for retrieving data from the network.
 */
public interface RestApi {
    /**
     * 登陆
     *
     * @param user
     * @return
     */
    @POST("/sparkjava/login")
    Observable<LoginResponse> login(@Body User user);

    /**
     * 获取用户信息
     *
     * @param token
     * @param username
     * @return
     */
    @GET("/sparkjava/userMessage/{username}")
    Observable<UserInfo> getUserInfo(@Header("access_token") String token, @Path("username") String username);

    /**
     * 注册
     *
     * @param register
     * @return
     */
    @POST("/sparkjava/user")
    Observable<RegistResponse> regist(@Body Register register);

    /**
     * 获取账单信息
     *
     * @param token
     * @param username
     * @return
     */
    @GET("/sparkjava/billsMessage/{username}")
    Observable<String> getBillMessage(@Header("access_token") String token, @Path("username") String username);

    /**
     * 添加自定义类型
     *
     * @param token
     * @param username
     * @param myBillType
     * @return
     */
    @POST("/sparkjava/type/{username}")
    Observable<String> addType(@Header("access_token") String token, @Path("username") String username, @Body MyBillType myBillType);

    /**
     * 更新用户信息
     *
     * @param token
     * @param userInfo
     * @return
     */
    @POST("/sparkjava/userMessage/")
    Observable<LoginResponse> updateUserInfo(@Header("access_token") String token, @Body UserInfo userInfo);

    /**
     * 更新头像
     *
     * @param token
     * @param username
     * @param userInfo
     * @return
     */
    @PUT("/sparkjava/image/{username}")
    Observable<LoginResponse> updateUserAvatar(@Header("access_token") String token, @Path("username") String username, @Body UserInfo userInfo);

    /**
     * 获取预判类型
     *
     * @param token
     * @param billTypeList
     * @return
     */
    @POST("/sparkjava/MaxType")
    Observable<MaxTypeResponse> getMaxType(@Header("access_token") String token, @Body BillTypeList billTypeList);
}
