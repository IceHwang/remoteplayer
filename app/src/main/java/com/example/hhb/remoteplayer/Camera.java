package com.example.hhb.remoteplayer;

public class Camera
{
    private String id;

    private String place;

    private int imageId;

    private String url;

    public Camera(String id,String place,int imageId,String url)
    {
        this.id=id;
        this.place=place;
        this.imageId=imageId;
        this.url=url;
    }

    public String getId()
    {
        return id;
    }

    public String getPlace()
    {
        return place;
    }

    public String getName()
    {
        return place+id;
    }

    public int getImageId()
    {
        return imageId;
    }

    public String getUrl()
    {
        return url;
    }

}
