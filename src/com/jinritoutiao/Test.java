package com.jinritoutiao;


import java.io.File;
import java.security.KeyStore.CallbackHandlerProtection;
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


import android.R.string;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.RemoteException;
import android.widget.GridView;
import jp.jun_nama.test.utf7ime.helper.Utf7ImeHelper;

public class Test extends UiAutomatorBase {

//	private static final String TAG = Test.class.getSimpleName();
//	private static final String FORMAT_LOG = ">>> %s [%s] %s";
//	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
//			"yyyy-HH-DD hh:mm:ss");
//	private static final long TIME_OUT_FOR_EXISTS = 5 * 1000L;
	
	protected void setUp() throws Exception {
		log(TAG, "setUp of " + getName());
		UiDevice.getInstance().pressBack();
		UiDevice.getInstance().pressBack();
		UiDevice.getInstance().pressHome();
		UiDevice.getInstance().pressHome();
		super.setUp();
		//startApp(testCmp1);
	}
	protected void tearDown() throws Exception {
//		UiObject find_product = getObjByRidText("cn.com.pconline.android.browser:id/main_content_bottom_tv"
//				, "找产品");
//		while(!find_product.exists()){
//			UiDevice.getInstance().pressBack();
//		}
		UiDevice.getInstance().pressBack();
		UiDevice.getInstance().pressBack();
		UiDevice.getInstance().pressHome();
		log(TAG, "tearDown of " + getName());
		super.tearDown();
	}
//	private static String getCurTime() {
//		return DATE_FORMAT.format(new Date(System.currentTimeMillis()));
//	}
//	
//	/**
//	 * 输出日志
//	 * @param tag TAG
//	 * @param message 消息
//	 */
//	private static void log(String tag, String message){
//		System.out.println(String.format(FORMAT_LOG, getCurTime(), tag, message));
//	}
	public static void main(String[] args) {
		
		String jarName ="Test";
		String testClass="com.jinritoutiao.Test";
		String testName="testCreateBitmap";
		String androidId="3";
		new UiAutomatorHelper(jarName, testClass, testName, androidId);
	}

	public void test_01() throws UiObjectNotFoundException {
		UiObject content = getObjByRid("cn.com.pconline.android.browser:id/search_content");
		UiObject empty_view=getObjByRid("cn.com.pconline.android.browser:id/empty_view");
		String componentName="cn.com.pconline.android.browser/.module.launcher.LauncherActivity";
		String title="@@@@@";
		String search_type="产品";
		String tips="搜索"+search_type+"为空:\n没有相关"+search_type+"，请调整搜索词";
		startApp(componentName);
		sleep(10000);
		search(title, search_type);
		sleep(2000);
		assertEquals("search content doesn't match ",title, content.getText());
		assertEquals("search tips doesn't match ",tips, empty_view.getText());//判断搜索为空
	}
	
	public void test_02() throws UiObjectNotFoundException {
		String componentName="cn.com.pconline.android.browser/.module.launcher.LauncherActivity";
		String upContent = "图赏";
		startApp(componentName);
		up_menu(upContent);
	}
	
	public void test_03() throws RemoteException, UiObjectNotFoundException {
		String componentName="cn.com.pconline.android.browser/.module.launcher.LauncherActivity";
		startApp(componentName);
		sleep(7000);
//		UiObject more = getObjByRid("cn.com.pconline.android.browser:id/right_mask");
		UiObject more = new UiObject(new UiSelector().className("android.widget.ImageButton"));
		more.clickAndWaitForNewWindow();
		sleep(2000);
		UiObject girdview = new UiObject(new UiSelector().className("android.widget.GridView"));
		int abc = girdview.getChildCount();
		System.out.println("column num:"+abc);
		
	}
	
	public void testCreateBitmap(){
		//截取一张图片
		String path = "/mnt/sdcard/testBitmap.png";
		File storePath = new File(path);
		UiDevice.getInstance().takeScreenshot(storePath);
		sleep(3000);
		//创建bitmap
		Bitmap bitmap = BitmapFactory.decodeFile(path);
		//重命名图片且另存为
		String newName = "testBitmap111";
		saveBitmapToSdcard(bitmap,newName);
		
	}
	
