package io.github.wesleyosantos91.domain.commons;

import java.util.List;

public class PageModel<T> {
    private List<T> data;
    private long totalCount;
    private int page;
    private int size;

    public PageModel(List<T> data, long totalCount, int page, int size) {
        this.data = data;
        this.totalCount = totalCount;
        this.page = page;
        this.size = size;
    }


    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
