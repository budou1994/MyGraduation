package com.graduation.mygraduation.entity;

import java.util.List;

/**
 * 妹子详情列表
 */

public class BeautyPhoList {
    /**
     * "status": true,
     * "total": 1022,
     * "tngou": [
     * {
     * "count": 23930,
     * "fcount": 0,
     * "galleryclass": 4,
     * "id": 1036,
     * "img": "/ext/161223/7083a1fde72448a62e477c5aab0721c8.jpg",
     * "rcount": 0,
     * "size": 11,
     * "time": 1482494705000,
     * "title": "大胸美女性感爆乳丰满胸围性感图片"
     * },
     */

    private boolean status;
    private int total;
    private List<tngou> tngou;

    public class tngou {
        private int count;
        private int fcount;
        private int galleryclass;
        private int id;
        private String img;
        private int rcount;
        private int size;
        private long time;
        private String title;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getFcount() {
            return fcount;
        }

        public void setFcount(int fcount) {
            this.fcount = fcount;
        }

        public int getGalleryclass() {
            return galleryclass;
        }

        public void setGalleryclass(int galleryclass) {
            this.galleryclass = galleryclass;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getRcount() {
            return rcount;
        }

        public void setRcount(int rcount) {
            this.rcount = rcount;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<BeautyPhoList.tngou> getTngou() {
        return tngou;
    }

    public void setTngou(List<BeautyPhoList.tngou> tngou) {
        this.tngou = tngou;
    }
}
