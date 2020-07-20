# StuckDetection
卡顿检测
给主线程Looper设置我们自己的mLogging，通过检测mLogging中检测打印里面字符，判断">>>>> Dispatching to"和"<<<<< Finished to"执行时间差，来判断是否存在卡顿
