package com.halo.bmsce;

public class Material {
    private String icon,material_link,material_title,material_date;

    public void Material(){}

    public void Material(String icon,String material_link,String material_title,String material_date){
        this.icon=icon;
        this.material_link=material_link;
        this.material_title=material_title;
        this.material_date=material_date;
    }

    public String getIcon() {
        return icon;
    }

    public String getMaterial_link() {
        return material_link;
    }

    public String getMaterial_title() {
        return material_title;
    }

    public String getMaterial_date() {
        return material_date;
    }
}
