package com.cs251.tasktree;



public class subProduct {
    private int idoftask;
    private String task_name;
    private String task_date;
    private String task_description;


    public subProduct(int idoftask,String task_name, String task_date,String task_description){
        this.idoftask=idoftask;
        this.task_name=task_name;
        this.task_date=task_date;
        this.task_description=task_description;
    }
    public  int getidoftask(){ return idoftask;}
    public String getTask_name(){
        return task_name;
    }

    public String getTask_date() {
        return task_date;
    }
    public String getTask_description() {
        return task_description;
    }
}
