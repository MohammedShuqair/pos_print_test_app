package com.example.test.newland.test_app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.newland.sdk.me.module.printer.ErrorCode;
import com.newland.sdk.me.module.printer.ModuleManage;
import com.newland.sdk.me.module.printer.PrintListener;
import com.newland.sdk.me.module.printer.PrinterModule;
import com.newland.sdk.me.module.printer.PrinterStatus;

import java.util.HashMap;
import java.util.Map;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;

public class MainActivity extends FlutterActivity {
    private static final String CHANNEL =AppStrings.methodChannel;

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL)
                .setMethodCallHandler(
                        (call, result) -> {
                            // This method is invoked on the main thread.
                            if (call.method.equals(AppStrings.printMethod)) {
                                String methodResult = printTest();
                                result.success(methodResult);
                            } else {
                                result.notImplemented();
                            }
                        }
                );
    }

    private String printTest() {
        try{
            //Obtain Status of Printer
            ModuleManage.getInstance().init();
            PrinterModule mPrinterModule = ModuleManage.getInstance().getPrinterModule();
            PrinterStatus printerStatus = mPrinterModule.getStatus();
            if (!PrinterStatus.NORMAL.name().equals(printerStatus.name())) {
                return "status error";
            }
            //Assembly Data
            StringBuffer printDara = new StringBuffer();
            String fontsPath = mPrinterModule.setFont(this, "simsun.ttc");
            if (fontsPath != null) {
                //Set Font
                printDara.append("!font " + fontsPath + "\n");
                //Set concentration
                printDara.append("!gray 8\n");
            }
            Map<String, Bitmap> bitmaps = new HashMap<>();
            Bitmap logo = BitmapFactory.decodeResource(this.getResources(), R.drawable.logo);
            String bitmapName1 = "logo";
            bitmaps.put(bitmapName1, logo);
            //Image centered
            printDara.append("*image c 370*120 path:" + bitmapName1 + "\n");
            //Set Font Size,small
            printDara.append("!hz s\n!asc s\n");
            //text-align left
            printDara.append("!NLFONT 15 15 3\n*text l MID:123456789012345\n");
            printDara.append("!NLFONT 15 15 3\n*text l TID:12345678\n");
            //text-align center ，small
            printDara.append("!NLFONT 10 22 3\n*text c ------------------\n");
            //card number
            printDara.append("!NLFONT 6 36 0\n*text c 621669********1111\n");
            printDara.append("!NLFONT 15 15 3\n*TEXT l BATCH NO.:\n!NLFONT 15 15 3\n*text r 000002\n");
            printDara.append("!NLFONT 15 15 3\n*TEXT l VOUCHER NO.:\n!NLFONT 15 15 3\n*text r 000045\n");
            printDara.append("!NLFONT 15 15 3\n*TEXT l Auth NO.:\n!NLFONT 15 15 3\n*text r 123456\n");
            //Amount
            printDara.append("!NLFONT 6 36 0\n*TEXT l Amount:\n!NLFONT 6 36 0\n*text c $\n!NLFONT 6 36 0\n*text r 123456\n");
            printDara.append("!NLFONT 10 22 3\n*text c ------------------\n");
            //small
            printDara.append("!NLFONT 6 1 3\n*text l AID:A0000003301101\n");
            printDara.append("!NLFONT 6 1 3\n*text l TSI:0000\n");
            //Barcode
            printDara.append("!BARCODE 8 120 1 3\n*BARCODE c ABC123456123\n");
            //QRCODE
            printDara.append("!QRCODE 300 0 3\n*QRCODE c Test123456788\n");
            //Skip line
            printDara.append("*feedline 8\n");

            mPrinterModule.print(printDara.toString(),
                    bitmaps,
                    new PrintListener() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(ErrorCode errorCode, String s) {
                        }
                    });
            return "Success";
        }catch (Exception e){
            return  "error: "+ e;
        }

    }


}
class AppStrings{
    static  String methodChannel="samples.flutter.dev/battery";
    static  String printMethod="printMethod";
}