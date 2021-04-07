package com.mobile.bummerzaehler.listadapter;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: gangsta
 * Date: 10.10.13
 * Time: 15:31
 * To change this template use File | Settings | File Templates.
 */
public class NavigationListModel implements Serializable {

    private final int imageId;
    private final String title;    
    
    public NavigationListModel(int imageId, String title) {
        this.imageId = imageId;
        this.title = title;
    }

    /*********** Get Methods ****************/


    public String getTitle()
    {
        return this.title;
    }
    public int getImageId()
    {
        return this.imageId;
    }
}
