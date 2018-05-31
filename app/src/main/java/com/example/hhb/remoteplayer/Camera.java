package com.example.hhb.remoteplayer;

public class Camera
{
    private String id;

    private String place;

    private int imageId;

    public Camera(String id,String place,int imageId)
    {
        this.id=id;
        this.place=place;
        this.imageId=imageId;
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

}
