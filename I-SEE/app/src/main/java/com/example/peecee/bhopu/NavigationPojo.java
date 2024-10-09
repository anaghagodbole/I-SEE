package com.example.peecee.bhopu;

public class NavigationPojo
{
    String title;
    boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public NavigationPojo(String title)
    {
        this.title=title;

    }

    public NavigationPojo() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

