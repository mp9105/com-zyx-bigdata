package com.zyx.jdbcdemo.druid;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author zyx
 * @since 2021/04/28 21:32
 */
public class DruidUtil {
    public static Connection getConnection11() throws ClassNotFoundException, SQLException {
        InputStream is = DruidUtil.class
                .getClassLoader().getResourceAsStream("mysql.properties");
        Properties prop = new Properties();
        try {
            prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = prop.getProperty("jdbc.url");
        Class.forName(prop.getProperty("jdbc.driverClassName"));

        return DriverManager.getConnection(url, prop);
    }

    public static Connection getConnect() {
        InputStream druidIs = DruidUtil.class
                .getClassLoader().getResourceAsStream("druid.properties");
        Properties properties = new Properties();
        try {
            properties.load(druidIs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            DataSource ds = DruidDataSourceFactory.createDataSource(properties);
            return ds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        try (Connection conn = getConnect()) {
            System.out.println("connection >>> " + conn);
            System.out.println("连接成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
