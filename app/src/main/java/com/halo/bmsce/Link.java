package com.halo.bmsce;

import android.media.Image;
import android.net.Uri;

import io.grpc.internal.SharedResourceHolder;

public class Link {
    private String link,a1,thumbnail;

    public Link(){}

    public Link(String link,String a1,String thumbnail){
        this.a1=a1;
        this.link=link;
        this.thumbnail=thumbnail;
    }

    public String getLink() {
        return link;
    }

    public String getA1() {
        return a1;
    }

    public String getThumbnail() {
        return thumbnail;
    }


}
