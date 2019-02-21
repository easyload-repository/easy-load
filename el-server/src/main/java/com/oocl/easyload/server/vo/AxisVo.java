package com.oocl.easyload.server.vo;

import java.io.Serializable;

public class AxisVo implements Serializable {

  private Float positiveX;
  private Float negativeX;
  private Float positiveY;
  private Float negativeY;

  public Float getPositiveX() {
    return positiveX;
  }

  public void setPositiveX(Float positiveX) {
    this.positiveX = positiveX;
  }

  public Float getNegativeX() {
    return negativeX;
  }

  public void setNegativeX(Float negativeX) {
    this.negativeX = negativeX;
  }

  public Float getPositiveY() {
    return positiveY;
  }

  public void setPositiveY(Float positiveY) {
    this.positiveY = positiveY;
  }

  public Float getNegativeY() {
    return negativeY;
  }

  public void setNegativeY(Float negativeY) {
    this.negativeY = negativeY;
  }
}
