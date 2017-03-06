package com.jinritoutiao;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import com.android.uiautomator.core.UiCollection;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

import android.R.color;
import android.R.string;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class UiAutomatorBase extends UiAutomatorTestCase {
	
	
	public static final String TAG = UiAutomatorBase.class.getSimpleName();
	public static final String FORMAT_LOG = ">>> %s [%s] %s";
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy-HH-DD hh:mm:ss");
	public static final long TIME_OUT_FOR_EXISTS = 5 * 1000L;
	
	/**
	 * 返回当前系统时间
	 * @return yyyy-HH-DD hh:mm:ss
	 */
	public static String getCurTime() {
		return DATE_FORMAT.format(new Date(System.currentTimeMillis()));
	}
	
	/**
	 * 输出日志
	 * @param tag TAG
	 * @param message 消息
	 */
	public static void log(String tag, String message){
		System.out.println(String.format(FORMAT_LOG, getCurTime(), tag, message));
	}
	
	
	
	
	public UiObject getObjByRid(String Rid){
		return new UiObject(new UiSelector().resourceId(Rid));
	}
	

	public UiObject getObjByDescription(String desc) {
		return  new UiObject(new UiSelector().description(desc));
	}
	
	public UiObject getObjByText(String text) {
		return new UiObject(new UiSelector().text(text));
	}
	
	public UiObject getObjByClass(String ClassName) {
		return new UiObject(new UiSelector().className(ClassName));
	}
	
	public UiObject getObjByPackageName(String name) {
		return new UiObject(new UiSelector().packageName(name));
	}
	
	public UiObject getObjByRidINS(String Rid,int instance){
		return new UiObject(new UiSelector().resourceId(Rid).instance(instance));
	}
	

	public UiObject getObjByDescriptionINS(String desc,int instance) {
		return  new UiObject(new UiSelector().description(desc).instance(instance));
	}
	
	public UiObject getObjByTextINS(String text,int instance) {
		return new UiObject(new UiSelector().text(text).instance(instance));
	}
	
	public UiObject getObjByClassINS(String ClassName,int instance) {
		return new UiObject(new UiSelector().className(ClassName).instance(instance));
	}
	
	public UiObject getObjByPackageNameINS(String name,int instance) {
		return new UiObject(new UiSelector().packageName(name).instance(instance));
	}
	
	public UiObject getObjByRidText(String Rid,String text){
		return new UiObject(new UiSelector().resourceId(Rid).text(text));
	}
	
	public UiObject getObjByClassText(String ClassName,String Text) {
		return new UiObject(new UiSelector().text(Text).className(ClassName));
	}
	
	public UiObject getObjByDescriptionText(String desc,String ClassName) {
		return  new UiObject(new UiSelector().description(desc).className(ClassName));
	}
	
	
	
	
	
	public int getListviewNum() throws UiObjectNotFoundException{  //获取所有note的数目
		HashSet<String> filename = new HashSet<String>();
		UiScrollable listview = new UiScrollable(new UiSelector().className("android.widget.ListView"));
		UiSelector testview = new UiSelector().className("android.widget.TextView");
		listview.scrollToBeginning(5);
		boolean flag = false;
		while(true){
			int count = listview.getChildCount();
			for(int k=0;k<count;k++){
				String e = listview.getChildByInstance(testview, k).getText();
				filename.add(e);
			}
			if(flag){
				break;
			}
			if(!listview.scrollForward(80)){  //滚动到最后一页仍要将列表元素加入到hashset中
				flag = true;
			}
		}
		return filename.size();
	}
	
	public int startApp(String componentName)	{ 	//通过startactivity启动app函数
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("am start -n ");
		sBuffer.append(componentName);
		int ret = -1;
		try {
			Process process = Runtime.getRuntime().exec(sBuffer.toString());
			ret = process.waitFor();
		} catch(Exception e){
			e.printStackTrace();
		}
		return ret;
	}

	public UiObject getSameLineObject(UiObject srcObject, String destClass,
			int upOffset, int downOffset){
		Rect r1;
		UiCollection collection = new UiCollection(new UiSelector().index(0));
		UiObject CheckObject = null;
		try{
			r1 = srcObject.getBounds();
			int y0 = r1.top + upOffset;
			int y1 = r1.bottom + downOffset;
			for (int i=0;i<10;i++){
				CheckObject = collection.getChildByInstance(new UiSelector().className(destClass), i);
				Rect rect = CheckObject.getBounds();
				int centy = rect.centerY();
				if(centy>y0&&centy<y1){
					return CheckObject;
				}
			}
		} catch(UiObjectNotFoundException e){
			e.printStackTrace();
		}
		return CheckObject;
	}

	public void saveBitmapToSdcard(Bitmap bitmap,String newName){
		FileOutputStream out = null;
		try {
			out = new FileOutputStream("/mnt/sdcard/"+newName+".jpg");
			if(out!=null){
				bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
				out.close();
			}
		} catch (Exception e) {
//			throw new RuntimeException(e);
		}
		
	}
		
		
	public void cutBitMap(Rect rect, String path ,String newName){
		Bitmap m = BitmapFactory.decodeFile(path);
		m=m.createBitmap(m, rect.left, rect.top, rect.width(), rect.height());
		saveBitmapToSdcard(m, newName);
	}
	
	public int getColorPicel(int x,int y){
		String path = "/mnt/sdcard/testcolor.png";
		File file = new File(path);
		UiDevice.getInstance().takeScreenshot(file);
		Bitmap m = new BitmapFactory().decodeFile(path);
		int color = m.getPixel(x, y);
		return color;
	}
	
	public void screenShotAndDrawText(String path,String imageName,String text){
		File file = new File(path);
		UiDevice.getInstance().takeScreenshot(file);
		Bitmap bitmap = new BitmapFactory().decodeFile(path);
		Bitmap drawBitmap=drawBitmapText(bitmap, text);
		saveBitmapToSdcard(drawBitmap, imageName);
		
		
		
	}
	
	public Bitmap drawBitmapText(Bitmap bitmap,String text){
		int x =bitmap.getWidth();
		int y =bitmap.getHeight();
		
		//创建一个比原来 图片更大的位图
		Bitmap newbitmap = bitmap.createBitmap(x, y+80, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(newbitmap);
		Paint paint = new Paint();
		//在原图的位置（0,0）叠加一张图片
		canvas.drawBitmap(bitmap, 0,0, paint);
		//设置画笔颜色
		paint.setColor(Color.parseColor("#FF0000"));
		//设置字体大小
		paint.setTextSize(30);
		canvas.drawText(text, 20,y+55, paint); 
		canvas.save(Canvas.ALL_SAVE_FLAG);
		return newbitmap;
	}
	
	public boolean imageSameAs(String targetImagePath,String comPath,double percent){
		try {
			Bitmap m1 = BitmapFactory.decodeFile(targetImagePath);
			Bitmap m2 = BitmapFactory.decodeFile(comPath);
			
			int width = m2.getWidth();
			int height=m2.getHeight();
			int numDiffPixels=0;
			for(int y=0;y<height;y++){
				for(int x=0;x<width;x++){
					if(m2.getPixel(x, y)!=m1.getPixel(x, y)){
						numDiffPixels++;
					}
				}
			}
			double totalPicels=width*height;
			double diffPercent=numDiffPixels/totalPicels;
			return percent<=1.0-diffPercent;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return false;
	}



}
