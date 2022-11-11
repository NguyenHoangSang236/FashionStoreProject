package com.example.demo.entity.dto;

public class SelectedProductID {
    private int[] idList;
    
    
    public SelectedProductID() {}

    public SelectedProductID(int[] idList) {
        super();
        this.idList = idList;
    }

    
    
    public int[] getIdList() {
        return idList;
    }

    public void setIdList(int[] idList) {
        this.idList = idList;
    }
}
