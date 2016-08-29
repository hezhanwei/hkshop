package com.bluemobi.test;

import com.bluemobi.to.ResultTO;

public class Test {

    
    public static void main(String[] args){
        
        
        Test t = new Test();
        
        ResultTO r = new ResultTO();
        t.modify(r);
        
        System.out.println(r.getMsg());
        
    }
    
    public void modify(ResultTO r){
        r.setMsg("aaaa11111");
        r = new ResultTO();
        r.setMsg("aaaa");
    }
    
    
    
}
