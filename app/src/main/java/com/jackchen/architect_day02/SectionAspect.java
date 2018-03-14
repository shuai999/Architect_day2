package com.jackchen.architect_day02;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Email: 2185134304@qq.com
 * Created by JackChen 2018/3/14 15:02
 * Version 1.0
 * Params:
 * Description:  处理切点
*/
@Aspect
public class SectionAspect {

    /**
     * 找到处理的切点
     * * *(..)  可以处理所有的方法
     */
    @Pointcut("execution(@com.jackchen.architect_day02.CheckNet * *(..))")
    public void checkNetBehavior() {

    }

    /**
     * 处理切面
     */
    @Around("checkNetBehavior()")
    public Object checkNet(ProceedingJoinPoint joinPoint) throws Throwable {
        Log.e("TAG", "checkNet");
        // 这里只要可以进来，就可以去做下边的操作了
        // 在这里可以做一些共有代码的处理
        // 比如做埋点、日志上传、权限检测、网络检测
        // 网络检测
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 1. 获取 CheckNet注解 NDK图片压缩 C++调用Java方法
        CheckNet checkNet = signature.getMethod().getAnnotation(CheckNet.class);
        if (checkNet != null){
            // 2. 判断有没有网络 如何获取context
            Object object = joinPoint.getThis() ;  // View、Activity、Fragment ； getThis()方法表示当前切点方法所在的类
            Context context = getContext(object);
            if (context != null){
                if (!isNetworkAvailable(context)){
                    // 3. 没有网络就不要再往下边执行
                    Toast.makeText(context , "请检查您的网络" , Toast.LENGTH_SHORT).show();
                    return null ;
                }
            }
        }

        return joinPoint.proceed();
    }


    /**
     * 通过对象获取上下文
     * @param object
     * @return
     */
    private Context getContext(Object object) {
        if (object instanceof Activity){
            return (Activity) object;
        }else if (object instanceof Fragment){
            Fragment fragment = (Fragment) object;
            return fragment.getActivity() ;
        }else if (object instanceof View){
            View view = (View) object;
            return view.getContext() ;
        }
        return null ;
    }


    /**
     * 检查当前网络是否可用
     *
     * @return
     */
    private static boolean isNetworkAvailable(Context context) {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