	public void testGetPicelAndCutImg() throws UiObjectNotFoundException{
		UiObject object = getObjByClass("");
		Rect rect =object.getBounds();
		String path = "/mnt/sdcard/testGetPicelAndCutImg.png";
		String newname="testGetPicelAndCutImg";
		//截取图片
		cutBitMap(rect, path,newname);
		
		//获取某个点的颜色值
		getColorPicel(rect.centerX(),rect.centerY());
		
	}
	
	public void testEmbedText() {
		String path = "/mnt/sdcard/testEmbed.png";
		String imageName = "testEmbedText123";
		String text = "测试信息testEmbedText";
		screenShotAndDrawText(path, imageName, text);
	}
	
	public void testImgComparison(){
		String targetImagePath="/mnt/sdcard/c1.png";
		String comPath="/mnt/sdcard/c1.png";
		
		File f1 = new File(targetImagePath);
		File f2 = new File(comPath);
		
		UiDevice.getInstance().takeScreenshot(f1);
		sleep(3000);
		UiDevice.getInstance().pressHome();
		UiDevice.getInstance().takeScreenshot(f2);
		
		boolean b =imageSameAs(targetImagePath,comPath,1.0d);
		System.out.println("图像对比结果"+b);
		
	}
	
//	private int startApp(String componentName)	{ 	//通过startactivity启动app函数
//		StringBuffer sBuffer = new StringBuffer();
//		sBuffer.append("am start -n ");
//		sBuffer.append(componentName);
//		int ret = -1;
//		try {
//			Process process = Runtime.getRuntime().exec(sBuffer.toString());
//			ret = process.waitFor();
//		} catch(Exception e){
//			e.printStackTrace();
//		}
//		return ret;
//	}
//	
	public void launchApp() throws UiObjectNotFoundException{ //黑盒方法打开app
		UiObject test = new UiObject(new UiSelector().text("Notes"));
        test.clickAndWaitForNewWindow();	
		sleep(1500);
		//cleanNote();//清空所有note
	}
	
	public void  search(String title,String search_type) throws UiObjectNotFoundException {	
		UiObject search_text = getObjByRid("cn.com.pconline.android.browser:id/xlistview_header_edittext");
		UiObject search_content = getObjByRid("cn.com.pconline.android.browser:id/search_content");
		UiObject search_btn = getObjByRid("cn.com.pconline.android.browser:id/search_btn");
		UiObject search_type_view = getObjByRid("cn.com.pconline.android.browser:id/search_type_view");
		UiObject search_Type = getObjByRidText("cn.com.pconline.android.browser:id/search_type_view"
				, search_type);
		down_menu("找产品");
		sleep(1000);
		search_text.clickAndWaitForNewWindow();
		sleep(1000);
		search_type_view.click();
		sleep(1000);
		search_Type.click();
		search_content.setText(Utf7ImeHelper.e(title));
		sleep(1000);
		search_btn.clickAndWaitForNewWindow();
	}
	
	public void down_menu(String mainContent) throws UiObjectNotFoundException{
		UiObject find_product = getObjByRidText("cn.com.pconline.android.browser:id/main_content_bottom_tv"
				,mainContent);
		find_product.clickAndWaitForNewWindow();
		assertTrue("doesn't click ", find_product.isSelected());
	}
	
	public void up_menu(String upContent) throws UiObjectNotFoundException {
		
		UiScrollable upOption_menu = new UiScrollable(new UiSelector()
				.className("android.widget.LinearLayout"));
//		upOption_menu.setAsHorizontalList();
//		UiObject upOption = upOption_menu.getChildByText(new UiSelector()
//				.className("android.widget.TextView"),upContent , true);
		UiObject upOption1 = new UiObject(new UiSelector().text(upContent));
		while(!upOption1.exists()){
			upOption_menu.swipeLeft(5);
		}
		
		upOption1.clickAndWaitForNewWindow();
		assertTrue("up option does't selected", upOption1.isSelected());
	}

	

	
}
