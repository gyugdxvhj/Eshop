package org.ccunix.eshop.utils;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: 魏建波
 * Date: 2021/4/23
 * Time: 18:55
 * Description:
 */
public class DataFormatUtil {
    public static String formatDateString(Date date, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }
    private static final AtomicInteger atomicInteger = new AtomicInteger(1000000);//多线程同步

    // 根据uuid生成的订单
    public static synchronized String getOrderNoByUuid(){
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-","");
    }
    // 生成订单
    /**
     * uuid
     *
     * @param no
     * @return
     */
    public static synchronized String getOrderNoByUUIDHashCode(String no) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Integer uuidHashCode = UUID.randomUUID().toString().hashCode();
        System.out.println("uuidHashCode="+uuidHashCode);
        if (uuidHashCode < 0) {
            uuidHashCode = uuidHashCode * (-1);
        }
        String date = simpleDateFormat.format(new Date());
        return no + date + uuidHashCode;
    }

    /**
     * 获取同一秒钟 生成的订单号连续
     *
     * @param no
     *            数据中心编号
     * @return 同一秒内订单连续的编号
     */
    public static synchronized String getOrderNoByAtomic(String no) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        atomicInteger.getAndIncrement();// x++
        int i = atomicInteger.get();
        String date = simpleDateFormat.format(new Date());
        return no + date + i;
    }

    public static String encodingParseUTF8(String data) {
        try {
            return new String(data.getBytes("iso-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        for(int i=0;i<100;i++) {
            // String str = UUID.randomUUID().toString();
            // System.out.println(getOrderNoByAtomic("bobo"));
            System.out.println(getOrderNoByUUIDHashCode("bobo"));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}