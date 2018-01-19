package com.project.goosegame.model.pojo;

/**
 * Created by Adam on 19/01/2018.
 */

public class Case {
  private  int id;
  private  boolean isBonus;
  private  int type;
  private  float x;
  private  float y;
  private  int height;
  private  int width;

    public Case(int id, boolean isBonus, int type, float x, float y) {
        this.id = id;
        this.isBonus = isBonus;
        this.type = type;
        this.x = x;
        this.y = y;
        this.height= 300;
        this.width = 200;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isBonus() {
        return isBonus;
    }

    public void setBonus(boolean bonus) {
        isBonus = bonus;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
