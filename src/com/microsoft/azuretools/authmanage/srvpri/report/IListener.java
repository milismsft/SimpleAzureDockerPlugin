package com.microsoft.azuretools.authmanage.srvpri.report;

public interface IListener<T> {
  void listen(T message);
}
