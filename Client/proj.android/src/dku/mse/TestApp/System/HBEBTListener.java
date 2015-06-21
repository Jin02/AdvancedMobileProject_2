package dku.mse.TestApp.System;

public abstract interface HBEBTListener
{
  public abstract void onConnected();

  public abstract void onDisconnected();

  public abstract void onConnecting();

  public abstract void onConnectionFailed();

  public abstract void onConnectionLost();

  public abstract void onReceive(byte[] paramArrayOfByte);
}