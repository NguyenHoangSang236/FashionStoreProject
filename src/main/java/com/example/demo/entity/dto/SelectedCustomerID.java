package com.example.demo.entity.dto;

public class SelectedCustomerID {
    private int[] idList;
    
    
    public SelectedCustomerID() {}

    public SelectedCustomerID(int[] idList) {
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
