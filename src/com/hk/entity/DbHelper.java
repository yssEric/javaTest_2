package com.hk.entity;

import com.hk.inface.MapperIf;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: javaTest
 * @description: 数据库管理类
 * @author: Mr.Yi
 * @create: 2018-12-05 11:20
 **/
public class DbHelper<T> {

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URlSTR = "jdbc:mysql:///my_userInfro?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    private static final String NAME = "root";
    private static final String PASSWORD = "123456";
    private static Connection connect = null;
    private static PreparedStatement state = null;
    private static ResultSet res = null;

    /**
     * single
     */
    private static DbHelper helper = null;
    public static DbHelper getInstance() {
        synchronized (DbHelper.class) {
            if (helper == null) {
                helper = new DbHelper<>();
            }
        }
        return helper;
    }

    /**
     * 初始链接数据库
     * @return
     */
    public Connection getHelper() {
        try {
            Class.forName(DRIVER);
            connect = DriverManager.getConnection(URlSTR,NAME,PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return connect;
    }

    /**
     * 保存数据
     * @param sql 执行sql语句
     * @param prarms 参数值
     */
    public void save(String sql,Object...prarms) {
        connect = getHelper();
        try {
            state = connect.prepareStatement(sql);
            for (int i = 0;i < prarms.length;i++) {
                state.setObject(i+1,prarms[i]);
            }
            state.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(null,state,connect);
        }

    }

    /**
     * 另一种保存数据方式
     * @param sql
     * @param mapper
     */
    public void save(String sql,MapperIf mapper) {
        connect = getHelper();
        try {
            state = connect.prepareStatement(sql);
            dataModel model = (dataModel) mapper.mapperSave();
            state.setObject(1,model.getName());
            state.setObject(2,model.getAge());
            state.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(null,state,connect);
        }

    }

    /**
     * 查询单个元素
     * @param sql
     * @param mapper
     * @param prarms
     * @return
     */
    public T getData(String sql, MapperIf mapper,Object... prarms) {

        T t = null;
        connect = getHelper();
        try {
            state = connect.prepareStatement(sql);
            for (int i = 0;i < prarms.length;i++) {
                state.setObject(i+1,prarms[i]);
            }

            res = state.executeQuery();
            while (res.next()) {
                t = (T) mapper.mapperObj(res);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(res,state,connect);
        }

        return t;
    }

    /**
     * 查询集合元素
     * @param sql
     * @param mapper
     * @param prarms
     * @return
     */
    public List<T> getListData(String sql, MapperIf mapper,Object... prarms) {

        List<T> listAiry = new ArrayList<>();
        connect = getHelper();
        try {
            state = connect.prepareStatement(sql);
            for (int i = 0;i < prarms.length;i++) {
                state.setObject(i+1,prarms[i]);
            }

            res = state.executeQuery();
            while (res.next()) {
                listAiry.add((T) mapper.mapperObj(res));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(res,state,connect);
        }
        return listAiry;
    }


    /**
     * 关闭数据库
     * @param state 执行源
     * @param connect 链接源
     */
    public static void close(ResultSet res,Statement state,Connection connect) {
        try {
            if (res != null && !res.isClosed()) {
                res.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (state != null && !state.isClosed()) {
                    state.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    if (connect != null && !connect.isClosed()) {
                        connect.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
