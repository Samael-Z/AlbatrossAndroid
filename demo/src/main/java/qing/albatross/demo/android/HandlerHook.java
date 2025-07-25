package qing.albatross.demo.android;

import android.os.Handler;
import android.os.Message;

import qing.albatross.annotation.CompileOption;
import qing.albatross.annotation.MethodBackup;
import qing.albatross.annotation.MethodHook;
import qing.albatross.annotation.TargetClass;
import qing.albatross.core.Albatross;

@TargetClass(value = Handler.class, compileHooker = CompileOption.COMPILE_OPTIMIZED)
public class HandlerHook {

  @MethodBackup
  public static native void dispatchMessage(Handler handler, Message message);

  @MethodHook
  public static void dispatchMessage$Hook(Handler handler, Message message) throws Throwable {
    try {
      dispatchMessage(handler, message);
    } catch (Throwable e) {
      Throwable reason = e;
      if (e.getCause() != null)
        reason = e.getCause();
      if (reason instanceof AssertionError)
        throw reason;
      Albatross.log("dispatchMessage", reason);
    }
  }
}
