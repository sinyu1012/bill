package com.wecanstudio.xdsjs.save.Model.bean;

import java.util.List;

/**
 * Created by xdsjs on 2015/11/23.
 */
public class BillTypeList {

    /**
     * type_id : 1
     * choose : 1
     * time : 1447721922872
     */

    private List<DataEntity> data;

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        private int type_id;//类型对应的id
        private int choose;//选择的次数
        private long time;//时间戳

        public void setType_id(int type_id) {
            this.type_id = type_id;
        }

        public void setChoose(int choose) {
            this.choose = choose;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public int getType_id() {
            return type_id;
        }

        public int getChoose() {
            return choose;
        }

        public long getTime() {
            return time;
        }
    }
}
