package com.yydscm.view;

/**
 * 分页实体
 *
 * @author Administrator
 */
public class PageBean {
    private int curPage;             //当前页  
    private int pageCount;           //总页数  
    private int rowsCount;           //总行数  
    private int pageSize = 10;         //每页多少行


    public PageBean(int rows, int pageSize) {

        this.pageSize = pageSize;
        this.setRowsCount(rows);
        if (this.rowsCount % this.pageSize == 0) {
            this.pageCount = this.rowsCount / this.pageSize;
        } else if (rows < this.pageSize) {
            this.pageCount = 1;
        } else {
            this.pageCount = this.rowsCount / this.pageSize + 1;
        }
    }


    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getRowsCount() {
        return rowsCount;
    }

    public void setRowsCount(int rowsCount) {
        this.rowsCount = rowsCount;
    }
}