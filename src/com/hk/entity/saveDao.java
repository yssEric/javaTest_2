package com.hk.entity;

import com.hk.inface.MapperIf;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @program: javaTest
 * @description: 数据处理
 * @author: Mr.Yi
 * @create: 2018-11-30 11:19
 **/
public class saveDao<T> {

    private static saveDao satay = null;
    public static saveDao getInstance() {
        synchronized (saveDao.class) {
            if (satay == null) {
                satay = new saveDao<>();
            }
        }
        return satay;
    }

    /**
     * 保存数据
     */
    public void save() {
        /*dataModel model = new dataModel();
        model.setName("xiaoming");
        model.setAge("12");

        DbHelper.save("insert into t_age(age,nameStr) values (?,?)",model.getName(),model.getAge());*/

        //DbHelper.getInstance().save("insert into t_age(age,nameStr) values (?,?)",new saveDao().new MapperEn());
        /**
         * 匿名局部内部类,不需要在本类中添加内部类实现，既可以屏蔽83行代码
         */
        DbHelper.getInstance().save("insert into t_age(age,nameStr) values (?,?)", new MapperIf<T>() {
            @Override
            public T mapperObj(ResultSet res){
                return null;
            }

            @Override
            public T mapperSave() {

                dataModel model = new dataModel();
                model.setAge("3333");
                model.setName("44444");
                return (T)model;
            }
        });
    }

    /**
     * 查询单个元素
     * @return
     */
    public dataModel getData() {

        //return (dataModel)DbHelper.getInstance().getData("select *from t_age where nameStr =?", new saveDao().new MapperEn(),"12");
        /**
         * 匿名局部内部类
         */
        return (dataModel)DbHelper.getInstance().getData("select *from t_age where nameStr =?", new MapperIf<T>() {
            @Override
            public T mapperObj(ResultSet res) throws SQLException {

                dataModel model = new dataModel();
                model.setAge(res.getString("age"));
                model.setName(res.getString("nameStr"));

                return (T)model;
            }

            @Override
            public T mapperSave() {
                return null;
            }
        }, "12");
    }

    /**
     * 查询集合元素
     * @return
     */
    public List<dataModel> getListData() {
        //return DbHelper.getInstance().getListData("select *from t_age where nameStr =?", new saveDao().new MapperEn(),"12");

        /**
         * 匿名局部内部类
         */
        return DbHelper.getInstance().getListData("select *from t_age where nameStr =?", new MapperIf<T>() {
            @Override
            public T mapperObj(ResultSet res) throws SQLException {

                dataModel model = new dataModel();
                model.setAge(res.getString("age"));
                model.setName(res.getString("nameStr"));

                return (T)model;
            }

            @Override
            public T mapperSave() {
                return null;
            }
        }, "12");
    }

    /**
     * 私有内部类（只有本类使用，这种写法最佳）
     */
   /* private class MapperEn implements MapperIf<T> {

        @Override
        public T mapperObj(ResultSet res) throws SQLException {

            dataModel model = new dataModel();
            model.setAge(res.getString("age"));
            model.setName(res.getString("nameStr"));

            return (T)model;
        }

        @Override
        public T mapperSave() {

            dataModel model = new dataModel();
            model.setAge("3333");
            model.setName("44444");
            return (T)model;
        }
    }*/
}

